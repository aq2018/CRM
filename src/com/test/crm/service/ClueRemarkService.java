package com.test.crm.service;

import java.util.List;

import com.test.crm.domain.Remark;

public interface ClueRemarkService {

	boolean add(Remark clueRrematk);

	List<Remark> getRemarks(String p_clueid);

	boolean update(Remark clueRrematk);
	/**
	 *根据备注id删除
	 * @param id
	 * @return
	 */
	boolean delById(String id);
	/**
	 * 根据线索id删除线索备注
	 * @param ids
	 */
	void delByClueId(String[] ids);

}
