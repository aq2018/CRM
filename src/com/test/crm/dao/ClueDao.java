package com.test.crm.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Clue;

public interface ClueDao {

	Long getTotal(Map<String, Object> condition);

	List<Clue> getList(Map<String, Object> condition);

	List<Clue> getById(String[] ids);

	int update(Clue clue);

	/**
	 * 通过id删除多条线索
	 * @param ids
	 */
	void del(String[] ids);

	List<Clue> getAll();

	int add(List<Clue> listClue);

	/**
	 * 通过线索id获取线索
	 * @param id
	 * @return *
	 */
	Clue getByid2(String id);

	/**
	 * 通过id删除一条
	 * @param id
	 */
	void delById(String id);

}
