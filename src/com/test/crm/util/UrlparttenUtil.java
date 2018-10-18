package com.test.crm.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;

public class UrlparttenUtil {
	/**
	 * 通过urlpartten 最后一个'/'后缀得到方法名调用该方法
	 * @param clazz
	 * @param request
	 * @param response
	 */
	public static void get(Class<?> clazz, HttpServletRequest request, HttpServletResponse response) {
		String servletPath = request.getServletPath();
		int index = servletPath.lastIndexOf("/") + 1;
		String path = servletPath.substring(index);
		//System.out.println(servletPath+"---->method:"+path);
		try {
			Object obj = clazz.newInstance();
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if(method.getName().equals(path)){
					method.setAccessible(true);
					method.invoke(obj, request,response);
				}
			}
		} catch (IllegalAccessException | SecurityException | IllegalArgumentException
				| InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
	}
}
