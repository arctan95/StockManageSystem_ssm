package com.tan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tan.dao.ExpireDao;
import com.tan.service.ExpireService;

@Service("ExpireService")
public class ExpireServiceImpl implements ExpireService{

	
	@Resource
	private ExpireDao expireDao;
	
	@Override
	public List<Map<String, Object>> find(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return expireDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return expireDao.getTotal(map);
	}

}
