package com.test.crm.web.contact.dao;

import java.util.List;
import com.test.crm.domain.Remark;

public interface ContactRemarkDao {

	int add(Remark contactRrematk);

	List<Remark> getRemark(String contactId);

	int update(Remark contactRrematk);
	/**
	 * 根据备注id删除
	 * @param id
	 * @return
	 */
	boolean delById(String id);

	/**
	 * 线索备注转到联系人备注
	 * @param listremark
	 */
	void save(List<Remark> listremark);

	/**
	 * 通过联系人id批量删除联系人相关备注
	 * @param ids
	 */
	void delByContactIds(String[] ids);

	/**
	 * 通过联系人id删除备注
	 * @param contactId
	 */
	void delByContactId(String contactId);

}
