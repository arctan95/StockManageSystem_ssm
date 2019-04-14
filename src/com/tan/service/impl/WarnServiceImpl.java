package com.tan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tan.dao.WarnDao;
import com.tan.service.WarnService;

@Service("WarnService")
public class WarnServiceImpl implements WarnService{

	
	@Resource
	private WarnDao warnDao;
	
	@Override
	public List<Map<String, Object>> find(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return warnDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return warnDao.getTotal(map);
	}

	@Override
	public Long warnTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return warnDao.warnTotal(map);
	}

}
