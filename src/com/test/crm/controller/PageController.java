package com.test.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.test.crm.domain.Dept;
import com.test.crm.service.PageService;
import com.test.crm.service.impl.PageServiceImpl;
import com.test.crm.service.impl.ServiceFactory;

public class PageController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println("path:"+path);
		
		
		if("/pageCondition.do".equals(path)){
			pageCondition(request,response);
		}
	}

	private void doGetDept(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		
		PageService pageService = (PageService) ServiceFactory.getService(new PageServiceImpl());
		Map<String, Object> map = pageService.doGetDept(pageNo,pageSize);
		ObjectMapper objectMapper = new ObjectMapper();
		
		String json = objectMapper.writeValueAsString(map); 
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(json);
	}

	/**
	 * 优化doGetTotalCount方法
	 * 在底层一起返回一个map
	 */
	private void pageCondition(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String dname = request.getParameter("dname");
		String master = request.getParameter("master");

		Map<String, Object> condition = new HashMap<>();
		
		condition.put("pageNo", pageNo);
		condition.put("pageSize", pageSize);
		condition.put("dname", dname);
		condition.put("master", master);

		//一个代理只能调一个方法
		PageService pageService2 = (PageService) ServiceFactory.getService(new PageServiceImpl());
		Map<String, Object> getPageContext = pageService2.pageCondition(condition);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(getPageContext);
		
		System.out.println(jsonData);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(jsonData);

	}

	private void doGetTotalCount(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		//LIMIT (pageno -1)* pagesize,pagesize;
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String dname = request.getParameter("dname");
		String master = request.getParameter("master");
		
		
		System.out.println((pageNo - 1)* pageSize);
		PageService pageService = (PageService) ServiceFactory.getService(new PageServiceImpl());
		
		int count = pageService.getTotalCount();
		Map<String, Object> map = new HashMap<>();
		
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);

		//一个代理只能调一个方法
		PageService pageService2 = (PageService) ServiceFactory.getService(new PageServiceImpl());
		List<Dept>  pList = pageService2.getCurrentPageList(map);
		
		
		map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("pageList", pList);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(map);
		System.out.println(jsonData);
		PrintWriter out = response.getWriter();
		out.print(jsonData);;
	}
}
