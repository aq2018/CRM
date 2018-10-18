package com.test.crm.web.settings.org.dao;

import java.util.List;

import com.test.crm.web.settings.org.domain.Organization;

public interface OrgDao {
	/**
	 * 新增机构
	 * @param org
	 * @return
	 */
	int save(Organization org);
	/**
	 * 修改机构
	 * @param org
	 * @return
	 */
	int update(Organization org);
	/**
	 * 通过id获取组织机构
	 * @param id
	 * @return
	 */
	Organization getById(String id);
	/**
	 * 获取所有组织机构
	 * @return
	 */
	List<Organization> getAll();
	/**
	 * 通过id删除组织机构
	 * @param id
	 * @return
	 */
	int delById(String id);
	/**
	 * 通过pId删除组织机构子机构
	 * @param id
	 * @return
	 */
	void delByPId(String id);
}
