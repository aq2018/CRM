package com.test.crm.web.transaction.service;

import java.util.List;
import com.test.crm.domain.Remark;

public interface TransRemarkService {

	/**
	 * 返回客户名称
	 * @param transId
	 * @return remarkList customerId:客户名称
	 */
	List<Remark> listByCustomerId(String transId);

	boolean save(Remark remark);

	/**
	 * 通过备注id删除
	 * @param id
	 * @return
	 */
	boolean delById(String id);

	/**
	 * 
	 * @param remark
	 * @return  {"success":true , "remark" : {"editTime":"","editBy":"","description":""}}
	 */
	boolean update(Remark remark);

}
