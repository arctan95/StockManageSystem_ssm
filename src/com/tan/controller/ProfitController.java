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
import com.tan.service.ProfitService;
import com.tan.util.ExcelUtil;
import com.tan.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/profit")
public class ProfitController {

	@Resource
	private ProfitService profitService;
	
	@RequestMapping("/list")
	public String find(@RequestParam(value="goodsName",required=false)String goodsName,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletResponse response) throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		if(goodsName!=null){
			map.put("goodsName", goodsName);
		}
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getSize());
		List<Map<String,Object>> list=profitService.find(map);
		Long total=profitService.getTotal(map);
		Long profitTotal=profitService.profitTotal(map);
		
		JSONObject result=new JSONObject();
		JSONObject jsonFotter=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(list);
		JSONArray jsonArrFotter=new JSONArray();
		jsonFotter.put("impoPrice","所有商品利润");
		jsonFotter.put("profit", profitTotal);
		jsonArrFotter.add(jsonFotter);
		result.put("rows", jsonArray);
		result.put("total", total);
		result.put("footer", jsonArrFotter);
		ResponseUtil.write(response, result);
		return null;
	}
	
	@RequestMapping("/export")
	public String export(HttpServletResponse response) throws Exception{
		Workbook wb=new HSSFWorkbook();
		String[] headers={"编号","商品编号","商品名称","商品售出数量",	"商品成本价","商品出售价 ","商品利润"};
		String[] columns={"id","goodsId","goodsName","expoNum","impoPrice","expoPrice","profit"};
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("goodsName", "");
		List<Map<String,Object>> exportList=profitService.find(map);
		ExcelUtil.fillExcelData(exportList, wb, headers,columns);
		ResponseUtil.export(response, wb, "导出利润统计信息.xls");
		return null;
	}
}
