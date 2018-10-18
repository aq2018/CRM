package com.test.crm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.test.crm.util.Const;

public class LoginFilter implements Filter{
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		/*chain.doFilter(request, response);*/
		if ("/login.do".equals(path) || "/login.jsp".equals(path) || "/welcome.do".equals(path)) {
			chain.doFilter(request, response);
		} else {
			HttpSession session = request.getSession();
			if (session != null && session.getAttribute(Const.SESSION_USER) != null) {
				chain.doFilter(request, response);
			} else {
				response.sendRedirect(request.getContextPath()+"/login.jsp");
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
