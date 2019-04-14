package com.tan.dao;


import java.util.List;
import java.util.Map;

import com.tan.entity.Provider;

public interface ProviderDao {

	/**
	 * 供应商查询
	 * @param map
	 * @return 供应商集合
	 */
	public List<Provider> find(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return 获取的total数
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 根据主键id查询
	 * @param id
	 * @return
	 */
	public Provider findById(Integer id);
	
	/**
	 * 添加供应商
	 * @param provider
	 * @return
	 */
	
	public int add(Provider provider);
	
	/**
	 * 修改供应商
	 * @param provider
	 * @return
	 */
	public int update(Provider provider);
	
	/**
	 * 删除供应商
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}
