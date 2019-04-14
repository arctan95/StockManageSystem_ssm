package com.tan.dao;

import java.util.List;
import java.util.Map;

public interface ProfitDao {

	public List<Map<String,Object>> find(Map<String,Object> map);
	
	public Long getTotal(Map<String,Object> map);
	
	public Long profitTotal(Map<String,Object> map);
}
