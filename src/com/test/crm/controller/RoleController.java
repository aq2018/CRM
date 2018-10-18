package com.test.crm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.crm.domain.Page;
import com.test.crm.domain.Role;
import com.test.crm.service.RoleService;
import com.test.crm.service.impl.RoleServiceImpl;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.Const;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.UUIDUtil;
import com.test.crm.web.settings.qx.permission.domain.Permission;
import com.test.crm.web.settings.qx.permission.service.PermissionService;
import com.test.crm.web.settings.qx.role.service.RolePermissionRelationService;

public class RoleController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println("path:"+path);
		
		
		if("/settings/qx/role/add.do".equals(path)){
			doAdd(request,response);
		}else if("/settings/qx/role/list.do".equals(path)){
			doGetList(request,response);
		} else if("/settings/qx/role/del.do".equals(path)){
			doDel(request,response);
		} else if("/settings/qx/role/check.do".equals(path)){
			doCheck(request,response);
		} else if("/settings/qx/role/detail.do".equals(path)){
			doGetDetail(request,response);
		} else if("/settings/qx/role/update.do".equals(path)){
			doUpdate(request,response);
		} else if("/settings/qx/role/permissionTree".equals(path)){
			doPermissionTree(request,response);
		} else if("/settings/qx/role/distributionPermission".equals(path)){
			doDistributionPermission(request,response);
		}
	}

	protected void doDistributionPermission(HttpServletRequest request, HttpServletResponse response) {
		RolePermissionRelationService rprs = ServiceFactory.getServiceImpl("RolePermissionRelationService");
		
		
	}

	protected void doUpdate(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		RoleService rs = (RoleService) ServiceFactory.getService(new RoleServiceImpl());

		Role role = new Role();
		role.setNo(no);
		role.setName(name);
		role.setDescription(description);
		role.setId(id);
		Map<String, Object> map = new HashMap<>();
		try {
			rs.update(role);
			map.put(Const.SUCCESS, true);
			map.put("data", role);
		} catch (Exception e) {
			map.put(Const.SUCCESS, false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);

	}

	protected void doGetDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		RoleService rs = (RoleService) ServiceFactory.getService(new RoleServiceImpl());
		Role role = rs.getDetailById(id);
		request.setAttribute("role", role);
		request.getRequestDispatcher("detail.jsp").forward(request, response);
	}

	protected void doPermissionTree(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		RolePermissionRelationService rps = ServiceFactory.getServiceImpl("RolePermissionRelationService");
		List<Map<String, Object>> rolePermissionList = new ArrayList<>();
		List<Permission> rolePermissionList2 = rps.listByRoleId(id);
		for (Permission permission : rolePermissionList2) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", permission.getId());
			map.put("pId", permission.getPid());
			map.put("name", permission.getName());
			if("CRM".equals(permission.getName())){
				map.put("open", true);
			}
			rolePermissionList.add(map);
		}
		JSONUtil.getJsonOut(rolePermissionList, response);
		
	}
	
	protected void doCheck(HttpServletRequest request, HttpServletResponse response) {
		String no = request.getParameter("no");
		
		RoleService rs = (RoleService) ServiceFactory.getService(new RoleServiceImpl());
		
		boolean success =  rs.checkByNo(no);
		
		JSONUtil.booleanPrint(success, response);
		
	}

	protected void doDel(HttpServletRequest request, HttpServletResponse response) {
		String ids[] = request.getParameterValues(("id"));
		
		RoleService rs = (RoleService) ServiceFactory.getService(new RoleServiceImpl());
		
		boolean success =  rs.delete(ids);
		
		JSONUtil.booleanPrint(success, response);
		
	}

	protected void doGetList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		Page page = new Page();
		page.setPageNo(((pageNo - 1) * pageSize));
		page.setPageSize(pageSize);
		RoleService rs = (RoleService) ServiceFactory.getService(new RoleServiceImpl());
		Map<String, Object> map = rs.getList(page);
		String json = JSONUtil.getJson(map);
		response.getWriter().print(json);
	}

	protected void doAdd(HttpServletRequest request, HttpServletResponse response) {
		
		String id = UUIDUtil.getUuid();
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		RoleService rs = (RoleService) ServiceFactory.getService(new RoleServiceImpl());
		
		Role role = new Role();
		role.setId(id);
		role.setNo(no);
		role.setName(name);
		role.setDescription(description);
		boolean success = rs.add(role);
		
		JSONUtil.booleanPrint(success, response);
	}

}
