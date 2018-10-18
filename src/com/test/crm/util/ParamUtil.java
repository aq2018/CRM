package com.test.crm.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.test.crm.domain.User;


public class ParamUtil {
	/**
	 * 处理普通参数
	 * @param request
	 * @param class1 需要调用set方法的对象.class
	 * @return	T t
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws Exception
	 */
	//普通参数***需要throw抛异常而不是处理异常，另外处理事务
	public static <T>T parseRequset(HttpServletRequest request,Class<T> clazz){
		T obj = null;
		try {
			obj =  (T)clazz.newInstance();
			
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	
		/**
		 * 就是前端提交到Servlet或者Action里面的参数Map哈，如果你是以表单提交，
		 * 那么request.getParameterMap()中将包含你表单里面所有input标签的数据，
		 * 以其name为key，以其value为值，如果你是以ajax提交的话，就是你自己组织的所有参数了
		 */
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			if(values != null && values.length > 1){
                continue;
            }
			String value = entry.getValue()[0];
			/*System.out.println(key);
			System.out.println(value);*/
			try {
				if(key != null && value != null){
					String methodName = "set"+key.substring(0, 1).toUpperCase()+key.substring(1);
					Method method = clazz.getMethod(methodName, String.class);
					method.invoke(obj, value);		
					
					//做完加上username
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	/**
	 * 分页查询使用   
	 * create 2018/7/24
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getConditionMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Map<String, String[]> parameterMap = request.getParameterMap();
		int pageNo = Integer.valueOf(parameterMap.get("pageNo")[0]);
		int pageSize = Integer.valueOf(parameterMap.get("pageSize")[0]);
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			if("pageNo".equals(entry.getKey())){				
				map.put("pageNo", ((pageNo - 1) * pageSize));
			}else if("pageSize".equals(entry.getKey())){				
				map.put("pageSize", pageSize);
			}else{
				map.put(entry.getKey(), entry.getValue()[0]);
			}
		}
//		JSONUtil.getJson(map);
		return map;
	}
	
	/**
	 * 添加创建者creater、创建时间createTime、创建标记flag=0
	 * @param request
	 * @param class1 需要调用set方法的对象.class
	 * @return	T t
	 */
	public static <T>T add(HttpServletRequest request, Class<T> clazz) {
		T obj = null;
		try {
			obj = (T) clazz.newInstance();
			
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			if(values != null && values.length > 1){
                continue;
            }
			String value = entry.getValue()[0];
			
			try {
				if(key != null && value != null){
					String methodName = "set"+key.substring(0, 1).toUpperCase()+key.substring(1);
					Method[] methods = clazz.getMethods();
					for (Method method : methods) {
						if("setId".equals(method.getName())){
							method.invoke(obj, UUIDUtil.get());							
						} else if("setCreateBy".equals(method.getName())){
							if(user != null){								
								method.invoke(obj, user.getUsername());				
							}else{
								method.invoke(obj, "没有登录，测试用例");
							}
						} else if("setCreateTime".equals(method.getName())){
							method.invoke(obj, DateUtil.getDate());							
						} else if("setEditFlag".equals(method.getName()) || "setFlag".equals(method.getName())){
							method.invoke(obj, "0");							
						} else if(methodName.equals(method.getName())){
							method.invoke(obj,value);
						}
					}
					
				}
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	/**
	 * 更改编辑者editer、编辑时间editTime、编辑标记flag=1
	 * @param request
	 * @param class1 需要调用set方法的对象.class
	 * @return	T t
	 */
	public static <T>T update(HttpServletRequest request, Class<T> clazz) {
		T obj = null;
		try {
			obj = (T) clazz.newInstance();
			
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			if(values != null && values.length > 1){
                continue;
            }
			String value = "".equals(entry.getValue()[0]) ? null : entry.getValue()[0];
//			String value = entry.getValue()[0];?
			try {
				if(key != null && value != null){
					String methodName = "set"+key.substring(0, 1).toUpperCase()+key.substring(1);
					Method[] methods = clazz.getMethods();
					for (Method method : methods) {
						if("setEditBy".equals(method.getName())){
							if(user != null){								
								method.invoke(obj, user.getUsername());				
							}else{
								method.invoke(obj, "没有登录，测试用例");
							}
						} else if("setEditTime".equals(method.getName())){
							method.invoke(obj, DateUtil.getDate());							
						} else if("setFlag".equals(method.getName()) || "setEditFlag".equals(method.getName())){
							method.invoke(obj, "1");							
						} else if(methodName.equals(method.getName())){
							method.invoke(obj, value);
						}
					}
					
				}
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
