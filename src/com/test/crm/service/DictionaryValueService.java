package com.test.crm.service;

import java.util.List;
import java.util.Map;
import com.test.crm.domain.DictionaryValue;

public interface DictionaryValueService {

	List<DictionaryValue> getDictionaryValueList();

	/**
	 * 	返回字典id、type[{"id":"?","type":"?"},]
	 * @return
	 */
	List<DictionaryValue> getDictionaryTypeCodeList();

	/**
	 * 
	 * @param dictionaryValue
	 * 
	 * @param id value主键
	 * @param value 字典值
	 * @param content 文本
	 * @param sortNo 排序号
	 * @param pid_dictType 字典类型外键
	 * @return
	 */
	boolean addDictionaryValue(DictionaryValue dictionaryValue);

	/**
	 * 
	 * @param id 字典值id
	 * @return 字典值id、字典值value、文本content、排序号sortNo、字典类型编码type、字典类型pid
	 */
	Map<String, Object> editDictionaryValue(String id);

	boolean updateDictValue(DictionaryValue dictionaryValue);

	boolean delDictionaryValue(String[] ids);

	/**
	 * 
	 * @param typeid
	 * @param value
	 * @return true 字典值可用
	 */
	boolean checkUniqueByTypeAndValue(String typeid, String value);

	/**
	 * 
	 * @return value和content
	 */
	List<DictionaryValue> getCalls();

	List<DictionaryValue> getClueState();

	List<DictionaryValue> getClueSource();

	Map<String, List<DictionaryValue>> getAll();
	
	Map<String, List<DictionaryValue>> getAll2();

}
