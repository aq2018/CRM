package com.test.crm.web.contact.service.impl;

import java.util.List;

import com.test.crm.domain.Remark;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.web.contact.dao.ContactRemarkDao;
import com.test.crm.web.contact.service.ContactRemarkService;

public class ContactRemarkServiceImpl implements ContactRemarkService {
	private ContactRemarkDao crd = (ContactRemarkDao) SqlSessionUtil.getSqlSession().getMapper(ContactRemarkDao.class);

	@Override
	public boolean add(Remark contactRrematk) {
		return crd.add(contactRrematk) == 1;
	}

	@Override
	public List<Remark> getRemark(String contactId) {
		return crd.getRemark(contactId);
	}

	@Override
	public boolean update(Remark contactRrematk) {
		return crd.update(contactRrematk) == 1;
	}

	@Override
	public boolean delById(String id) {
		return crd.delById(id);
	}

	

}
