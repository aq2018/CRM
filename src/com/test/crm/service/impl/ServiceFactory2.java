package com.test.crm.service.impl;

import org.apache.poi.ss.formula.functions.T;


public class ServiceFactory2 {
	public static void main(String[] args) {
		ServiceFactory2 sa = new ServiceFactory2();
//		MarketServiceImpl m  = ServiceFactory.getService((MarketServiceImpl.class));
	}
	public static Object getService(Object target){
		return new TransactionInvocationHandler(target).getProxy();
	}
	
	//获取实现类
	public T  getService(Class<T> c){
		T t = null;
		try {
			t = (T) c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
		
		return (T) new TransactionInvocationHandler(t).getProxy();
	}
}
