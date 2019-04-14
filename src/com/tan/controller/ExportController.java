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

import com.tan.entity.Export;
import com.tan.entity.PageBean;
import com.tan.service.ExportService;
import com.tan.util.ExcelUtil;
import com.tan.util.JsonUtil;
import com.tan.util.ListUtil;
import com.tan.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/export")
public class ExportController {

	@Resource
	private ExportService exportService;
	
	@RequestMapping("/list")
	public String find(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,@RequestParam(value="bexpoPrice",required=false)String bexpoPrice,@RequestParam(value="eexpoPrice",required=false)String eexpoPrice,@RequestParam(value="bexpoDate",required=false)String bexpoDate,@RequestParam(value="eexpoDate",required=false)String eexpoDate,@RequestParam(value="goodsName",required=false)String goodsName,HttpServletResponse response) throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		if(goodsName!=null){
			map.put("goodsName", goodsName);
		}
		if(bexpoPrice!=null&&bexpoPrice!=""){
			map.put("bexpoPrice", Integer.parseInt(bexpoPrice));
		}
		if(eexpoPrice!=null&&eexpoPrice!=""){
			map.put("eexpoPrice", Integer.parseInt(eexpoPrice));
		}
		if(bexpoDate!=null){
			map.put("bexpoDate", bexpoDate);
		}
		if(eexpoDate!=null){
			map.put("eexpoDate", eexpoDate);
		}
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getSize());
		List<Export> exportList=exportService.find(map);
		Long total=exportService.getTotal(map);
		
		ListUtil listUtil=new ListUtil();
		List<Map<String,Object>> list=listUtil.listConvert(exportList);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JsonUtil.ListToJson(list);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	@RequestMapping("/save")
	public String save(Export export,HttpServletResponse response) throws Exception{
		int resultTotal=0;//初始化操作记录数
		if(export.getId()==null){
			resultTotal=exportService.add(export);
		}else{
			resultTotal=exportService.update(export);
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
	public String delete(@RequestParam(value="delIds",required=false)String delIds,HttpServletResponse response) throws Exception{
		JSONObject jsonObject=new JSONObject();
		int delNums=0;
		String[] idStr=delIds.split(",");
		for(String id:idStr){
			exportService.delete(Integer.parseInt(id));
			delNums++;
		}
		if(delNums>0){
			jsonObject.put("success", true);
			jsonObject.put("delNums", delNums);
		}else{
			jsonObject.put("success", false);
			jsonObject.put("errorMsg", "删除失败!");
		}
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	@RequestMapping("/export")
	public String export(HttpServletResponse response) throws Exception{
		List<Export> exportList=exportService.exportData();
		ListUtil listUtil=new ListUtil();
		List<Map<String,Object>> list=listUtil.listConvert(exportList);
		List<Map<String,Object>> finalList=ListUtil.formatList(list);
		Workbook wb=ExcelUtil.fillExcelDataWithTemplate(finalList, "exportTemp.xls");
		ResponseUtil.export(response, wb, "导出出库记录.xls");
		return null;
	}
}
