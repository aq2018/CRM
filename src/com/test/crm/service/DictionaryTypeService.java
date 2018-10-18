package com.test.crm.service;

import java.util.Map;
import com.test.crm.domain.DictionaryType;

public interface DictionaryTypeService {

	Map<String, Object> getDictionaryTypeList();

	boolean addDictionaryType(DictionaryType dictionaryType);

	DictionaryType getDictionaryTypeById(String id);

	boolean updateDictionaryType(DictionaryType dictionaryType);

	boolean delDictionaryType(String[] ids);

	/**
	 * 
	 * @param id
	 * @return 0 返回true 该编码可用
	 */
	boolean checkDictTypeByCode(String code);

}
