package com.test.crm.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.crm.util.UrlparttenUtil;


public class TestController extends HttpServlet {
	public static void main(String[] args) {
		String servletPath = "/aaa/bbb/_getDept";
		int index = servletPath.indexOf("_")+ 1;
		System.out.println(servletPath.substring(index));
	}
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UrlparttenUtil.get(TestController.class,request,response);
	}

	private void getDept(HttpServletRequest request, HttpServletResponse response)  {
		System.out.println("doGetDept");
	}

	/**
	 * 优化doGetTotalCount方法
	 * 在底层一起返回一个map
	 */
	private void pageCondition(HttpServletRequest request, HttpServletResponse response)  {
		System.out.println("pageCondition");
	}

	private void getTotalCount(HttpServletRequest request, HttpServletResponse response)  {
		System.out.println("doGetTotalCount");
	}
}

