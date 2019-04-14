package com.tan.service;

import java.util.List;
import java.util.Map;

public interface WarnService {

	public List<Map<String,Object>> find(Map<String,Object> map);
	
	public Long getTotal(Map<String,Object> map);
	
	public Long warnTotal(Map<String,Object> map);
}
