package com.test.crm.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.DictionaryValue;

public interface DictionaryValueDao {

	List<DictionaryValue> getDictionaryValueList();

	List<DictionaryValue> getDictionaryTypeCodeList();

	int addDictionaryValue(DictionaryValue dictionaryValue);

	Map<String, Object> editDictionaryValue(String id);

	int updateDictValue(DictionaryValue dictionaryValue);

	int delDictionaryValue(String[] ids);

	int checkUniqueByTypeAndValue(String typeid, String value);

	List<DictionaryValue> getCalls();

	List<DictionaryValue> getClueState();

	List<DictionaryValue> getClueSource();

	List<DictionaryValue> getStage();

	/**
	 * 通过type 返回value集合
	 * @param key
	 * @return
	 */
	List<DictionaryValue> listValueByType(String type);
}
