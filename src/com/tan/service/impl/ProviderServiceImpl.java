package com.tan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.tan.dao.ProviderDao;
import com.tan.entity.Provider;
import com.tan.service.ProviderService;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService{

	@Resource
	private ProviderDao providerDao;

	@Override
	public List<Provider> find(Map<String, Object> map) {
		return providerDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return providerDao.getTotal(map);
	}

	@Override
	public int add(Provider provider) {
		return providerDao.add(provider);
	}

	@Override
	public int update(Provider provider) {
		return providerDao.update(provider);
	}

	@Override
	public int delete(Integer id) {
		return providerDao.delete(id);
	}



}
	
