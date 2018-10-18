package com.test.crm.web.contact.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.crm.domain.ContactActRelation;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.util.UUIDUtil;
import com.test.crm.web.contact.dao.ContactActRelationDao;
import com.test.crm.web.contact.service.ContactActRelationService;

public class ContactActRelationServiceImpl implements ContactActRelationService {
	private ContactActRelationDao card = (ContactActRelationDao) SqlSessionUtil.getSqlSession().getMapper(ContactActRelationDao.class);

	@Override
	public List<Map<String, Object>> getActsByContactId(String id) {
		return card.getActsByContactId(id);
	}

	@Override
	public Map<String, Object> getActByContactIdAndName(Map<String, Object> condition) {
		Map<String,Object> map = new HashMap<>();
		map.put("total", card.getTotalByContactIdAndName(condition));
		map.put("dataList", card.getActByContactIdAndName(condition));
		return map;
	}

	@Override
	public boolean addAssociateAct(String[] actIds, String contactId) {
		List<ContactActRelation> list = new ArrayList<>();
		for (String actId : actIds) {
			ContactActRelation car = new ContactActRelation();
			car.setId(UUIDUtil.get());
			car.setContactId(contactId);
			car.setActivityId(actId);
			list.add(car);
		}
		return card.addAssociateAct(list) == actIds.length;
	}

	@Override
	public boolean delAssociateAct(String id) {
		return card.delAssociateAct(id) == 1;
	}
}
