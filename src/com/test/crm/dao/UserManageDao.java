package com.test.crm.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.User;

public interface UserManageDao {

	List<Map<String, Object>> getUMList();

	Long getTotal(Map<String, Object> condition);

	List<Map<String, Object>> getUMListByCondition(Map<String, Object> condition);

	int addUser(User user);

	Map<String,Object> getById(String id);

	Map<String,Object> getByAccount(String account);

	int updateUser(User user);

	int delUser(String[] ids);

	List<Map<String, Object>>  getOwner();

	User login(String account, String passwd);

	String getIdByName(String owner);

	Map<String, Object> getOwnerById(String id);

	int updateState(User user);

	/**
	 * 
	 * @param account
	 * @return 1表示存在该账户
	 */
	int checkByAccount(String account);

	int resetPwd(User user);

	int checkOldPwd(User user);

	/**
	 * /**
	 * 忘记密码 通过账号和邮件检测用户是否匹配
	 * @param user
	 * @return true 账号密码匹配
	 */
	int getByAccountAndEmail(User user);

}
