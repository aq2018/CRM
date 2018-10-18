package com.test.crm.web.settings.qx.role.dao;

import java.util.List;

import com.test.crm.web.settings.qx.role.domain.RolePermissionRelation;

public interface RolePermissionRelationDao {

	/**
	 * 通过角色id返回角色许可关系集合
	 * @param roleId
	 * @return
	 */
	List<RolePermissionRelation> listByRoleId(String roleId);

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
