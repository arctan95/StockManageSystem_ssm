package com.tan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tan.dao.ExportDao;
import com.tan.entity.Export;
import com.tan.service.ExportService;

@Service("ExportService")
public class ExportServiceImpl implements ExportService{

	
	@Resource
	private ExportDao exportDao;
	
	@Override
	public List<Export> find(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return exportDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return exportDao.getTotal(map);
	}

	@Override
	public int add(Export export) {
		// TODO Auto-generated method stub
		return exportDao.add(export);
	}

	@Override
	public int update(Export export) {
		// TODO Auto-generated method stub
		return exportDao.update(export);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return exportDao.delete(id);
	}

	@Override
	public List<Export> exportData() {
		// TODO Auto-generated method stub
		return exportDao.exportData();
	}

	@Override
	public Long getExportByGoodsId(Integer id) {
		// TODO Auto-generated method stub
		return exportDao.getExportByGoodsId(id);
	}

	@Override
	public Long getExportByStockId(Integer id) {
		// TODO Auto-generated method stub
		return exportDao.getExportByStockId(id);
	}

}
