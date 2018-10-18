package com.test.crm.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.test.crm.dao.DictionaryTypeDao;
import com.test.crm.dao.DictionaryValueDao;
import com.test.crm.domain.DictionaryValue;
import com.test.crm.service.DictionaryValueService;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.SqlSessionUtil;

public class DictionaryValueServiceImpl implements DictionaryValueService{
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		DictionaryValueServiceImpl d = new DictionaryValueServiceImpl();
		
//		JSONUtil.getJson(d.getKeys());
		Map<String, List<DictionaryValue>> all2 = d.getAll2();
		List<DictionaryValue> dvList = all2.get("transactionStageList");
		JSONUtil.getJson(dvList);
		
		String stage = null;
		for (DictionaryValue dv : dvList) {
			if("trans_estimate".equals(dv.getValue())){
				stage = dv.getContent();
				break;
			}
		}
		System.out.println(stage);
	}
	
	private DictionaryValueDao dictionaryValueDao = (DictionaryValueDao) SqlSessionUtil.getSqlSession().getMapper(DictionaryValueDao.class);
	private DictionaryTypeDao dtd = SqlSessionUtil.getSqlSession().getMapper(DictionaryTypeDao.class);

	@Override
	public List<DictionaryValue> getDictionaryValueList() {
		return dictionaryValueDao.getDictionaryValueList();
	}

	@Override
	public List<DictionaryValue> getDictionaryTypeCodeList() {
		return dictionaryValueDao.getDictionaryTypeCodeList();
	}

	@Override
	public boolean addDictionaryValue(DictionaryValue dictionaryValue) {
		return dictionaryValueDao.addDictionaryValue(dictionaryValue) == 1;
	}

	@Override
	public Map<String, Object> editDictionaryValue(String id) {
		return dictionaryValueDao.editDictionaryValue(id);
	}

	@Override
	public boolean updateDictValue(DictionaryValue dictionaryValue) {
		return dictionaryValueDao.updateDictValue(dictionaryValue) == 1;
	}

	@Override
	public boolean delDictionaryValue(String[] ids) {
		return dictionaryValueDao.delDictionaryValue(ids) == ids.length;
	}

	@Override
	public boolean checkUniqueByTypeAndValue(String typeid, String value) {
		return dictionaryValueDao.checkUniqueByTypeAndValue(typeid,value) == 0;
	}

	@Override
	public List<DictionaryValue> getCalls() {
 		return dictionaryValueDao.getCalls();
	}

	@Override
	public List<DictionaryValue> getClueState() {
		return dictionaryValueDao.getClueState();
	}

	@Override
	public List<DictionaryValue> getClueSource() {
		return dictionaryValueDao.getClueSource();
	}

	@Override
	public Map<String, List<DictionaryValue>> getAll() {
		Set<String> keys = dtd.getKeys();
		Map<String, List<DictionaryValue>> dvMap = new HashMap<>();
		dvMap.put("callsList", dictionaryValueDao.getCalls());
		dvMap.put("clueSourceList", dictionaryValueDao.getClueSource());
		dvMap.put("clueStateList", dictionaryValueDao.getClueState());
		dvMap.put("stageList", dictionaryValueDao.getStage());
		return dvMap;
	}
	public Map<String, List<DictionaryValue>> getAll2() {
		Set<String> keys = dtd.getKeys();
		Map<String, List<DictionaryValue>> dvMap = new HashMap<>();
		for (String key : keys) {
			List<DictionaryValue> valueList = dictionaryValueDao.listValueByType(key);
			dvMap.put(key+"List", valueList);
		}
		return dvMap;
	}
}
