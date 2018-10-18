package com.test.crm.web.transaction.dao;

import java.util.List;

import com.test.crm.domain.Remark;

public interface TransRemarkDao {

	/**
	 * 返回客户名称
	 * @param transId
	 * @return remarkList customerId:客户名称
	 */
	List<Remark> listByCustomerId(String transId);

	int save(Remark remark);

	/**
	 * 通过备注id删除
	 * @param id
	 * @return
	 */
	int delById(String id);

	/**
	 * 通过备注id更新
	 * @param remark
	 * @return
	 */
	int update(Remark remark);

	/**
	 * 通过交易id删除关联的备注
	 * @param transId[]
	 */
	void delByTransIds(String[] transId);

	/**
	 * 通过交易id删除关联的备注
	 * @param transId
	 */
	void delByTransId(String transId);

}
