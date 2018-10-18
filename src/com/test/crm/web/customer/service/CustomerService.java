package com.test.crm.web.customer.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Customer;
import com.test.crm.domain.PaginationVo;

public interface CustomerService {
	/**
	 * 导入批量添加
	 * @param liCustomers
	 * @return
	 */
	boolean add(List<Customer> liCustomers);
	/**
	 * 添加单个
	 * @param customer
	 * @return
	 */
	boolean add2(Customer customer);
	
	PaginationVo<Customer> query(Map<String, Object> condition);

	/**
	 * 通过id导出选中
	 * @param ids
	 * @return
	 */
	List<Customer> getById(String[] ids);

	boolean update(Customer customer);

	List<Customer> getAll();

	void del(String[] ids);

	/**
	 * 通过id查单个客户
	 * @param id
	 * @return
	 */
	Map<String, Object> getById2(String id);
	
	/**
	 * 通过客户姓名模糊查询
	 * @param customerName
	 * @return List<String> customerName集合
	 */
	List<String> listByName(String customerName);


}
