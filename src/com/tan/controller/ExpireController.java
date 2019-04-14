package com.tan.controller;

import java.util.Calendar;
import java.util.Date;
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
import com.tan.service.ExpireService;
import com.tan.util.DateUtil;
import com.tan.util.ExcelUtil;
import com.tan.util.JsonUtil;
import com.tan.util.ListUtil;
import com.tan.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/expire")
public class ExpireController {

	@Resource
	private ExpireService expireService;
	
	@RequestMapping("/list")
	public String find(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,@RequestParam(value="goodsName",required=false)String goodsName,HttpServletResponse response) throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		if(goodsName!=null){
			map.put("goodsName", goodsName);
		}
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getSize());
		
		List<Map<String,Object>> expireList=expireService.find(map);
		Long total=expireService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JsonUtil.ListToJson(expireList);
		JSONObject jsonFooter=new JSONObject();
		JSONArray jsonArrFooter=new JSONArray();
		
		Calendar calendar=Calendar.getInstance();
		Calendar now=Calendar.getInstance();
		int expireTotal=0;
		for(int i=0;i<jsonArray.size();i++){
			JSONObject each=(JSONObject) jsonArray.get(i);
			int expireTime=each.getInt("expireTime");
			calendar.setTime(DateUtil.formatString(each.getString("impoDate"), "yyyy-MM-dd"));
			calendar.add(Calendar.MONTH, expireTime);
			now.setTime(new Date());
			
			if(now.compareTo(calendar)<=0){
				each.put("proLeaveTime", DateUtil.differentDays(now, calendar));
				each.put("proIsOverTime", "未过期");
			}else{
				each.put("proIsOverTime", "过期");
				expireTotal++;
			}
		}
		
		jsonFooter.put("goodsName", "过期商品个数");
		jsonFooter.put("impoDate", expireTotal);
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
		String[] headers={"库存编号","商品名称","入库时间","保质期","剩余过期天数","是否过期"};
		String[] columns={"id","goodsName","impoDate","expireTime","proLeaveTime","proIsOverTime"};
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("goodsName", "");
		List<Map<String,Object>> exportList=expireService.find(map);
		List<Map<String,Object>> list=ListUtil.formatList(exportList);
		Calendar calendar=Calendar.getInstance();
		Calendar now=Calendar.getInstance();
		for(int i=0;i<list.size();i++){
			Map<String,Object> listMap=list.get(i);
			int expireTime=Integer.parseInt(listMap.get("expireTime").toString());
			calendar.setTime(DateUtil.formatString(listMap.get("impoDate").toString(), "yyyy-MM-dd"));
			calendar.add(Calendar.MONTH, expireTime);
			now.setTime(new Date());
			
			if(now.compareTo(calendar)<=0){
				listMap.put("proLeaveTime", DateUtil.differentDays(now, calendar));
				listMap.put("proIsOverTime", "未过期");
			}else{
				listMap.put("proLeaveTime", "");
				listMap.put("proIsOverTime", "过期");
			}
		}
		ExcelUtil.fillExcelData(list, wb, headers, columns);
		ResponseUtil.export(response, wb, "导出保质期预警信息.xls");
		return null;
	}
}
