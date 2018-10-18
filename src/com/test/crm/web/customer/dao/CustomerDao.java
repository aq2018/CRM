package com.test.crm.web.customer.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Customer;

public interface CustomerDao {

	/**
	 * 导入时添加多个
	 * @param liCustomers
	 * @return
	 */
	int add(List<Customer> liCustomers);
	/**
	 * 添加单个客户
	 * @param Customer
	 * @return
	 */
	int add2(Customer Customer);
	
	Long getTotal(Map<String, Object> condition);

	List<Customer> getCustomer(Map<String, Object> condition);

	List<Customer> getById(String[] id);

	int update(Customer customer);

	List<Customer> getAll();

	boolean del(String[] ids);

	Map<String, Object> getById2(String id);
	/**
	 * 通过客户名称name 查询该客户是否存在
	 * @param customerName
	 * @return 返回 客户id 
	 */
	Customer getByName(String customerName);
	
	/**
	 * 通过客户姓名模糊查询
	 * @param customerName
	 * @return List<String> customerName集合
	 */
	List<String> listByName(String customerName);
}
