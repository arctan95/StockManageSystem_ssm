package com.tan.dao;

import java.util.List;
import java.util.Map;

import com.tan.entity.Goods;

public interface GoodsDao {

	public List<Goods> find(Map<String,Object> map);
	
	public Long getTotal(Map<String,Object> map);
	
	public Goods findById(Integer id);
	
	public int add(Goods goods);
	
	public int update(Goods goods);
	
	public int delete(Integer id);
	
	public List<Goods> exportData();
	
	/**
	 * 根据供应商编号查询商品
	 * @param id
	 * @return
	 */
	public Long getGoodsByProId(Integer id);
	
	/**
	 * 根据商品类型编号查询商品
	 * @param id
	 * @return
	 */
	public Long getGoodsByTypeId(Integer id);
}
