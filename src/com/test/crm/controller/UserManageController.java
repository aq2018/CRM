package com.test.crm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.Role;
import com.test.crm.domain.User;
import com.test.crm.service.DeptService;
import com.test.crm.service.RoleService;
import com.test.crm.service.UserManageService;
import com.test.crm.service.impl.DeptServiceImpl;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.service.impl.UserManageServiceImpl;
import com.test.crm.util.Const;
import com.test.crm.util.DateUtil;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.MD5Util;
import com.test.crm.util.ParamUtil;
import com.test.crm.util.UUIDUtil;

public class UserManageController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println("path:"+path);
		
		UserManageService umService = (UserManageService)ServiceFactory.getService(new UserManageServiceImpl());
		if("/settings/qx/user/query.do".equals(path)){
			doQueryUser(request,response,umService);
		}else if("/settings/qx/user/add.do".equals(path)){
			doAddyUser(request,response,umService);
		}else if("/settings/qx/user/getByAccount.do".equals(path)){
			doGetUserByAccount(request,response,umService);
		}else if("/settings/qx/user/getById.do".equals(path)){
			doGetUserById(request,response,umService);
		}else if("/settings/qx/user/update.do".equals(path)){
			doUpdateUser(request,response);
		}else if("/settings/qx/user/del.do".equals(path)){
			doDelUser(request,response,umService);
		}else if("/settings/qx/user/userInfo.do".equals(path)){
			doUserInfo(request,response,umService);
		}else if("/settings/qx/user/getOwner.do".equals(path)){
			doGetOwner(request,response,umService);
		} else if("/settings/qx/user/getOwnerById.do".equals(path)){
			doGetOwnerById(request,response,umService);
		} else if("/settings/qx/user/changeState.do".equals(path)){
			doChangeState(request,response);
		} else if("/settings/qx/user/checkAct.do".equals(path)){
			doCheckAct(request,response);
		} else if("/settings/qx/user/resetPwd.do".equals(path)){
			doResetPwd(request,response);
		} else if("/settings/qx/user/checkOldPwd.do".equals(path)){
			doCheckOldPwd(request,response);
		}
	}
	
	protected void doCheckOldPwd(HttpServletRequest request, HttpServletResponse response) {
		User user = ParamUtil.parseRequset(request, User.class);
		user.setPassword(MD5Util.get(user.getPassword()));
		UserManageService ums = (UserManageService) ServiceFactory.getServiceImpl("UserManageService");
		boolean success = ums.checkOldPwd(user);
		JSONUtil.booleanPrint(success, response);//**************************修改密码***********
	}

	protected void doResetPwd(HttpServletRequest request, HttpServletResponse response) {
		User user = ParamUtil.parseRequset(request, User.class);
		user.setPassword(MD5Util.get(user.getPassword()));
		JSONUtil.getJson(user);
		UserManageService ums = (UserManageService) ServiceFactory.getServiceImpl("UserManageService");
		boolean success = ums.resetPwd(user);
		JSONUtil.booleanPrint(success, response);
	}

	protected void doCheckAct(HttpServletRequest request, HttpServletResponse response) {
		String account = request.getParameter("account");
		UserManageService ums = (UserManageService) ServiceFactory.getServiceImpl("UserManageService");
		boolean success = ums.checkByAccount(account);
		JSONUtil.booleanPrint(success, response);
	}

	protected void doChangeState(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String state = request.getParameter("state");
		User user = new User();
		user.setId(id);
		user.setLockState(state);
		user.setEditBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getUsername());
		user.setEditTime(DateUtil.getDate());
		
		UserManageService ums = (UserManageService) ServiceFactory.getServiceImpl("UserManageService");
		Map<String, Object> map = new HashMap<>();
		try {
			boolean success = ums.updateState(user);
			map.put("success", success);
			map.put("user", user);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	protected void doGetOwnerById(HttpServletRequest request, HttpServletResponse response, UserManageService umService) {
		String id = request.getParameter("id");
		Map<String,Object> map = umService.getOwnerById(id);
		JSONUtil.getJsonOut(map, response);
	}
	protected void doGetOwner(HttpServletRequest request, HttpServletResponse response, UserManageService umService) throws ServletException, IOException {
		List<Map<String, Object>> list = umService.getOwner();
		ObjectMapper ob = new ObjectMapper();
		String json = JSONUtil.getJson(list);
		response.getWriter().print(json);
	}
	
	
	protected void doUserInfo(HttpServletRequest request, HttpServletResponse response, UserManageService umService) throws ServletException, IOException {
		String account = request.getParameter("account");
		System.out.println(account);
		Map<String,Object> map = umService.getByAccount(account);
		request.setAttribute("userMap", map);
		request.getRequestDispatcher("/settings/qx/user/detail.jsp").forward(request, response);
	}

	protected void doDelUser(HttpServletRequest request, HttpServletResponse response, UserManageService umService) throws IOException {
		String ids[] = request.getParameterValues("id");
		
		boolean success = umService.delUser(ids);
		JSONUtil.booleanPrint(success, response);
	}

	protected void doUpdateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String account = request.getParameter("account");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String invalid_time = request.getParameter("invalid_time");
		String lockState = request.getParameter("lockState");
		String permit_ip = request.getParameter("permit_ip");
		String deptno = request.getParameter("deptno");
		
		User user = new User();
		user.setId(id);
		user.setAccount(account);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setInvalid_time(invalid_time);
		user.setLockState(lockState);
		user.setDeptno(deptno);
		user.setPermit_ip(permit_ip);
		user.setCreateBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getUsername());//修改者
		user.setCreateTime(DateUtil.getDate());//修改时间
		
		UserManageService umService = (UserManageService) ServiceFactory.getService(new UserManageServiceImpl());
		Map<String, Object> map = new HashMap<>();
		try {
			umService.updateUser(user);
			map.put(Const.SUCCESS, true);
			map.put("data", user);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Const.SUCCESS, false);
		}
		
		JSONUtil.getJsonOut(map, response);
	}

	//{"permit_ip":"127.0.0.1,192.168.100.2","password":"123","invalid_time":1487038210000,"id":"4","lockState":"锁定","account":"zhaoliu","email":"bbb@bjpowernode.com","deptno":"M0001","username":"赵雷","deptname":"市场部"}
	protected void doGetUserById(HttpServletRequest request, HttpServletResponse response, UserManageService umService) throws JsonGenerationException, JsonMappingException, IOException {
		String id = request.getParameter("id");
		Map<String, Object> userMap = umService.getById(id);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(userMap);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(json);
	}
	
	/*
	 * {"permit_ip":"127.0.0.1,192.168.100.2","invalid_time":1484359810000,"id":"1","state":"启用","dname":"市场部","account":"zhangsan","email":"zhangsan@bjpowernode.com","username":"张三"}
	 */
	protected void doGetUserByAccount(HttpServletRequest request, HttpServletResponse response, UserManageService umService) throws JsonGenerationException, JsonMappingException, IOException {
		String account = request.getParameter("account");
		Map<String, Object> userMap = umService.getByAccount(account);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(userMap);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(json);
		
	}

	protected void doAddyUser(HttpServletRequest request, HttpServletResponse response, UserManageService umService) throws IOException {
		String account = request.getParameter("account");
		String username = request.getParameter("username");
		String password = MD5Util.get(request.getParameter("password"));
		String email = request.getParameter("email");
		String invalid_time = request.getParameter("invalid_time");
		String lockState = request.getParameter("lockState");
		String deptnameno = request.getParameter("deptname");
		String permit_ip = request.getParameter("permit_ip");
		String creatBy = (((User)request.getSession().getAttribute(Const.SESSION_USER)).getUsername());
		
		User user = new User();
		user.setId(UUIDUtil.getUuid());
		user.setAccount(account);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setInvalid_time(invalid_time);
		user.setLockState(lockState);
		user.setDeptno(deptnameno);
		user.setPermit_ip(permit_ip);
		user.setCreateBy(creatBy);
		user.setCreateTime(DateUtil.getDate());
		boolean success = umService.addUser(user);
		JSONUtil.booleanPrint(success, response);
		
	}

	protected void doQueryUser(HttpServletRequest request, HttpServletResponse response,
			UserManageService umService) throws JsonGenerationException, JsonMappingException, IOException {
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String username = request.getParameter("username");
		String deptname = request.getParameter("deptname");
		String state = request.getParameter("state");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		
		Map<String, Object> condition = new HashMap<>();
		condition.put("pageNo", (pageNo - 1)* pageSize);
		condition.put("pageSize", pageSize);
		condition.put("username", username);
		condition.put("deptname", deptname);
		condition.put("lockState",state);
		condition.put("start_time", start_time);
		condition.put("end_time", end_time);
		
		PaginationVo<User> pv = umService.getUMListByCondition(condition);
		
		JSONUtil.getJsonOut(pv, response);
	}
}
