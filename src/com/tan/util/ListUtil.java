package com.tan.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ListUtil{

	/**
	 * 把List<Object>转换成Map<String,Object>形式
	 * @param list
	 * @return
	 */
	
	//泛型方法
	public <T> List<Map<String,Object>> listConvert(List<T> list){
		List<Map<String,Object>> list_map=new ArrayList<Map<String,Object>>();
        try {
            for (T t : list) {
            	Field[] fields=t.getClass().getDeclaredFields();
            	//创建一属性描述器
            	Map<String, Object> m = new LinkedHashMap<String, Object>();//用LinkedHashMap是为了保证添加的顺序不错乱
            	for(Field field:fields){
            		String keyName=field.getName();
            		PropertyDescriptor pd = new PropertyDescriptor(keyName,t.getClass());
            		Method getMethod = pd.getReadMethod();// 获得getter方法
            		Object o = getMethod.invoke(t);// 执行getter方法返回一个Object
            		m.put(keyName, o);
            		}
            	list_map.add(m);
            }
            return list_map;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }
	
	/**
	 * 将List中的Date类型格式化为字符串
	 * @param list
	 * @return
	 */
	public static List<Map<String,Object>> formatList(List<Map<String,Object>> list){
		List<Map<String,Object>> newList=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:list){
			Map<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			for(Map.Entry<String, Object> m:map.entrySet()){
				Object object=m.getValue();
			if(object instanceof Date){
				linkedHashMap.put(m.getKey(), DateUtil.formatDate((Date)object, "yyyy-MM-dd"));
			}else{
				linkedHashMap.put(m.getKey(),m.getValue());
				}
			}
			newList.add(linkedHashMap);
		}
	return newList;
	}
}
