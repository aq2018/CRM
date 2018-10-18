package com.test.crm.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.test.crm.dao.UserManageDao;
import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.User;
import com.test.crm.exception.LoginException;
import com.test.crm.service.UserManageService;
import com.test.crm.util.DateUtil;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.SqlSessionUtil;

public class UserManageServiceImpl implements UserManageService{
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException, ParseException {
		UserManageServiceImpl um = new UserManageServiceImpl();
		Map<String, Object> map = new HashMap<>();
		map.put("pageNo", 0);
		map.put("pageSize", 10);
		JSONUtil.getJson(um.getUMListByCondition(map));
	}
	private UserManageDao umDao = SqlSessionUtil.getSqlSession().getMapper(UserManageDao.class);

	@Override
	public PaginationVo<User> getUMListByCondition(Map<String, Object> condition) {
		PaginationVo<User> pv = new PaginationVo<>();
		pv.setTotal(umDao.getTotal(condition));
		pv.setDataList2(umDao.getUMListByCondition(condition));
		return pv;
	}

	@Override
	public boolean addUser(User user) {
		return umDao.addUser(user) == 1;
	}

	@Override
	public Map<String,Object> getByAccount(String account) {
		return umDao.getByAccount(account);
	}

	@Override
	public Map<String,Object> getById(String id) {
		return umDao.getById(id);
	}

	@Override
	public boolean updateUser(User user) {
		//比对密码是否被修改,修改密码
		//umDao.resetPwd(user);
		return umDao.updateUser(user) == 1;
	}

	@Override
	public boolean delUser(String[] ids) {
		return umDao.delUser(ids) == ids.length;
	}

	@Override
	public List<Map<String, Object>>  getOwner() {
		return umDao.getOwner();
	}

	@Override
	public User login(String account, String passwd,String remoteIp) throws LoginException{
		User user = umDao.login(account, passwd);
		if (user == null) {
			throw new LoginException("用户名或密码错误");
		}
		
		// 验证ip是否允许登录
		if(user.getPermit_ip() != null && !"".equals(user.getPermit_ip())){
			if (!user.getPermit_ip().contains(remoteIp)) {
				throw new LoginException("未经允许的ip访问");
			}			
		}

		// 验证账号是否过期
		String currentTime = DateUtil.getDate();
		if(user.getInvalid_time() != null && !"".equals(user.getInvalid_time())){
			if (currentTime.compareTo(user.getInvalid_time()) >= 1) { // 当前时间大于有效期，过期
				throw new LoginException("该账户已过期");
			}
		}
		
		// 验证是否锁定
		if ("锁定".equals(user.getLockState())) {
			throw new LoginException("该账户已被锁定");
		}
		
		return user;
	}

	@Override
	public String getIdByName(String owner) {
		return umDao.getIdByName(owner);
	}

	@Override
	public Map<String, Object> getOwnerById(String id) {
		return umDao.getOwnerById(id);
	}

	@Override
	public boolean updateState(User user) {
		return umDao.updateState(user) == 1;
	}

	@Override
	public boolean checkByAccount(String account) {
		return umDao.checkByAccount(account) == 0;
	}

	@Override
	public boolean resetPwd(User user) {
		return umDao.resetPwd(user) == 1;
	}

	@Override
	public boolean checkOldPwd(User user) {
		return umDao.checkOldPwd(user) == 1;
	}

	@Override
	public boolean getByAccountAndEmail(User user){
		return  umDao.getByAccountAndEmail(user) == 1;
	}

}
