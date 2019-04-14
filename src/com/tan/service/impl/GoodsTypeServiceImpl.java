package com.tan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tan.dao.GoodsTypeDao;
import com.tan.entity.GoodsType;
import com.tan.service.GoodsTypeService;

@Service("GoodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService{

	@Resource
	private GoodsTypeDao goodsTypeDao;
	
	@Override
	public List<GoodsType> find(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return goodsTypeDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return goodsTypeDao.getTotal(map);
	}

	@Override
	public int add(GoodsType goodsType) {
		// TODO Auto-generated method stub
		return goodsTypeDao.add(goodsType);
	}

	@Override
	public int update(GoodsType goodsType) {
		// TODO Auto-generated method stub
		return goodsTypeDao.update(goodsType);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return goodsTypeDao.delete(id);
	}

}
