package com.test.crm.util;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
public class SqlSessionUtil{
	private SqlSessionUtil(){}
	private static SqlSessionFactory factory;
	private static ThreadLocal<SqlSession> t = new ThreadLocal<SqlSession>();
	
	static{
		try {
			factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSession getSqlSession(){
		SqlSession sqlSession = t.get();
		if(sqlSession == null){
			sqlSession = factory.openSession();
			t.set(sqlSession);
		}
		return sqlSession;
	}
	
	public static void closeSqlSession(SqlSession sqlSession){
		if(sqlSession != null){
			sqlSession.close();
			t.remove();
		}
	}
}
