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

import com.tan.entity.PageBean;
import com.tan.entity.Provider;
import com.tan.service.GoodsService;
import com.tan.service.ProviderService;
import com.tan.util.ExcelUtil;
import com.tan.util.ListUtil;
import com.tan.util.ResponseUtil;
import com.tan.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/provider")
	public class ProviderController {

	@Resource
	private ProviderService providerService;
	@Resource
	private GoodsService goodsService;
	
	/**
	 * 供应商分页查询
	 * @param page
	 * @param rows
	 * @param s_provider
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false) String page,@RequestParam(value="rows",required=false) String rows,Provider s_provider,HttpServletResponse response) throws Exception{
		//33至37行把前台分页数据传递到dao层供获取供应商信息用
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		if(s_provider.getProName()!=null){
			map.put("proName", StringUtil.formatLike(s_provider.getProName()));
		}
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getSize());
		List<Provider> providerList=providerService.find(map);
		Long total = providerService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(providerList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response,result);
		return null;
	}
	
	/**
	 * 添加或修改
	 * @param provider
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Provider provider,HttpServletResponse response) throws Exception{
		//操作记录数，初始化为0
		int resultTotal=0;
		if(provider.getId()==null){
			resultTotal=providerService.add(provider);
		}else{
			resultTotal=providerService.update(provider);
		}
		JSONObject jsonObject=new JSONObject();
		if(resultTotal>0){//说明修改或添加成功
			jsonObject.put("success", true);
		}else{
			jsonObject.put("success", false);
		}
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 删除供应商
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
			//判断商品数据库中是否含有该供应商
			Integer proId=Integer.valueOf(idStr[i]);
			Long n=goodsService.getGoodsByProId(proId);
			if(n>0){
				jsonObject.put("errorIndex", i);
				jsonObject.put("errorMsg", "存在于商品中，不能删除！");
				ResponseUtil.write(response, jsonObject);
			}else{
				providerService.delete(Integer.valueOf(idStr[i]));
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
	
	
	@RequestMapping("/providerComboList")
	public String providerComboList(HttpServletResponse response) throws Exception{
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		jsonObject.put("id", "");
		jsonObject.put("proName", "请选择...");
		jsonArray.add(jsonObject);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("proName", "");
		List<Provider> providerComboList=providerService.find(map);
		jsonArray.addAll(providerComboList);
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
		map.put("proName", "");
		List<Provider> exportList=providerService.find(map);
		ListUtil listUtil=new ListUtil();
		List<Map<String,Object>> list=listUtil.listConvert(exportList);
		Workbook wb=ExcelUtil.fillExcelDataWithTemplate(list, "providerTemp.xls");
		ResponseUtil.export(response, wb, "导出供应商.xls");
		return null;
	}
}
