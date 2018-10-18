package com.test.crm.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.User;
import com.test.crm.exception.LoginException;

public interface UserManageService {

	PaginationVo<User> getUMListByCondition(Map<String, Object> condition);

	boolean addUser(User user);

	Map<String,Object> getById(String id);
	
	Map<String,Object> getByAccount(String id);

	boolean updateUser(User user);

	boolean delUser(String[] ids);

	List<Map<String, Object>>  getOwner();

	/**
	 * 
	 * @param account
	 * @param passwd
	 * @param remoteIp 客户端ip
	 * @return User 为null登录失败
	 */
	User login(String account, String passwd,String remoteIp) throws LoginException;

	/**
	 * 
	 * @param owner
	 * @return id
	 */
	String getIdByName(String owner);

	Map<String, Object> getOwnerById(String id);

	/**
	 * 根据id更改锁定状态
	 * @param state
	 * @return
	 */
	boolean updateState(User user);

	/**
	 * 
	 * @param account
	 * @return true 账号可用
	 */
	boolean checkByAccount(String account);

	boolean resetPwd(User user);

	boolean checkOldPwd(User user);

	/**
	 * 忘记密码 通过账号和邮件检测用户是否匹配
	 * @param user
	 * @return true 账号密码匹配
	 */
	boolean getByAccountAndEmail(User user);

}
