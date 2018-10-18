package com.test.crm.web.settings.org.controller;

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
import com.test.crm.util.JSONUtil;
import com.test.crm.util.ParamUtil;
import com.test.crm.util.UrlparttenUtil;
import com.test.crm.web.settings.org.domain.Organization;
import com.test.crm.web.settings.org.service.OrgService;

public class OrgController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UrlparttenUtil.get(this.getClass(), request, response);
	}
	
	protected void save(HttpServletRequest request, HttpServletResponse response) {
		Organization org = ParamUtil.add(request, Organization.class);
		String pid = org.getPId();
		System.out.println(pid);
		if(pid == null || "".equals(pid)){
			org.setPId("0");
		}
		OrgService os = ServiceFactory.getServiceImpl("OrgService");
		Map<String, Object> map = new HashMap<>();
		try {
			os.save(org);
			map.put("success", true);
			map.put("id", org.getId());
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void del(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		OrgService os = ServiceFactory.getServiceImpl("OrgService");
		Map<String, Boolean> map = new HashMap<>();
		try {
			os.delById(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) {
		Organization org = ParamUtil.update(request, Organization.class);
		OrgService os = ServiceFactory.getServiceImpl("OrgService");
		Map<String, Object> map = new HashMap<>();
		try {
			os.update(org);
			map.put("success", true);
			map.put("org", org);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		OrgService os = ServiceFactory.getServiceImpl("OrgService");
		Organization org = os.getById(id);
		JSONUtil.getJsonOut(org, response);
	}
	
	protected void getOrgTree(HttpServletRequest request, HttpServletResponse response) {
		OrgService os = ServiceFactory.getServiceImpl("OrgService");
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<Organization> orgList = os.listOrg();
		for(Organization org : orgList){
			Map<String, Object> map = new HashMap<>();
			if("xx公司".equals(org.getName())){//{ id:0, name:"某公司" , open : true},
				map.put("id", org.getId());
				map.put("name", org.getName());
				map.put("open", true);
			}else{//{ id:5, pId:0, name:"市场部"}
				map.put("id", org.getId());
				map.put("pId", org.getPId());
				map.put("name", org.getName());
			}
			list.add(map);
		}
		JSONUtil.getJsonOut(list, response);
	}
	
	//测试用例
	protected void getOrgTree2(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> company = new HashMap<>();
		company.put("id", 0);
		company.put("name", "某公司");
		company.put("open", true);
		list.add(company);
		//[{ id:5, pId:0, name:"品牌部"},{}]
		Map<String, Object> map1 = new HashMap<>();
		map1.put("id", 1);
		map1.put("pId", 0);
		map1.put("name", "财务部");
		list.add(map1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("id", 2);
		map2.put("pId", 1);
		map2.put("name", "财务1部门");
		list.add(map2);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("id",3);
		map3.put("pId", 0);
		map3.put("name", "传媒部");
		list.add(map3);
		JSONUtil.getJsonOut(list, response);
	}
	
}
