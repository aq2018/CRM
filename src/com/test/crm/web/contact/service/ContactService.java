package com.test.crm.web.contact.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Contact;
import com.test.crm.domain.Customer;
import com.test.crm.domain.PaginationVo;

public interface ContactService {

	/**
	 * 新建联系人，先通过客户名称判断是否存在该客户
	 * 如果存在，获取该客户的id
	 * 如果不存在，创建客户对象
	 * @param contact
	 * @return
	 */
	boolean add(Contact contact);

	/**
	 * 通过联系人id 返回联系人、所有者id、客户名称Map
	 * @param id
	 * @return
	 */
	Map<String, Object> getById(String id);

	void update(Contact contact, Customer customer);

	/**
	 * 根据fullname owner source customerName birth模糊查询 
	 * 返回owner姓名、birth、id、source、fullname、customerName
	 * @param condition
	 * @return
	 */
	PaginationVo<Contact> query(Map<String, Object> condition);

	/**
	 * 通过联系人id 返回联系人、所有者id、所有者姓名、客户姓名Map
	 * @param id
	 * @return
	 */
	Map<String, Object> getById2(String id);
	/**
	 * 导入添加多条
	 * @param listContact
	 * @return
	 */
	boolean add2(List<Contact> listContact);

	/**
	 * 导出全部
	 * @return
	 */
	List<Contact> getAll();
	/**
	 * 通过id导出选中
	 * @param ids
	 * @return
	 */
	List<Contact> getById3(String[] ids);

	/**
	 * 通过客户customerId返回联系人列表
	 * @param customerId
	 * @return
	 */
	List<Contact> listByCustomerId(String customerId);

	/**
	 * 根据联系人id删除一条联系人 以及联系人相关的备注,交易,市场活动
	 * @param id
	 * @return
	 */
	void delById(String id);

	/**
	 *  根据联系人姓名fullname 返回id、名称、email、phone
	 * @param condition
	 * @return
	 */
	PaginationVo<Contact> queryByName(Map<String, Object> condition);

	/**
	 * 同时删除多个联系人 以及联系人相关的备注,交易,市场活动
	 * @param ids[] 联系人id
	 * @return
	 */
	void delByIds(String[] ids);

}
