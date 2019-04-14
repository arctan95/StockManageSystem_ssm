package com.tan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tan.entity.PageBean;
import com.tan.service.WarnService;
import com.tan.util.ExcelUtil;
import com.tan.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/warn")
public class WarnController {

	@Resource
	private WarnService warnService;
	
	@RequestMapping("/list")
	public String find(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,@RequestParam(value="goodsName",required=false)String goodsName,HttpServletResponse response) throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		if(goodsName!=null){
			map.put("goodsName", goodsName);
		}
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getSize());
		List<Map<String,Object>> warnList=warnService.find(map);
		Long total=warnService.getTotal(map);
		Long warnTotal=warnService.warnTotal(map);
		
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(warnList);
		JSONObject jsonFooter=new JSONObject();
		JSONArray jsonArrFooter=new JSONArray();
		jsonFooter.put("goodsName", "需要进货商品数");
		jsonFooter.put("stockNum", warnTotal);
		jsonArrFooter.add(jsonFooter);
		result.put("rows", jsonArray);
		result.put("total", total);
		result.put("footer", jsonArrFooter);
		ResponseUtil.write(response, result);
		return null;
	}
	
	@RequestMapping("/export")
	public String export(HttpServletResponse response) throws Exception{
		Workbook wb=new HSSFWorkbook();
		String[] headers="编号,商品编号,商品名称,商品库存数量,商品最低库存限制".split(",");
		String[] columns="id,goodsId,goodsName,stockNum,limitNum".split(",");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("goodsName", "");
		List<Map<String,Object>> exportList=warnService.find(map);
		ExcelUtil.fillExcelData(exportList, wb, headers, columns);
		ResponseUtil.export(response, wb, "导出库存预警信息.xls");
		return null;
	}
}
