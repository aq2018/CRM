package com.test.crm.web.transaction.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.crm.dao.UserManageDao;
import com.test.crm.domain.Customer;
import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.Transaction;
import com.test.crm.domain.TransactionHistory;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.util.UUIDUtil;
import com.test.crm.web.customer.dao.CustomerDao;
import com.test.crm.web.transaction.dao.TransRemarkDao;
import com.test.crm.web.transaction.dao.TransactionDao;
import com.test.crm.web.transaction.dao.TransactionHistoryDao;
import com.test.crm.web.transaction.service.TransactionService;

public class TransactionServiceImpl implements TransactionService{
	
	private TransactionDao td = (TransactionDao)SqlSessionUtil.getSqlSession().getMapper(TransactionDao.class);
	private UserManageDao umd = (UserManageDao)SqlSessionUtil.getSqlSession().getMapper(UserManageDao.class);
	private CustomerDao cd = (CustomerDao)SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
	private TransactionHistoryDao thd = (TransactionHistoryDao)SqlSessionUtil.getSqlSession().getMapper(TransactionHistoryDao.class);
	private TransRemarkDao trd = (TransRemarkDao)SqlSessionUtil.getSqlSession().getMapper(TransRemarkDao.class);
	@Override
	public PaginationVo<Map<String, Object>> queryByCondition(Map<String, Object> condition) {
		PaginationVo<Map<String, Object>> pv = new PaginationVo<>();
		pv.setTotal(td.countByCondition(condition));
		pv.setDataList2(td.listByCondition(condition));
		return pv;
	}
	
	@Override
	public Transaction getById(String id) {
		return td.getById(id);
	}
	@Override
	public Map<String, Object> getById2(String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("ownerList", umd.getOwner());
		map.put("transaction", td.geyById2(id));
		return map;
	}
	
	@Override
	public void save(Transaction transaction) {
		//保存交易
		String customerName = transaction.getCustomerId();//提取用户提交的客户名
		Customer customer = cd.getByName(customerName);
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDUtil.get());
			customer.setCreateBy(transaction.getCreateBy());
			customer.setCreateTime(transaction.getCreateTime());
			customer.setName(customerName);
			customer.setNextContactTime(transaction.getNextContactTime());
			customer.setContactSummary(transaction.getContactSummary());
			customer.setDescription(transaction.getDescription());
			customer.setOwner(transaction.getOwner());
			cd.add2(customer);
		}
		transaction.setCustomerId(customer.getId());
		td.save(transaction);
		//保存交易历史
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setId(UUIDUtil.get());
		transactionHistory.setStage(transaction.getStage());
		transactionHistory.setMoney(transaction.getMoney());
		transactionHistory.setExpectedDate(transaction.getExpectedDate());
		transactionHistory.setCreateBy(transaction.getCreateBy());
		transactionHistory.setCreateTime(transaction.getCreateTime());
		transactionHistory.setTransId(transaction.getId());
		thd.save(transactionHistory);
	}
	@Override
	public void update(Transaction transaction) {
		//更新交易
		String customerName = transaction.getCustomerId();//提取用户提交的客户名
		Customer customer = cd.getByName(customerName);
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDUtil.get());
			customer.setCreateBy(transaction.getEditBy());
			customer.setCreateTime(transaction.getEditTime());
			customer.setName(customerName);
			customer.setNextContactTime(transaction.getNextContactTime());
			customer.setContactSummary(transaction.getContactSummary());
			customer.setDescription(transaction.getDescription());
			customer.setOwner(transaction.getOwner());
			cd.add2(customer);
		}
		transaction.setCustomerId(customer.getId());
		td.update(transaction);
		//保存交易历史
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setId(UUIDUtil.get());
		transactionHistory.setStage(transaction.getStage());
		transactionHistory.setMoney(transaction.getMoney());
		transactionHistory.setExpectedDate(transaction.getExpectedDate());
		transactionHistory.setCreateBy(transaction.getEditBy());
		transactionHistory.setCreateTime(transaction.getEditTime());
		transactionHistory.setTransId(transaction.getId());
		thd.save(transactionHistory);
	}
	@Override
	public void del(String[] ids) {
		td.del(ids);
		trd.delByTransIds(ids);
		thd.delByTransIds(ids);
	}
	@Override
	public List<Transaction> getByIds(String[] ids) {
		return td.getByIds(ids);
	}
	@Override
	public List<Transaction> getAll() {
		return td.getAll();
	}
	@Override
	public int save2(List<Transaction> transList) {
		return td.save2(transList);
	}
	@Override
	public List<Transaction> listByContactId(String contactId) {
		return td.listByContactId(contactId);
	}
	@Override
	public void delById(String id) {
		td.delById(id);
		trd.delByTransId(id);
		thd.delByTransId(id);
	}
	@Override
	public void delByCustomerId2(String customerId) {
		
		td.delByCustomerId2(customerId);
	}
	@Override
	public boolean updateByStage(Transaction updateParam) {
		TransactionHistory th = new TransactionHistory();
		th.setStage(updateParam.getStage());
		th.setCreateBy(updateParam.getEditBy());
		th.setCreateTime(updateParam.getEditTime());
		th.setMoney(updateParam.getMoney());
		th.setExpectedDate(updateParam.getExpectedDate());
		th.setId(UUIDUtil.get());
		th.setTransId(updateParam.getId());
		thd.save(th);
		return td.updateByStage(updateParam) == 1;
	}

	@Override
	public List<Transaction> getByCustomerId(String customerId) {
		return td.getByCustomerId(customerId);
	}

	
}
