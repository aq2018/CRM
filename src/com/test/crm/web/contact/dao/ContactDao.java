package com.test.crm.web.contact.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Contact;

public interface ContactDao {
	/**
	 * 添加单个联系人
	 * @param contact
	 * @return
	 */
	int add(Contact contact);

	/**
	 * 通过id返回联系人、客户姓名
	 * @param id
	 * @return
	 */
	Map<String, Object> getById(String id);

	void update(Contact contact);

	/**
	 * 模糊查询符合条件条数
	 * @param condition
	 * @return
	 */
	Long getTotal(Map<String, Object> condition);
	/**
	 * 用于编辑返回
	 * 通过客户名称模糊查询  返回联系人、所有者id 、客户名称
	 * @param condition
	 * @return
	 */
	List<Contact> getContacts(Map<String, Object> condition);
	/**
	 * 用于detail返回请求域
	 * 通过id返回联系人、 所有者id、所有者姓名、客户名称
	 * @param id
	 * @return
	 */
	Map<String, Object> getById2(String id);

	/**
	 * 导入添加多条联系人
	 * @param listContact
	 * @return
	 */
	int add2(List<Contact> listContact);
	/**
	 * 导出全部 返回contactlist
	 * @return
	 */
	List<Contact> getAll();

	/**
	 * 通过id导出选中 返回List<Contact>
	 * @param ids
	 * @return
	 */
	List<Contact> getById3(String[] ids);

	/**
	 * 通过客户名称name 返回 客户id
	 * @param customerName
	 * @return
	 */
	List<Contact> listByCustomerId(String customerId);

	/**
	 * 根据联系人id删除
	 * @param id
	 * @return
	 */
	int delById(String id);

	/**
	 * 更具联系人名称返回联系人名称、邮箱、手机List
	 * @param condition
	 * @return
	 */
	List<Contact> queryByName(Map<String, Object> condition);

	/**
	 * 更具联系人名称返回联系人名称、邮箱、手机总条数
	 * @param condition
	 * @return
	 */
	Long getTotal2(Map<String, Object> condition);

	/**
	 * 根据多个客户id删除关联联系人
	 * @param customerId[]
	 */
	void delByCustomerId(String[] ids);

	/**
	 * 同时删除多条联系人
	 * @param ids[] 联系人id
	 * @return
	 */
	int delByIds(String[] ids);

}
