package com.test.crm.web.settings.qx.permission.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.Const;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.ParamUtil;
import com.test.crm.util.UrlparttenUtil;
import com.test.crm.web.settings.qx.permission.domain.Permission;
import com.test.crm.web.settings.qx.permission.service.PermissionService;

public class PermissionController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UrlparttenUtil.get(this.getClass(), request, response);
	}

	protected void save(HttpServletRequest request, HttpServletResponse response) {
		Permission permission = ParamUtil.add(request, Permission.class);
		String pid = permission.getPid();
		if(pid == null || "".equals(pid)){
			permission.setPid("0");
		}
		PermissionService ps = ServiceFactory.getServiceImpl("PermissionService");
		Map<String, Object> map = new HashMap<>();
		try {
			ps.save(permission);
			map.put(Const.SUCCESS, true);
			map.put("id", permission.getId());
		} catch (Exception e) {
			map.put(Const.SUCCESS, false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	protected void update(HttpServletRequest request, HttpServletResponse response) {
		Permission permission = ParamUtil.update(request, Permission.class);
		PermissionService ps = ServiceFactory.getServiceImpl("PermissionService");
		Map<String, Object> map = new HashMap<>();
		try {
			ps.update(permission);
			map.put(Const.SUCCESS, true);
			map.put("permission", permission);
		} catch (Exception e) {
			map.put(Const.SUCCESS, false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void del(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		PermissionService ps = ServiceFactory.getServiceImpl("PermissionService");
		boolean success = ps.del(id);
		JSONUtil.booleanPrint(success, response);
	}
	
	protected void getTree(HttpServletRequest request, HttpServletResponse response) {
		PermissionService ps = ServiceFactory.getServiceImpl("PermissionService");
		List<Map<String, Object>> treeList = new ArrayList<>();
		List<Permission> allList = ps.listAll();
		/**
		 * { id:0, name:"CRM" , open : true},
		{ id:1, pId:0, name:"市场活动" , open : true},
		{ id:11, pId:1, name:"创建市场活动"}]
		 * 
		 */
		for (Permission permission : allList) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", permission.getId());
			map.put("pId", permission.getPid());
			map.put("name", permission.getName());
			if("CRM".equals(permission.getName())){
				map.put("open", true);
			}
			treeList.add(map);
		}
		JSONUtil.getJsonOut(treeList, response);
	}
	
	protected void detail(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		PermissionService ps = ServiceFactory.getServiceImpl("PermissionService");
		Permission permission = ps.getById(id);
		
		JSONUtil.getJsonOut(permission, response);
	}
}
