package com.tan.service;

import java.util.List;
import java.util.Map;

import com.tan.entity.Export;

public interface ExportService {

	
	public List<Export> find(Map<String,Object> map);
	
	public Long getTotal(Map<String,Object> map);
	
	public List<Export> exportData();
	
	public int add(Export export);
	
	public int update(Export export);
	
	public int delete(Integer id);	
	
	public Long getExportByGoodsId(Integer id);
	
	public Long getExportByStockId(Integer id);
}
