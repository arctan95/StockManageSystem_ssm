package com.tan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tan.dao.ProfitDao;
import com.tan.service.ProfitService;

@Service("ProfitService")
public class ProfitServiceImpl implements ProfitService{

	@Resource
	private ProfitDao profitDao;
	
	@Override
	public List<Map<String, Object>> find(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return profitDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return profitDao.getTotal(map);
	}

	@Override
	public Long profitTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return profitDao.profitTotal(map);
	}

}
