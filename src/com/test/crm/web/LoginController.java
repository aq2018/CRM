package com.test.crm.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.test.crm.util.DateUtil;
import com.test.crm.util.MD5Util;
import com.test.crm.util.ParamUtil;
import com.test.crm.util.UUIDUtil;
import com.test.crm.util.UrlparttenUtil;
import com.test.crm.util.JSONUtil;

public class LoginController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		UrlparttenUtil.get(LoginController.class, request, response);
	}
	

	protected void sendMail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		User user = ParamUtil.parseRequset(request, User.class);
		UserManageService ums = (UserManageService)ServiceFactory.getServiceImpl("UserManageService"); 
		Map<String, Object> map = new HashMap<>();
		boolean success = ums.getByAccountAndEmail(user);
		if(success){
			String emailCode = UUIDUtil.get().substring(0, 5);
			boolean res = false;//发送邮件标记
			String email_validity = "60";
			String _debug = "0";
			if("1".equals(_debug)){//调试模式 直接回显
				res = true;
			}else{
				//验证是否频繁发送验证码请求  单位 秒
				String temp = "60";
				Object emailTimeFlag = request.getSession().getAttribute("emailTimeFlag");////这里一直为空？？？？
				System.out.println("asdasd:"+request.getSession().getAttribute("emailTimeFlag"));
				boolean checkTime = true;
				if(emailTimeFlag != null){
					int second = (int) (new Date().getTime() - (Long)emailTimeFlag) / 1000;
					System.out.println("second"+second);
					System.out.println(temp);
					if(second <= Integer.valueOf(temp)){
						checkTime = false;
					}
				}
				
				if(checkTime){
					//组装邮件
					String mail = user.getEmail();
					String varTime = String.valueOf(Integer.valueOf(email_validity) / 60);//3分
					res = true;
					//res = SendMailUtil.send(emailCode, mail,varTime);
					
				}else{
					map.put("success", false);
					map.put("msg", "发送邮件过于频繁");
					return;
				}
				
			}
			
			if(res){
				request.getSession().setAttribute("emailCode", MD5Util.get(MD5Util.get(emailCode)));
				request.getSession().setAttribute("emailTimeFlag", new Date().getTime());
				request.getSession().setMaxInactiveInterval(60 * 1);
				System.out.println("safasfas++++"+request.getSession().getAttribute("emailTimeFlag"));
				if("1".equals(_debug)){
					map.put("success", true);
					map.put("msg","邮件已发送，请输入验证码" + emailCode + "有效时长:" + Integer.valueOf(email_validity) / 60 +"分--"+DateUtil.getDate());
				}else{
					map.put("success", true);
					map.put("msg","邮件已发送，请查收邮件");
				}
			}
			
		}else{
			map.put("success", false);
			map.put("msg", "账号和邮箱地址不匹配");
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void forgot(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String code = request.getParameter("code");
		String email_validity = "60";
		Object emailCode = request.getSession().getAttribute("emailCode");
		Object emailTimeFlag = request.getSession().getAttribute("emailTimeFlag");
		Map<String, Object> map = new HashMap<>();
		if(emailTimeFlag != null){//andaid@163.com
			boolean checkTime = true;
			long now = new Date().getTime();
			int time = (int)((now - (Long)emailTimeFlag) / 1000);
			if(time >= Integer.valueOf(email_validity)){
				checkTime = false;
			}
			
			if(checkTime){
				if(MD5Util.get(MD5Util.get(code)).equals((String)emailCode)){
					map.put("success", true);
				}else{
					map.put("success", false);
					map.put("msg", "验证码错误");
				}
			}else{
				map.put("success", false);
				map.put("msg", "验证码过期");
			}
			
		}else{
			map.put("success", false);
			map.put("msg", "您未发送邮件");
		}
		JSONUtil.getJsonOut(map, response);
		
	}
	
	protected void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		Cookie[] cookies = request.getCookies();
		for (int i = 0;i< cookies.length; i++) {
			cookies[i].setMaxAge(0);//why cookie没删掉
		}
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		String passwd = MD5Util.get(request.getParameter("passwd"));
		String flag = request.getParameter("flag");
		String remoteIp = request.getRemoteAddr();
		System.out.println(remoteIp);
		
		Map<String, Object> map = new HashMap<>();
		String json = null;
		
		
		UserManageService umService = (UserManageService)ServiceFactory.getService(new UserManageServiceImpl());
		try {
			User user = umService.login(account,passwd,remoteIp);
			
			if("1".equals(flag)){
				Cookie cookie1 = new Cookie("a", user.getAccount());
				Cookie cookie2 = new Cookie("b", user.getPassword());
				//设置时间
				cookie1.setMaxAge(60 * 60 * 24 * 2);
				cookie2.setMaxAge(60 * 60 * 24 * 2);
				
				//设置Cookie的Path
				cookie1.setPath(request.getContextPath());
				cookie2.setPath(request.getContextPath());
				//响应cookie
				response.addCookie(cookie1);
				response.addCookie(cookie2);
			}
			request.getSession().setAttribute(Const.SESSION_USER, user);
			request.getSession().setMaxInactiveInterval(60 * 30);
			map.put("success", true);
			json = JSONUtil.getJson(map);
			
		} catch (LoginException e) {
			
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			json = JSONUtil.getJson(map);
			
		}
		
		//响应json
		response.getWriter().print(json);
	}
}
