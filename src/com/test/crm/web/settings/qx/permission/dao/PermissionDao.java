package com.test.crm.web.settings.qx.permission.dao;

import java.util.List;

import com.test.crm.web.settings.qx.permission.domain.Permission;
import com.test.crm.web.settings.qx.role.domain.RolePermissionRelation;

public interface PermissionDao {
	/**
	 * 新增许可
	 * @param permission
	 * @return
	 */
	int save(Permission permission);
	/**
	 * 修改许可
	 * @param permission
	 * @return
	 */
	int update(Permission permission);
	/**
	 * 通过id删除许可
	 * @param permission
	 * @return
	 */
	int del(String id);
	/**
	 * 通过id返回许可对象
	 * @param id
	 * @return
	 */
	Permission getById(String id);
	/**
	 * 返回所有许可列表
	 * @return
	 */
	List<Permission> listAll();
	/**
	 * 通过角色许可关系集合 获取 角色拥有的许可集合
	 * @param permissionIds
	 * @return
	 */
	List<Permission> listByIds(List<RolePermissionRelation> rprList);

}
