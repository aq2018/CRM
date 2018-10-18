package com.test.crm.web.chart.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.crm.domain.PaginationVo;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.UrlparttenUtil;
import com.test.crm.web.chart.service.ChartService;

public class ChatController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UrlparttenUtil.get(ChatController.class, request, response);
	}
	
	protected void trans(HttpServletRequest request, HttpServletResponse response){
		ChartService cs = (ChartService)ServiceFactory.getServiceImpl("ChartService");
		PaginationVo<Map<String, Object>> dataList = cs.getTrans();
		JSONUtil.getJsonOut(dataList, response);
	}
	
	protected void activity(HttpServletRequest request, HttpServletResponse response){
		ChartService cs = (ChartService)ServiceFactory.getServiceImpl("ChartService");
		List<Map<String, Object>> dataList = cs.getActivity();
		JSONUtil.getJsonOut(dataList, response);
	}
}
