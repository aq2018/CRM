package com.test.crm.web.customer.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.CustomerRemark;

public interface CustomerRemarkService {

	/**
	 * 返回Map客户备注、与客户关联的联系人姓名、称呼
	 * @param customerRemark
	 * @return {"success":true,"remark":"Map<?>"}
	 * 			{"success":false}
	 */
	Map<String, Object> add(CustomerRemark customerRemark);

	/**
	 * 通过客户customerId 返回List<Map>客户备注、与客户关联的联系人姓名、称呼
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getListByCustomerId(String customerId);

	Map<String, Object> update(CustomerRemark customerRemark);

	boolean delById(String id);

}
