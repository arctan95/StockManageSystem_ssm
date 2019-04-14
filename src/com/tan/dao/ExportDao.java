package com.tan.dao;

import java.util.List;
import java.util.Map;

import com.tan.entity.Export;

public interface ExportDao {

	
	public List<Export> find(Map<String,Object> map);
	
	public Long getTotal(Map<String,Object> map);
	
	public List<Export> exportData();
	
	public int add(Export export);
	
	public int update(Export export);
	
	public int delete(Integer id);

	/**
	 * 根据商品编号goodsId查询出库商品
	 * @param id
	 * @return
	 */
	public Long getExportByGoodsId(Integer id);
	
	public Long getExportByStockId(Integer id);
}
