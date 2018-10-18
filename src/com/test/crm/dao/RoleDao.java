package com.test.crm.dao;

import java.util.List;

import com.test.crm.domain.Page;
import com.test.crm.domain.Role;

public interface RoleDao {

	int add(Role role);


	List<Role> getList(Page page);

	Long getTotal();


	int delete(String[] ids);


	int checkByNo(String no);


	Role getDetailById(String id);


	int update(Role role);
}
