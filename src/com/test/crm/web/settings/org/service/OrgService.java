package com.test.crm.web.settings.org.service;

import java.util.List;

import com.test.crm.web.settings.org.domain.Organization;

public interface OrgService {
	/**
	 * 新增机构
	 * @param org
	 * @return
	 */
	boolean save(Organization org);
	/**
	 * 更新机构
	 * @param org
	 * @return
	 */
	boolean update(Organization org);
	/**
	 * 通过id获取机构
	 * @param id
	 * @return
	 */
	Organization getById(String id);
	/**
	 * 通过id删除机构 及 子机构
	 * @param id
	 * @return
	 */
	void delById(String id);
	/**
	 * 获取所有组织机构
	 * @return
	 */
	List<Organization> listOrg();
	
	
}
