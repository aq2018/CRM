package com.test.crm.service.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.ibatis.session.SqlSession;

import com.test.crm.domain.MarketActive;
import com.test.crm.service.MarketService;
import com.test.crm.util.SqlSessionUtil;

public class TransactionInvocationHandler implements InvocationHandler {
	public static void main(String[] args) {
		MarketService cs = (MarketService) new TransactionInvocationHandler(new MarketServiceImpl()).getProxy();
		MarketActive market = cs.getById("1");
		System.out.println(market.toString());//{"id":"1","name":"发传单","start_date":"2018-07-11","end_date":"2018-07-15"}
	}
	private Object target;
	
	public TransactionInvocationHandler(Object target) {
		this.target = target;
	}

	public Object getProxy(){
		return  Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object obj = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionUtil.getSqlSession();//获取dao层工具，，开启事务
			obj = method.invoke(target, args);
			
			sqlSession.commit();
			
		} catch (Exception e) {
			if(sqlSession != null){
				sqlSession.rollback();
			}
			e.printStackTrace();
			throw e.getCause();//继续向上抛出异常
		
		}finally {
			SqlSessionUtil.closeSqlSession(sqlSession);
		}
		
		return obj;
	}

}
