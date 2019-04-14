package com.tan.dao;

import java.util.List;
import java.util.Map;

import com.tan.entity.GoodsType;

public interface GoodsTypeDao {

	/**
	 * 商品类别查询
	 * @param map
	 * @return 商品类别集合
	 */
	public List<GoodsType> find(Map<String,Object> map);
	
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
	public GoodsType findById(Integer id);
	
	/**
	 * 添加商品类别
	 * @param goodsType
	 * @return
	 */
	public int add(GoodsType goodsType);
	
	/**
	 * 修改商品类别
	 * @param goodsType
	 * @return
	 */
	public int update(GoodsType goodsType);
	
	/**
	 * 删除商品类别
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}
