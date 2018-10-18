package com.test.crm.dao;

import java.util.List;

import com.test.crm.domain.Remark;

public interface ClueRemarkDao {

	int add(Remark clueRrematk);

	List<Remark> getRemarks(String p_clueid);

	int update(Remark clueRrematk);

	/**
	 * 
	 * @param id 备注id
	 * @return
	 */
	int delById(String id);

	/**
	 * 删除多个线索同时关联的多个备注
	 * @param p_clueId 线索id[]
	 */
	void delByClueId(String[] p_clueIds);

	/**
	 * 通过转换一条线索删除线索关联的备注
	 * @param p_clueId 线索id
	 */
	void delByClueId2(String p_clueId);

}
