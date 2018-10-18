package com.test.crm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.test.crm.util.Const;

public class ServiceFactory {
	public static void main(String[] args) throws DocumentException {
		
	}	
	private static Map<String, String> classMap = Const.CLASSNAMEMAP;
	
	public static Object getService(Object target){
		return new TransactionInvocationHandler(target).getProxy();
	}
	/**
	 * 获取service实现类bean
	 * @param serviceName代理类父接口
	 * @return 代理实现类
	 */
	public static <T> T getServiceImpl(String serviceName) {
		String serviceImpl = classMap.get(serviceName);
		Object target = null;
		try {
			target = Class.forName(serviceImpl).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (T) new TransactionInvocationHandler(target).getProxy();
	}
	/**
	 * 静态读取class配置文件获得代理对象
	 * @param serviceName
	 * @return
	 */
	public static <T> T getServiceImplByXml(String serviceName){
		Map<String, String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		Object target = null;
		try {
			Document read = reader.read(Thread.currentThread().getContextClassLoader().getResource("applicationContext.xml"));
			Element root = read.getRootElement();
			List<Element> e = root.elements();
			for (Element ee : e) {
				map.put(ee.attributeValue("id"), ee.attributeValue("class"));
			}
			String serviceImpl = map.get(serviceName);
			if(serviceImpl != null){
				target = Class.forName(serviceImpl).newInstance();
			}else{
				throw new RuntimeException(serviceName + "Not Found");//***********asdasd*****
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return (T) new TransactionInvocationHandler(target).getProxy();
	}
}
