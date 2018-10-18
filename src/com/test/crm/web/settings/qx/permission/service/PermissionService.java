package com.test.crm.web.settings.qx.permission.service;

import java.util.List;

import com.test.crm.web.settings.qx.permission.domain.Permission;

public interface PermissionService {
	/**
	 * 新增许可
	 * @param permission
	 * @return
	 */
	boolean save(Permission permission);
	/**
	 * 修改许可
	 * @param permission
	 * @return
	 */
	boolean update(Permission permission);
	/**
	 * 通过id删除许可
	 * @param permission
	 * @return
	 */
	boolean del(String id);
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
}
