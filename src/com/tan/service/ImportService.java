package com.tan.service;

import java.util.List;
import java.util.Map;

import com.tan.entity.Import;

public interface ImportService {

	
	public List<Import> find(Map<String,Object> map);
	
	public long getTotal(Map<String,Object> map);
	
	public int add(Import impt);
	
	public int update(Import impt);
	
	public int delete(Integer id);
	
	public List<Import> exportData();
	
	
	/**
	 * 从Excel导入数据
	 * @param importList
	 * @return
	 */
	public int addImportList(List<Import> importList);
	
	public Long getImportByGoodsId(Integer id);
}
