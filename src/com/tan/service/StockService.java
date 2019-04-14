package com.tan.service;

import java.util.List;
import java.util.Map;

import com.tan.entity.Stock;

public interface StockService {

	public List<Stock> find(Map<String,Object> map);
	
	public long getTotal(Map<String,Object> map);
	
	public int add(Stock stock);
	
	public int update(Stock stock);
	
	public int delete(Integer id);
	
	public Long getStockByGoodsId(Integer id);
	
	public Long getStockByImportId(Integer id);
}
