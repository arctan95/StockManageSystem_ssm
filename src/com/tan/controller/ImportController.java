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

import com.tan.entity.Import;
import com.tan.entity.PageBean;
import com.tan.service.ImportService;
import com.tan.service.StockService;
import com.tan.util.ExcelUtil;
import com.tan.util.JsonUtil;
import com.tan.util.ListUtil;
import com.tan.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/import")

public class ImportController {

	@Resource
	private ImportService importService;
	@Resource
	private StockService stockService;
	
	@RequestMapping("/list")
	public String find(@RequestParam(value="bimpoPrice",required=false)String bimpoPrice,@RequestParam(value="eimpoPrice",required=false)String eimpoPrice,@RequestParam(value="bimpoDate",required=false)String bimpoDate,@RequestParam(value="eimpoDate",required=false)String eimpoDate,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,@RequestParam(value="goodsName",required=false)String goodsName,HttpServletResponse response ) throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		if(goodsName!=null){
			map.put("goodsName",goodsName);
		}
		if(bimpoPrice!=null&&bimpoPrice!=""){
			map.put("bimpoPrice", Integer.parseInt(bimpoPrice));
		}
		if(eimpoPrice!=null&&eimpoPrice!=""){
			map.put("eimpoPrice", Integer.parseInt(eimpoPrice));
		}
		if(bimpoDate!=null){
			map.put("bimpoDate", bimpoDate);
		}
		if(eimpoDate!=null){
			map.put("eimpoDate", eimpoDate);
		}
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getSize());
		List<Import> importList=importService.find(map);
		Long total=importService.getTotal(map);
		
		ListUtil listUtil=new ListUtil();
		List<Map<String,Object>> list=listUtil.listConvert(importList);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JsonUtil.ListToJson(list);
		result.put("rows", jsonArray);
		result.put("total",total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	@RequestMapping("/save")
	public String save(Import impt,HttpServletResponse response) throws Exception{
		int resultTotal=0;//初始化操作记录数
		if(impt.getId()==null){
			resultTotal=importService.add(impt);
		}else{
			resultTotal=importService.update(impt);
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
	public String delete(@RequestParam(value="delIds",required=false) String delIds,HttpServletResponse response) throws Exception{
		JSONObject jsonObject=new JSONObject();
		int delNums=0;
		String[] idStr=delIds.split(",");
		for(int i=0;i<idStr.length;i++){
			Integer importId=Integer.valueOf(idStr[i]);
			Long n=stockService.getStockByImportId(importId);
			if(n>0){
				jsonObject.put("errorMsg", "商品库存量大于0，无法删除！");
				ResponseUtil.write(response, jsonObject);
			}else{
				importService.delete(Integer.valueOf(idStr[i]));
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
	
	@RequestMapping("/export")
	public String export(HttpServletResponse response) throws Exception{
		List<Import> exportList=importService.exportData();
		ListUtil listUtil=new ListUtil();
		List<Map<String, Object>> list=listUtil.listConvert(exportList);
		List<Map<String, Object>> finalList=ListUtil.formatList(list);
		Workbook wb=ExcelUtil.fillExcelDataWithTemplate(finalList, "importTemp.xls");
		ResponseUtil.export(response, wb, "导出入库信息.xls");
		return null;
	}
	
	@RequestMapping("/upload")
	public String upload(@RequestParam(value="userUploadFile")MultipartFile file,HttpServletResponse response) throws Exception{
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
		
		//定义标题名与对应的属性名
		Map<String,String> titleAndAttribute=new LinkedHashMap<String,String>();
		//此处只能对应数据库中t_import中的goodsId字段, 且导入goodsId只能对应t_goods中已有的id,否则会报如下错误：
		//Cannot add or update a child row:a foreign key constraint fails
		titleAndAttribute.put("商品名称", "goodsId");
		titleAndAttribute.put("入库价格", "impoPrice");
		titleAndAttribute.put("入库时间", "impoDate");
		titleAndAttribute.put("入库数量", "impoNum");
		titleAndAttribute.put("入库备注", "impoDesc");
		
		ExcelUtil<Import> excelUtil=new ExcelUtil<Import>();
		Class<Import> clazz=Import.class;
		List<Import> importList=excelUtil.getExcelInfo(file, titleAndAttribute, clazz);
		int resultTotal=importService.addImportList(importList);
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
