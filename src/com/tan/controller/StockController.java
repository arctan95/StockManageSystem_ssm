package com.tan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tan.entity.PageBean;
import com.tan.entity.Stock;
import com.tan.service.ExportService;
import com.tan.service.StockService;
import com.tan.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/stock")
public class StockController {

	@Resource
	private StockService stockService;
	@Resource
	private ExportService exportService;
	
	@RequestMapping("/list")
	public String find(@RequestParam(value="bimpoPrice",required=false)String bimpoPrice,@RequestParam(value="eimpoPrice",required=false)String eimpoPrice,@RequestParam(value="bexpoPrice",required=false)String bexpoPrice,@RequestParam(value="eexpoPrice",required=false)String eexpoPrice,@RequestParam(value="rows",required=false) String rows,@RequestParam(value="page",required=false) String page,@RequestParam(value="goodsName",required=false)String goodsName,HttpServletResponse response) throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		if(goodsName!=null){
			map.put("goodsName", goodsName);
		}
		if(bimpoPrice!=null&&bimpoPrice!=""){
			map.put("bimpoPrice", Integer.parseInt(bimpoPrice));
		}
		if(eimpoPrice!=null&&eimpoPrice!=""){
			map.put("eimpoPrice", Integer.parseInt(eimpoPrice));
		}
		if(bexpoPrice!=null&&bexpoPrice!=""){
			map.put("bexpoPrice", Integer.parseInt(bexpoPrice));
		}
		if(eexpoPrice!=null&&eexpoPrice!=""){
			map.put("eexpoPrice", Integer.parseInt(eexpoPrice));
		}
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getSize());
		List<Stock> stockList=stockService.find(map);
		Long total=stockService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(stockList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
	
	
	@RequestMapping("/save")
	public String save(Stock stock,HttpServletResponse response) throws Exception{
		int resultTotal=0;//初始化操作记录数
		if(stock.getId()==null){
			resultTotal=stockService.add(stock);
		}else{
			resultTotal=stockService.update(stock);
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
		//解决外键约束问题
		for(int i=0;i<idStr.length;i++){
			//判断出库记录数据库中是否含有该商品
			Integer stockId=Integer.valueOf(idStr[i]);
			Long n=exportService.getExportByStockId(stockId);
			if(n>0){
				jsonObject.put("errorMsg", "存在于出库记录中，无法删除!");
				ResponseUtil.write(response, jsonObject);
			}else{
				stockService.delete(Integer.valueOf(idStr[i]));
				delNums++;
			}
		}
		if(delNums>0){
			jsonObject.put("success", true);
			jsonObject.put("delNums",delNums);
		}else{
			jsonObject.put("success", false);
			jsonObject.put("errorMsg", "删除失败！");
		}
		ResponseUtil.write(response, jsonObject);
		return null;
	}
}
