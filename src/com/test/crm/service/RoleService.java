package com.test.crm.service;

import java.util.Map;

import com.test.crm.domain.Page;
import com.test.crm.domain.Role;

public interface RoleService {

	boolean add(Role role);

	Map<String, Object> getList(Page page);

	boolean delete(String[] ids);
	
	/**
	 * 
	 * @param no
	 * @return true 编号可用
	 */
	boolean checkByNo(String no);

	Role getDetailById(String id);

	boolean update(Role role);

}
