package com.test.crm.web.settings.qx.role.service;

import java.util.List;

import com.test.crm.web.settings.qx.permission.domain.Permission;
import com.test.crm.web.settings.qx.role.domain.RolePermissionRelation;

public interface RolePermissionRelationService {

	/**
	 * 通过角色id返回许可集合
	 * @param id
	 * @return
	 */
	List<Permission> listByRoleId(String id);

	/**
	 * 为角色添加许可
	 * @param rprList
	 */
	void save(List<RolePermissionRelation> rprList);
	/**
	 * 通过角色id返回角色许可关系集合
	 * @param roleId
	 * @return
	 */
	List<RolePermissionRelation> listRprByRoleId(String roleId);
}
