package com.test.crm.web.contact.service.impl;

import java.util.List;
import java.util.Map;

import com.test.crm.dao.MarketDao;
import com.test.crm.domain.Contact;
import com.test.crm.domain.Customer;
import com.test.crm.domain.PaginationVo;
import com.test.crm.util.DateUtil;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.util.UUIDUtil;
import com.test.crm.web.contact.dao.ContactActRelationDao;
import com.test.crm.web.contact.dao.ContactDao;
import com.test.crm.web.contact.dao.ContactRemarkDao;
import com.test.crm.web.contact.service.ContactService;
import com.test.crm.web.customer.dao.CustomerDao;
import com.test.crm.web.customer.dao.CustomerRemarkDao;
import com.test.crm.web.transaction.dao.TransactionDao;
import com.test.crm.web.transaction.dao.TransactionHistoryDao;

public class ContactServiceImpl implements ContactService {
	public static void main(String[] args) {
		ContactServiceImpl s = new ContactServiceImpl();
		Contact c = new Contact();
		String id[] = {"1","2"};
		s.delByIds(id);
	}
	private ContactDao cd = (ContactDao)SqlSessionUtil.getSqlSession().getMapper(ContactDao.class);
	private CustomerDao customerDao = (CustomerDao)SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
	private ContactRemarkDao contactRemarkDao = (ContactRemarkDao)SqlSessionUtil.getSqlSession().getMapper(ContactRemarkDao.class);
	private ContactActRelationDao contactActRelationDao = (ContactActRelationDao)SqlSessionUtil.getSqlSession().getMapper(ContactActRelationDao.class);
	
	/**
	 * 新建联系人，先通过客户名称判断是否存在该客户
	 * 如果存在，获取该客户的id
	 * 如果不存在，创建客户对象
	 * @param contact
	 * @return
	 */
	@Override
	public boolean add(Contact contact) {
		String customerName = contact.getCustomerId();//接收传递进来的name
		Customer customer = customerDao.getByName(customerName);//通过客户名字匹配客户，有则提取客户id，没有则创建客户
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDUtil.get());
			customer.setOwner(contact.getOwner());
			customer.setName(contact.getCustomerId());
			customer.setPhone(contact.getMphone());
			customer.setCreateBy(contact.getCreateBy());
			customer.setCreateTime(DateUtil.getDate());
			customer.setContactSummary(contact.getContactSummary());
			customer.setNextContactTime(contact.getNextContactTime());
			customer.setDescription(contact.getDescription());
			customer.setAddress(contact.getAddress());
			customerDao.add2(customer);
		}
		contact.setCustomerId(customer.getId());
		return cd.add(contact) == 1;
	}

	@Override
	public Map<String, Object> getById(String id) {
		return cd.getById(id);
	}

	@Override
	public Map<String, Object> getById2(String id) {
		return cd.getById2(id);
	}
	@Override
	public void update(Contact contact, Customer customer) {
		cd.update(contact);
		customerDao.update(customer);
	}

	@Override
	public PaginationVo<Contact> query(Map<String, Object> condition) {
		PaginationVo<Contact> pv = new PaginationVo<>();
		pv.setTotal(cd.getTotal(condition));
		pv.setDataList(cd.getContacts(condition));
		return pv;
	}

	@Override
	public boolean add2(List<Contact> listContact) {
		return cd.add2(listContact) == listContact.size();
	}

	@Override
	public List<Contact> getAll() {
		return cd.getAll();
	}

	@Override
	public List<Contact> getById3(String[] ids) {
		return cd.getById3(ids);
	}

	@Override
	public List<Contact> listByCustomerId(String customerId) {
		return cd.listByCustomerId(customerId);
	}

	@Override
	public void delById(String id) {
		//删除联系人相关备注
		contactRemarkDao.delByContactId(id);
		//通过联系人id删除单个联系人市场活动关联表
		contactActRelationDao.delByContactId(id);
		cd.delById(id);
	}

	@Override
	public PaginationVo<Contact> queryByName(Map<String, Object> condition) {
		PaginationVo<Contact> pv = new PaginationVo<>();
		pv.setTotal(cd.getTotal2(condition));
		pv.setDataList(cd.queryByName(condition));
		return pv;
	}

	@Override
	public void delByIds(String[] ids) {
		cd.delByIds(ids);
		//批量删除多个联系人相关备注
		contactRemarkDao.delByContactIds(ids);
		//通过联系人id批量删除联系人市场活动关联表
		contactActRelationDao.delByContactIds(ids);
	}

}
