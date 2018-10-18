package com.test.crm.web.settings.org.service.impl;

import java.util.List;

import com.test.crm.util.SqlSessionUtil;
import com.test.crm.web.settings.org.dao.OrgDao;
import com.test.crm.web.settings.org.domain.Organization;
import com.test.crm.web.settings.org.service.OrgService;

public class OrgServiceImpl implements OrgService {
	private OrgDao od = (OrgDao)SqlSessionUtil.getSqlSession().getMapper(OrgDao.class);
	
	@Override
	public boolean save(Organization org) {
		return od.save(org) == 1;
	}

	@Override
	public boolean update(Organization org) {
		return od.update(org) == 1;
	}

	@Override
	public Organization getById(String id) {
		return od.getById(id);
	}

	@Override
	public void delById(String id) {
		od.delById(id);
		//删除子机构
		od.delByPId(id);
		
	}

	@Override
	public List<Organization> listOrg() {
		return od.getAll();
	}

}
