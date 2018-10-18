package com.test.crm.web.settings.qx.permission.service.impl;

import java.util.List;

import com.test.crm.util.SqlSessionUtil;
import com.test.crm.web.settings.qx.permission.dao.PermissionDao;
import com.test.crm.web.settings.qx.permission.domain.Permission;
import com.test.crm.web.settings.qx.permission.service.PermissionService;

public class PermissionServiceImpl implements PermissionService {
	private PermissionDao pd = (PermissionDao) SqlSessionUtil.getSqlSession().getMapper(PermissionDao.class);
	
	@Override
	public boolean save(Permission permission) {
		return pd.save(permission) == 1;
	}

	@Override
	public boolean update(Permission permission) {
		return pd.update(permission) == 1;
	}

	@Override
	public boolean del(String id) {
		return pd.del(id) == 1;
	}

	@Override
	public Permission getById(String id) {
		return pd.getById(id);
	}

	@Override
	public List<Permission> listAll() {
		return pd.listAll();
	}

}
