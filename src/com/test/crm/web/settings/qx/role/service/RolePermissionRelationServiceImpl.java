package com.test.crm.web.settings.qx.role.service;

import java.util.List;

import com.test.crm.util.SqlSessionUtil;
import com.test.crm.web.settings.qx.permission.dao.PermissionDao;
import com.test.crm.web.settings.qx.permission.domain.Permission;
import com.test.crm.web.settings.qx.role.dao.RolePermissionRelationDao;
import com.test.crm.web.settings.qx.role.domain.RolePermissionRelation;

public class RolePermissionRelationServiceImpl implements RolePermissionRelationService {
	private RolePermissionRelationDao rprd = (RolePermissionRelationDao)SqlSessionUtil.getSqlSession().getMapper(RolePermissionRelationDao.class);
	private PermissionDao pd = (PermissionDao)SqlSessionUtil.getSqlSession().getMapper(PermissionDao.class);
	@Override
	public List<Permission> listByRoleId(String roleId) {
		//通过角色id返回角色许可关系集合
		List<RolePermissionRelation> rprList= rprd.listByRoleId(roleId);
		//通过角色许可关系集合 获取 角色拥有的许可集合
		List<Permission> permissionList = pd.listByIds(rprList);
		return permissionList;
	}
	@Override
	public void save(List<RolePermissionRelation> rprList) {
		rprd.save(rprList);
		
	}
	@Override
	public List<RolePermissionRelation> listRprByRoleId(String roleId) {
		return rprd.listRprByRoleId(roleId);
	}
}
