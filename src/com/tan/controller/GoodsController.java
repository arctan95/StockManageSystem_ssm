package com.tan.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tan.entity.Goods;
import com.tan.entity.PageBean;
import com.tan.service.ExportService;
import com.tan.service.GoodsService;
import com.tan.service.ImportService;
import com.tan.service.StockService;
import com.tan.util.ExcelUtil;
import com.tan.util.ListUtil;
import com.tan.util.ResponseUtil;
import com.tan.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Resource
	private GoodsService goodsService;
	@Resource
	private ImportService importService;
	@Resource
	private ExportService exportService;
	@Resource
	private StockService stockService;
	
	@RequestMapping("/list")
	public String find(@RequestParam(value="page",required=false) String page,@RequestParam(value="rows",required=false) String rows,Goods s_goods,HttpServletResponse response) throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		if(s_goods.getGoodsId()!=null){
			map.put("goodsId", StringUtil.formatLike(s_goods.getGoodsId()));
		}
		if(s_goods.getGoodsName()!=null){
			map.put("goodsName", StringUtil.formatLike(s_goods.getGoodsName()));
		}
		if(s_goods.getProId()!=null){
			map.put("proId", s_goods.getProId());
		}
		if(s_goods.getTypeId()!=null){
			map.put("typeId",s_goods.getTypeId());
		}
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getSize());
		List<Goods> goodsList=goodsService.find(map);
		Long total=goodsService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(goodsList);
		result.put("rows", jsonArray);
		result.put("total",total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	@RequestMapping("/save")
	public String save(Goods goods,HttpServletResponse response) throws Exception{
		int resultTotal=0;//初始化操作记录数
		if(goods.getId()==null){
			resultTotal=goodsService.add(goods);
		}else{
			resultTotal=goodsService.update(goods);
		}
		JSONObject jsonObject=new JSONObject();
		if(resultTotal>0){
			jsonObject.put("success", true);
		}else{
			jsonObject.put("success", false);
		}
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="delIds") String delIds,HttpServletResponse response) throws Exception{
		JSONObject jsonObject=new JSONObject();
		int delNums=0;
		String[] idStr=delIds.split(",");
		//解决外键约束问题
		for(int i=0;i<idStr.length;i++){
			boolean f1=false;
			boolean f2=false;
			boolean f3=false;
			//判断入库信息中是否含有该商品
			Integer goodsId=Integer.valueOf(idStr[i]);
			Long n1=importService.getImportByGoodsId(goodsId);
			if(n1>0){
				f1=true;
			}
			//判断出库信息是否含有该商品
			Long n2=exportService.getExportByGoodsId(goodsId);
			if(n2>0){
				f2=true;
			}
			//判断库存信息是否含有该商品
			Long n3=stockService.getStockByGoodsId(goodsId);
			if(n3>0){
				f3=true;
			}
			
			if(f1||f2||f3){
				jsonObject.put("errorIndex", i);
				jsonObject.put("errorMsg", "商品库存量大于0，不能删除!");
				ResponseUtil.write(response, jsonObject);
			}else{
				goodsService.delete(Integer.valueOf(idStr[i]));
				delNums++;
			}
		}
		
		if(delNums>0){
			jsonObject.put("success", true);
			jsonObject.put("delNums", delNums);
		}else{
			jsonObject.put("success", false);
			jsonObject.put("errorMsg", "删除失败！");
		}
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	@RequestMapping("/goodsComboList")
	public String goodsComboList(HttpServletResponse response) throws Exception{
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		jsonObject.put("id", "");
		jsonObject.put("typeName", "请选择...");
		jsonArray.add(jsonObject);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("goodsName", "");
		List<Goods> goodsList=goodsService.find(map);
		jsonArray.addAll(goodsList);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
	
	@RequestMapping("/export")
	public String export(HttpServletResponse response) throws Exception{
		List<Goods> exportList=goodsService.exportData();
		ListUtil listUtil=new ListUtil();
		List<Map<String,Object>> list=listUtil.listConvert(exportList);
		Workbook wb=ExcelUtil.fillExcelDataWithTemplate(list, "goodsTemp.xls");
		ResponseUtil.export(response, wb, "导出商品.xls");
		return null;
	}
	
	@RequestMapping("/upload")
	public String upload(@RequestParam(value="userUploadFile") MultipartFile file,HttpServletResponse response) throws Exception{
		JSONObject jsonObject=new JSONObject();
		//判断文件是否为空
		if(file==null){
			return null;
		}
		//获取文件名
		String name=file.getOriginalFilename();
		//进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
		long size=file.getSize();
		if(name==null||("").equals(name)&&size==0){
			return null;
		}
		//定义对应的标题名与对应属性名
		Map<String, String> titleAndAttribute=new LinkedHashMap<String, String>();
		titleAndAttribute.put("商品编号", "goodsId");
		titleAndAttribute.put("商品名称", "goodsName");
		titleAndAttribute.put("过期月数", "expireTime");
		//此处只能对应数据库中t_goods中的proId字段，typeId同理
		titleAndAttribute.put("供应商", "proId");
		titleAndAttribute.put("商品类别", "typeId");
		titleAndAttribute.put("商品描述", "goodsDesc");
		
		ExcelUtil<Goods> excelUtil=new ExcelUtil<Goods>();
		Class<Goods> clazz=Goods.class;
		List<Goods> goodsList=excelUtil.getExcelInfo(file,titleAndAttribute,clazz);
		int resultTotal=goodsService.addGoodsList(goodsList);
		if(resultTotal>0){
			jsonObject.put("success", "true");
			jsonObject.put("totalRows", resultTotal);
		}else{
			jsonObject.put("success", "false");
		}
		ResponseUtil.write(response, jsonObject);
		return null;
	}
}
