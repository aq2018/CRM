package com.test.crm.web.customer.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.CustomerRemark;
import com.test.crm.domain.Remark;

public interface CustomerRemarkDao {

	
	/**
	 * 添加一条备注
	 * @param customerRemark
	 * @return
	 */
	int add(CustomerRemark customerRemark);

	/**
	 * 通过客户customerId 返回List<Map>客户备注、与客户关联的联系人姓名、称呼
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getListByCustomerId(String customerId);

	int update(CustomerRemark customerRemark);

	int delById(String id);
	
	/**
	 * 添加备注后返回Map客户备注内容创建时间、创建者、与客户关联的联系人姓名、称呼
	 * @param customerRemark
	 * @return
	 */
	Map<String, Object> getById(String id);

	/**
	 * 线索备注转到客户备注
	 * @param listremark
	 */
	void save(List<Remark> listremark);

	/**
	 * 同时删除多个客户，删除客户关联的备注
	 * @param customerIds[]
	 */
	void delByCustomerIds(String[] customerIds);

}
