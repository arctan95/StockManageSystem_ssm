package com.tan.dao;

import java.util.List;
import java.util.Map;

import com.tan.entity.Stock;

public interface StockDao {

	public List<Stock> find(Map<String,Object> map);
	
	public long getTotal(Map<String,Object> map);
	
	public int add(Stock stock);
	
	public int update(Stock stock);
	
	public int delete(Integer id);
	
	/**
	 * 根据商品编号goodsId查询库存中的商品
	 * @param id
	 * @return
	 */
	public Long getStockByGoodsId(Integer id);
	
	public Long getStockByImportId(Integer id);
}
