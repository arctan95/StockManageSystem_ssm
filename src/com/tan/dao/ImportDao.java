package com.tan.dao;

import java.util.List;
import java.util.Map;

import com.tan.entity.Import;

public interface ImportDao {

	
	public List<Import> find(Map<String,Object> map);
	
	public long getTotal(Map<String,Object> map);
	
	public int add(Import impt);
	
	public int update(Import impt);
	
	public int delete(Integer id);
	
	public List<Import> exportData();

	/**
	 * 根据商品编号goodsId查询入库商品
	 * @param id
	 * @return
	 */
	public Long getImportByGoodsId(Integer id);
}
