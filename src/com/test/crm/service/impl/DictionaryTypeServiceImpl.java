package com.test.crm.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.test.crm.dao.DictionaryTypeDao;
import com.test.crm.domain.DictionaryType;
import com.test.crm.service.DictionaryTypeService;
import com.test.crm.util.SqlSessionUtil;

public class DictionaryTypeServiceImpl implements DictionaryTypeService {
	public static void main(String[] args) {
		DictionaryTypeServiceImpl df = new DictionaryTypeServiceImpl();
		DictionaryTypeDao dictonaryDao = SqlSessionUtil.getSqlSession().getMapper(DictionaryTypeDao.class);
		Set<String> keys = dictonaryDao.getKeys();
		for (String key : keys) {
			System.out.println(key);
		}
	}
	private DictionaryTypeDao dictonaryDao = SqlSessionUtil.getSqlSession().getMapper(DictionaryTypeDao.class);

	@Override
	public Map<String, Object> getDictionaryTypeList() {
		Map<String, Object> map = new HashMap<>();
		map.put("dictList", dictonaryDao.getDictionaryTypeList());
		return map;
	}

	@Override
	public boolean addDictionaryType(DictionaryType dictionaryType) {
		System.out.println("添加");
		return dictonaryDao.addDictionaryType(dictionaryType) == 1;
	}

	@Override
	public DictionaryType getDictionaryTypeById(String id) {
		return dictonaryDao.getDictionaryTypeById(id);
	}

	@Override
	public boolean updateDictionaryType(DictionaryType dictionaryType) {
		return dictonaryDao.updateDictionaryType(dictionaryType) == 1;
	}

	@Override
	public boolean delDictionaryType(String[] ids) {
		return dictonaryDao.delDictionaryType(ids) == ids.length;
	}

	@Override
	public boolean checkDictTypeByCode(String type) {
		return dictonaryDao.checkDictTypeByCode(type) == 0;
	}

}
