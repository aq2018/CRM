package com.test.crm.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Clue;
import com.test.crm.domain.Transaction;

public interface ClueService {
	/**
	 * 
	 * @param clue
	 * @return 1 添加成功
	 */

	Map<String, Object> doGetByCondition(Map<String, Object> condition);

	List<Clue> getById(String[] ids);

	boolean update(Clue clue);

	void del(String[] ids);

	List<Clue> getAll();
	/**
	 * 
	 * @param listClue 线索列表,可导入添加
	 * @return
	 */
	boolean add(List<Clue> listClue);

	/**
	 * 通过线索id转换线索为联系人和客户
	 * flag为1 创建交易
	 * @param id
	 */
	void convert(String id, String operator, Transaction transaction);

}
