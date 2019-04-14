package com.tan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tan.dao.GoodsDao;
import com.tan.entity.Goods;
import com.tan.service.GoodsService;

@Service("GoodsService")
public class GoodsServiceImpl implements GoodsService{

	@Resource
	private GoodsDao goodsDao;
	
	@Override
	public List<Goods> find(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return goodsDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return goodsDao.getTotal(map);
	}

	@Override
	public int add(Goods goods) {
		// TODO Auto-generated method stub
		return goodsDao.add(goods);
	}

	@Override
	public int update(Goods goods) {
		// TODO Auto-generated method stub
		return goodsDao.update(goods);
	}

	@Override
	public int delete(Integer id) {
		return goodsDao.delete(id);
	}

	@Override
	public List<Goods> exportData() {
		// TODO Auto-generated method stub
		return goodsDao.exportData();
	}

	@Override
	public int addGoodsList(List<Goods> goodsList) {
		int total=0;
		for(Goods goods:goodsList){
			goodsDao.add(goods);
			total++;
		}
		return total;
	}

	@Override
	public Long getGoodsByProId(Integer id) {
		// TODO Auto-generated method stub
		return goodsDao.getGoodsByProId(id);
	}

	@Override
	public Long getGoodsByTypeId(Integer id) {
		// TODO Auto-generated method stub
		return goodsDao.getGoodsByTypeId(id);
	}


}
