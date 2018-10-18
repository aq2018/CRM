package com.test.crm.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.crm.domain.User;
import com.test.crm.exception.LoginException;
import com.test.crm.service.UserManageService;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.service.impl.UserManageServiceImpl;
import com.test.crm.util.Const;
import com.test.crm.util.JSONUtil;

public class WelcomeController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		
/*		String logAct = "xxx";
		String logPwd = "123";*/
		String logAct = null;
		String logPwd = null;
		if(cookies != null){
			//遍历获取用户名和密码
			for (Cookie cookie : cookies) {
				if("a".equals(cookie.getName())){
					logAct = cookie.getValue();
				}else if("b".equals(cookie.getName())){
					logPwd = cookie.getValue();
				}
			}
		}
						
		if(logAct != null && logPwd != null){
			System.out.println("欢迎"+cookies.length);
			try {
				System.out.println("cookie:account="+logAct);
				System.out.println("cookie:passwd="+logPwd);
				
				UserManageService umService = (UserManageService)ServiceFactory.getService(new UserManageServiceImpl());
				User user = umService.login(logAct, logPwd, request.getRemoteAddr());
				//重定向到工作页面
				request.getSession().setAttribute(Const.SESSION_USER, user);
				System.out.println(JSONUtil.getJson(Const.SESSION_USER));
				request.getSession().setMaxInactiveInterval(60 * 30);
				response.sendRedirect(request.getContextPath() + "/workbench/index.jsp");
				
			} catch (LoginException e) {
				//重定向到登录页面
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			}
			
		}else{
			//重定向到登录页面
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}
}
