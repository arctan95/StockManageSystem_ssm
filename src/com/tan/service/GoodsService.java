package com.tan.service;

import java.util.List;
import java.util.Map;

import com.tan.entity.Goods;

public interface GoodsService {

	public List<Goods> find(Map<String,Object> map);
	
	public Long getTotal(Map<String,Object> map); 
	
	public int add(Goods goods);
	
	public int update(Goods goods);
	
	public int delete(Integer id);
	
	public List<Goods> exportData();
	
	
	/**
	 * 从Excel导入数据
	 * @param goodsList
	 * @return
	 */
	//由于需要写入数据库，故方法名addGoodsList应该按照applicationContext.xml中事务传播属性规则来命名，否则按照默认的配置，对数据库访问的权限为read-only,报如下错误:
	//org.hibernate.exception.GenericJDBCException: Connection is read-only. Queries leading to data modification are not allowed
	public int addGoodsList(List<Goods> goodsList);
	
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
