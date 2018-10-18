package com.test.crm.dao;

import java.util.List;
import java.util.Set;

import com.test.crm.domain.DictionaryType;

public interface DictionaryTypeDao {

	List<DictionaryType> getDictionaryTypeList();

	int addDictionaryType(DictionaryType dictionaryType);

	DictionaryType getDictionaryTypeById(String id);

	int updateDictionaryType(DictionaryType dictionaryType);

	int delDictionaryType(String[] ids);

	int checkDictTypeByCode(String type);

	/**
	 * 返回value种所有typeSet
	 * @return
	 */
	Set<String> getKeys();

}
