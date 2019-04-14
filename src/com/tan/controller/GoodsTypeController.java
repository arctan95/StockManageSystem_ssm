package com.tan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tan.entity.GoodsType;
import com.tan.entity.PageBean;
import com.tan.service.GoodsService;
import com.tan.service.GoodsTypeService;
import com.tan.util.ExcelUtil;
import com.tan.util.ListUtil;
import com.tan.util.ResponseUtil;
import com.tan.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/goodsType")
public class GoodsTypeController {

	@Resource
	private GoodsTypeService goodsTypeService;
	@Resource
	private GoodsService goodsService;
	
	/**
	 * 商品类别分页查询
	 * @param page
	 * @param rows
	 * @param s_provider
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false) String page,@RequestParam(value="rows",required=false) String rows,GoodsType s_goodsType,HttpServletResponse response) throws Exception{
		//41至48行把前台分页数据传递到dao层供获取供应商信息用
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		if(s_goodsType.getTypeName()!=null){
			map.put("typeName", StringUtil.formatLike(s_goodsType.getTypeName()));
		}
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getSize());
		List<GoodsType> goodsTypeList=goodsTypeService.find(map);
		Long total = goodsTypeService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(goodsTypeList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response,result);
		return null;
	}
	
	/**
	 * 添加或修改
	 * @param goodsType
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(GoodsType goodsType,HttpServletResponse response) throws Exception{
		int resultTotal=0;//初始化操作记录数
		if(goodsType.getId()==null){
			resultTotal=goodsTypeService.add(goodsType);
		}else{
			resultTotal=goodsTypeService.update(goodsType);
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
	
	/**
	 * 删除
	 * @param delIds
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="delIds") String delIds,HttpServletResponse response) throws Exception{
		JSONObject jsonObject=new JSONObject();
		int delNums=0;
		String[] idStr=delIds.split(",");
		//解决外键约束问题
		for(int i=0;i<idStr.length;i++){
			//判断商品数据库中是否含有该商品类型
			Integer typeId=Integer.valueOf(idStr[i]);
			Long n=goodsService.getGoodsByTypeId(typeId);
			if(n>0){
				jsonObject.put("errorIndex", i);
				jsonObject.put("errorMsg", "存在于商品中，无法删除!");
				ResponseUtil.write(response, jsonObject);
			}else{
				goodsTypeService.delete(Integer.valueOf(idStr[i]));
				delNums++;
			}
		}
		
		if(delNums>0){
			jsonObject.put("success", true);
			jsonObject.put("delNums", delNums);
		}else{
			jsonObject.put("success", false);
			jsonObject.put("errorMsg", "删除失败");
		}
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	@RequestMapping("/goodsTypeComboList")
	public String goodsTypeComboList(HttpServletResponse response) throws Exception{
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		jsonObject.put("id", "");
		jsonObject.put("typeName", "请选择...");
		jsonArray.add(jsonObject);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("typeName","");
		List<GoodsType> goodsTypeComboList=goodsTypeService.find(map);
		jsonArray.addAll(goodsTypeComboList);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
	
	/**
	 * 导出数据
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/export")
	public String exportData(HttpServletResponse response) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", "");
		List<GoodsType> exportList=goodsTypeService.find(map);
		
		ListUtil listUtil=new ListUtil();
		List<Map<String,Object>> list=listUtil.listConvert(exportList);
		Workbook wb=ExcelUtil.fillExcelDataWithTemplate(list, "goodsTypeTemp.xls");
		ResponseUtil.export(response, wb, "导出商品类别.xls");
		return null;
	}
}
