package com.test.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.crm.dao.ClueActRelationDao;
import com.test.crm.dao.ClueDao;
import com.test.crm.dao.ClueRemarkDao;
import com.test.crm.domain.Clue;
import com.test.crm.domain.ClueActRelation;
import com.test.crm.domain.Contact;
import com.test.crm.domain.ContactActRelation;
import com.test.crm.domain.Customer;
import com.test.crm.domain.Remark;
import com.test.crm.domain.Transaction;
import com.test.crm.domain.TransactionHistory;
import com.test.crm.service.ClueService;
import com.test.crm.util.DateUtil;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.util.UUIDUtil;
import com.test.crm.web.contact.dao.ContactActRelationDao;
import com.test.crm.web.contact.dao.ContactDao;
import com.test.crm.web.contact.dao.ContactRemarkDao;
import com.test.crm.web.customer.dao.CustomerDao;
import com.test.crm.web.customer.dao.CustomerRemarkDao;
import com.test.crm.web.transaction.dao.TransactionDao;
import com.test.crm.web.transaction.dao.TransactionHistoryDao;

public class ClueServiceImpl implements ClueService{
	public static void main(String[] args) {
		ClueServiceImpl c = new ClueServiceImpl();
		Transaction t=new Transaction();
		t.setId("123123");
		t.setMoney("32");
		String ids[] = {"1","2"};
//		c.convert("123456", "张三",t);
		c.del(ids);
	}
	
	private ClueDao clueDao = (ClueDao) SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
	private CustomerDao customerDao = (CustomerDao) SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
	private ContactDao contactDao = (ContactDao) SqlSessionUtil.getSqlSession().getMapper(ContactDao.class);
	private ClueRemarkDao clueRemarkDao = (ClueRemarkDao) SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
	private CustomerRemarkDao customerRemarkDao = (CustomerRemarkDao) SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
	private ContactRemarkDao contactRemarkdao = (ContactRemarkDao) SqlSessionUtil.getSqlSession().getMapper(ContactRemarkDao.class);
	private ClueActRelationDao clueActRelationDao = (ClueActRelationDao) SqlSessionUtil.getSqlSession().getMapper(ClueActRelationDao.class);
	private ContactActRelationDao contactActRelationDao = (ContactActRelationDao) SqlSessionUtil.getSqlSession().getMapper(ContactActRelationDao.class);
	private TransactionDao transactionDao = (TransactionDao) SqlSessionUtil.getSqlSession().getMapper(TransactionDao.class);
	private TransactionHistoryDao transactionHistoryDao = (TransactionHistoryDao) SqlSessionUtil.getSqlSession().getMapper(TransactionHistoryDao.class);
	
	@Override
	public Map<String, Object> doGetByCondition(Map<String, Object> condition) {
		Map<String, Object> map = new HashMap<>();
		map.put("total", clueDao.getTotal(condition));
		map.put("pList", clueDao.getList(condition));
		return map;
	}

	@Override
	public List<Clue> getById(String[] ids) {
		return clueDao.getById(ids);
	}

	@Override
	public boolean update(Clue clue) {
		return clueDao.update(clue) == 1;
	}

	@Override
	public void del(String[] ids) {
		clueDao.del(ids);
		clueRemarkDao.delByClueId(ids);
		clueActRelationDao.delByClueIds(ids);
	}

	@Override
	public List<Clue> getAll() {
		return clueDao.getAll();
	}

	@Override
	public boolean add(List<Clue> listClue) {
		return clueDao.add(listClue) == listClue.size();
	}

	@Override
	public void convert(String id, String operator,Transaction transaction) {
		//通过id获取线索
		Clue clue = clueDao.getByid2(id);
		
		//通过线索提取客户 客户不存在新建客户 
		Customer customer = customerDao.getByName(clue.getCompany());
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDUtil.get());
			customer.setOwner(clue.getPid_user());
			customer.setName(clue.getCompany());
			customer.setPhone(clue.getPhone());
			customer.setCreateBy(operator);
			customer.setCreateTime(DateUtil.getDate());
			customer.setContactSummary(clue.getContactSummary());
			customer.setNextContactTime(clue.getNextContactTime());
			customer.setDescription(clue.getDescription());
			customer.setAddress(clue.getAddress());
			customer.setWebsite(clue.getWebsite());
			customerDao.add2(customer);
		}
		//通过线索提取联系人
		Contact contact = new Contact();
		contact.setId(UUIDUtil.get());
		contact.setOwner(clue.getPid_user());
		contact.setSource(clue.getSource());
		contact.setCustomerId(customer.getId());
		contact.setFullname(clue.getSurname());
		contact.setAppellation(clue.getCalls());
		contact.setEmail(clue.getEmail());
		contact.setMphone(clue.getPhone());
		contact.setJob(clue.getJob());
		contact.setCreateBy(operator);
		contact.setCreateTime(DateUtil.getDate());
		contact.setDescription(clue.getDescription());
		contact.setContactSummary(clue.getContactSummary());
		contact.setNextContactTime(clue.getNextContactTime());
		contact.setAddress(clue.getAddress());
		contactDao.add(contact);
		
		//创建交易
		if(transaction != null){
			transaction.setId(UUIDUtil.get());
			transaction.setOwner(clue.getPid_user());
			transaction.setCustomerId(customer.getId());
			transaction.setContactId(contact.getId());
			transaction.setCreateBy(operator);
			transaction.setCreateTime(DateUtil.getDate());
			transaction.setDescription(clue.getDescription());
			transaction.setContactSummary(clue.getContactSummary());
			transaction.setNextContactTime(clue.getNextContactTime());
			transactionDao.save(transaction);
			//创建交易历史
			TransactionHistory transHistory = new TransactionHistory();
			transHistory.setId(UUIDUtil.get());
			transHistory.setCreateBy(operator);
			transHistory.setCreateTime(transaction.getCreateTime());
			transHistory.setExpectedDate(transaction.getExpectedDate());
			transHistory.setMoney(transaction.getMoney());
			transHistory.setStage(transaction.getStage());
			transHistory.setTransId(transaction.getId());
			transactionHistoryDao.save(transHistory);
		}
		
		//线索备注转到客户备注及联系人备注 -->试试直接数据库update更新
		List<Remark> remarkList = clueRemarkDao.getRemarks(id);
		if(remarkList.size() > 0){
			for (Remark remark : remarkList) {
				remark.setId(UUIDUtil.get());
				remark.setContactId(contact.getId());
			}
			contactRemarkdao.save(remarkList);
			for (Remark remark : remarkList) {
				remark.setId(UUIDUtil.get());
				remark.setCustomerId(customer.getId());
			}
			customerRemarkDao.save(remarkList);
			//删除线索关联的备注
			clueRemarkDao.delByClueId2(id);
		
		}
		
		//线索市场活动转到联系人市场活动
		List<ClueActRelation> carList = clueActRelationDao.listByClueId(id);
		List<ContactActRelation> contactActRelationList = new ArrayList<>();
		if(carList.size() > 0){
			for (ClueActRelation car : carList) {
				ContactActRelation contactActRelation = new ContactActRelation();
				contactActRelation.setId(UUIDUtil.get());
				contactActRelation.setContactId(contact.getId());
				contactActRelation.setActivityId(car.getP_marketId());
				contactActRelationList.add(contactActRelation);
			}
			contactActRelationDao.addAssociateAct(contactActRelationList);
			//删除线索关联市场活动
			clueActRelationDao.delByClueId(id);
		}
		
		//删除线索
		clueDao.delById(id);
		
	}

}
