package com.test.crm.web.customer.service.impl;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Customer;
import com.test.crm.domain.PaginationVo;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.web.contact.dao.ContactDao;
import com.test.crm.web.customer.dao.CustomerDao;
import com.test.crm.web.customer.dao.CustomerRemarkDao;
import com.test.crm.web.customer.service.CustomerService;
import com.test.crm.web.transaction.dao.TransactionDao;

public class CustomerServiceImpl implements CustomerService{
	public static void main(String[] args) {
		CustomerServiceImpl v = new CustomerServiceImpl();
//		JSONUtil.getJson(cd.listByName("网"));
	}
	private CustomerDao cd = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
	private CustomerRemarkDao crd = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
	private ContactDao contactDao = SqlSessionUtil.getSqlSession().getMapper(ContactDao.class);
	private TransactionDao transactionDao = SqlSessionUtil.getSqlSession().getMapper(TransactionDao.class);

	@Override
	public boolean add(List<Customer> liCustomers) {
		return cd.add(liCustomers) == liCustomers.size();
	}
	
	@Override
	public boolean add2(Customer customer) {
		return cd.add2(customer) == 1;
	}

	@Override
	public PaginationVo<Customer> query(Map<String, Object> condition) {
		PaginationVo<Customer> pv = new PaginationVo<>();
		pv.setTotal(cd.getTotal(condition));
		pv.setDataList(cd.getCustomer(condition));
		return pv;
	}

	@Override
	public List<Customer> getById(String[] id) {
		return cd.getById(id);
	}

	@Override
	public boolean update(Customer customer) {
		return cd.update(customer) == 1;
	}

	@Override
	public List<Customer> getAll() {
		return cd.getAll();
	}

	@Override
	public void del(String[] ids) {
		cd.del(ids);
		crd.delByCustomerIds(ids);
		contactDao.delByCustomerId(ids);
		transactionDao.delByCustomerId(ids);
	}

	@Override
	public Map<String, Object> getById2(String id) {
		return cd.getById2(id);
	}

	@Override
	public List<String> listByName(String customerName) {
		return cd.listByName(customerName);
	}
}
