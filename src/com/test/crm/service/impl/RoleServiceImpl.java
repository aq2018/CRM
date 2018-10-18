package com.test.crm.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.test.crm.dao.RoleDao;
import com.test.crm.domain.Page;
import com.test.crm.domain.Role;
import com.test.crm.service.RoleService;
import com.test.crm.util.SqlSessionUtil;

public class RoleServiceImpl implements RoleService{
	public static void main(String[] args) {
		RoleServiceImpl q = new RoleServiceImpl();
		Role role = new Role();
		role.setNo("as");
		role.setName("asd");
		role.setDescription("asd");
		role.setId("1");
		System.out.println(q.update(role));
	}
	private RoleDao roleDao = SqlSessionUtil.getSqlSession().getMapper(RoleDao.class);

	@Override
	public boolean add(Role role) {
		return roleDao.add(role) == 1;
	}

	@Override
	public Map<String, Object> getList(Page page) {
		Map<String, Object> map = new HashMap<>();
		map.put("total", roleDao.getTotal());
		map.put("roleList", roleDao.getList(page));
		return map;
	}

	@Override
	public boolean delete(String[] ids) {
		return roleDao.delete(ids) == ids.length;
	}

	@Override
	public boolean checkByNo(String no) {
		return roleDao.checkByNo(no) == 0;
	}

	@Override
	public Role getDetailById(String id) {
		return roleDao.getDetailById(id);
	}

	@Override
	public boolean update(Role role) {
		return roleDao.update(role) == 1;
	}
}
