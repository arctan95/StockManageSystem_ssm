package com.tan.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	public static JSONArray ListToJson(List<Map<String,Object>> list) throws Exception{
		JSONArray jsonArray=new JSONArray();
		for(Map<String,Object> map:list){
			JSONObject mapOfColValues=new JSONObject();
			for(String s:map.keySet()){
				Object object=map.get(s);
			if(object instanceof Date){
				mapOfColValues.put(s, DateUtil.formatDate((Date)object, "yyyy-MM-dd"));
			}else{
				mapOfColValues.put(s,map.get(s));
				}
			}
		jsonArray.add(mapOfColValues);
		}
	return jsonArray;
	}
}

