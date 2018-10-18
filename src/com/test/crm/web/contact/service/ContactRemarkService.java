package com.test.crm.web.contact.service;

import java.util.List;
import com.test.crm.domain.Remark;

public interface ContactRemarkService {
	/**
	 * 添加一条备注
	 * @param clueRrematk
	 * @return
	 */
	boolean add(Remark clueRrematk);

	List<Remark> getRemark(String contactId);

	boolean update(Remark contactRrematk);
	/**
	 * 根据备注id删除
	 * @param id
	 * @return
	 */
	boolean delById(String id);
	

}
