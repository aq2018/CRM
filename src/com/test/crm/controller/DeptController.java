package com.test.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.crm.domain.Dept;
import com.test.crm.domain.PaginationVo;
import com.test.crm.service.DeptService;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.ParamUtil;
import com.test.crm.util.UUIDUtil;
public class DeptController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		ServletContext attribute = request.getServletContext();
		DeptService	deptService = (DeptService) ServiceFactory.getServiceImpl("DeptService");
		
		if("/settings/dept/getList.do".equals(path)){
			deptList(request,response,deptService);
		}else if("/settings/dept/add.do".equals(path)){
			addDept(request,response,deptService);
		}else if("/settings/dept/edit.do".equals(path)){
			edit(request,response,deptService);//根据主键查部门
		}else if("/settings/dept/update.do".equals(path)){
			update(request,response,deptService);
		}else if("/settings/dept/del.do".equals(path)){
			delDept(request,response,deptService);
		}else if("/settings/dept/checkDeptNo.do".equals(path)){
			doCheckDeptByNo(request,response,deptService);
		}else if("/settings/dept/getDeptNameList.do".equals(path)){
			doGetDeptNameList(request,response,deptService);
		}else if("/settings/dept/getCondition.do".equals(path)){
			doGetCondition(request,response,deptService);
		}else if("/settings/dept/getNameByNo.do".equals(path)){
			doGetNoByName(request,response,deptService);
		}else if("/settings/dept/getDeptByName.do".equals(path)){
			doGetDeptByName(request,response,deptService);
		}
		
	}
	
	protected void doGetDeptByName(HttpServletRequest request, HttpServletResponse response, DeptService deptService) {
		String deptname = request.getParameter("name");
		DeptService ds = ServiceFactory.getServiceImpl("DeptService");
		
		List<Map<String, String>> list = ds.mapNoAndNameByName(deptname);
	
		JSONUtil.getJsonOut(list, response);
	}

	protected void edit(HttpServletRequest request, HttpServletResponse response, DeptService deptService) {
		String id = request.getParameter("id");
		Dept dept = deptService.getDeptById(id);
		JSONUtil.getJsonOut(dept, response);
	}

	protected void doGetNoByName(HttpServletRequest request, HttpServletResponse response, DeptService deptService) throws IOException {
		String deptname = request.getParameter("deptname");
		Map<String,Object> map = deptService.getNoByName(deptname);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(map);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(jsonData);
	}

	protected void doGetCondition(HttpServletRequest request, HttpServletResponse response, DeptService deptService) throws IOException {
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));

		//一个代理只能调一个方法
		PaginationVo<Dept> getPageContext = deptService.getCondition((pageNo - 1)* pageSize,pageSize);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(getPageContext);
		
		System.out.println(jsonData);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(jsonData);
	}

	protected void doGetDeptNameList(HttpServletRequest request, HttpServletResponse response, DeptService deptService) throws IOException {
		List<Dept> deptNameList = deptService.getdeptNameList();
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(deptNameList);
		response.getWriter().print(json);
	}

	//检验部门重复
	protected void doCheckDeptByNo(HttpServletRequest request, HttpServletResponse response, DeptService deptService) throws IOException {
		String deptno = request.getParameter("deptno");
		boolean success = deptService.checkDeptByNo(deptno);
		JSONUtil.booleanPrint(success, response);
	}
	protected void update(HttpServletRequest request, HttpServletResponse response, DeptService deptService) {
		/**
		 * 返回一个带id的map集合
		 * 
		 */
		//通过主键id改数据
		Dept dept = ParamUtil.parseRequset(request, Dept.class);
		boolean success = deptService.update(dept);
		JSONUtil.booleanPrint(success, response);
	}
	
	protected void deptList(HttpServletRequest request, HttpServletResponse response, DeptService deptService) throws IOException {
		List<Dept> dList = deptService.getDeptList();
		
		Map<String, Object> map = new HashMap<>();
		map.put("dList", dList);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsobData = objectMapper.writeValueAsString(map);
		System.out.println(jsobData);
		PrintWriter out = response.getWriter();
		out.print(jsobData);
		
	}

	protected void addDept(HttpServletRequest request, HttpServletResponse response, DeptService deptService) throws IOException {
		//{"deptno":"?","dname":"?","master":"?","tel":"?","descript":"?"}
		Dept dept = ParamUtil.parseRequset(request, Dept.class);
		dept.setId(UUIDUtil.get());
		boolean success = deptService.addDept(dept);
		JSONUtil.booleanPrint(success, response);
	}
	//通过编号查部门主键，返回给前台用于修改
	/*
	通过编号查主键，
	使用主键更改数据
	 */
	
	protected void delDept(HttpServletRequest request, HttpServletResponse response, DeptService deptService) {
		String ids[] = request.getParameterValues("id");
		boolean success = deptService.delDept(ids);
		JSONUtil.booleanPrint(success, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
