package com.tan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tan.dao.StockDao;
import com.tan.entity.Stock;
import com.tan.service.StockService;


@Service("StockService")
public class StockServiceImpl implements StockService{

	@Resource
	private StockDao stockDao;
	
	@Override
	public List<Stock> find(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return stockDao.find(map);
	}

	@Override
	public long getTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return stockDao.getTotal(map);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return stockDao.delete(id);
	}

	@Override
	public int add(Stock stock) {
		// TODO Auto-generated method stub
		return stockDao.add(stock);
	}

	@Override
	public int update(Stock stock) {
		// TODO Auto-generated method stub
		return stockDao.update(stock);
	}

	@Override
	public Long getStockByGoodsId(Integer id) {
		// TODO Auto-generated method stub
		return stockDao.getStockByGoodsId(id);
	}

	@Override
	public Long getStockByImportId(Integer id) {
		// TODO Auto-generated method stub
		return stockDao.getStockByImportId(id);
	}

}
