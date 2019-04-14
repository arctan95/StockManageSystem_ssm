package com.tan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tan.dao.ImportDao;
import com.tan.entity.Import;
import com.tan.service.ImportService;

@Service("ImportService")
public class ImportServiceImpl implements ImportService{

	@Resource
	private ImportDao importDao;
	
	@Override
	public List<Import> find(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return importDao.find(map);
	}

	@Override
	public long getTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return importDao.getTotal(map);
	}

	@Override
	public int add(Import impt) {
		// TODO Auto-generated method stub
		return importDao.add(impt);
	}

	@Override
	public int update(Import impt) {
		// TODO Auto-generated method stub
		return importDao.update(impt);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return importDao.delete(id);
	}

	@Override
	public List<Import> exportData() {
		// TODO Auto-generated method stub
		return importDao.exportData();
	}

	@Override
	public int addImportList(List<Import> importList) {
		int total=0;
		for(Import impt:importList){
			importDao.add(impt);
			total++;
		}
		return total;
	}

	@Override
	public Long getImportByGoodsId(Integer id) {
		// TODO Auto-generated method stub
		return importDao.getImportByGoodsId(id);
	}

}
