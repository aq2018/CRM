package com.test.crm.listener;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.test.crm.domain.DictionaryValue;
import com.test.crm.service.DictionaryValueService;
import com.test.crm.service.impl.DictionaryValueServiceImpl;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.Const;

public class SysInitListener implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("-------------------------system init start------------");
		//System.out.println("加载service类名缓存");
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("applicationContext.xml");
			SAXReader reader = new SAXReader();
			Document read = reader.read(in);
			Element root = read.getRootElement();
			List<Element> e = root.elements();
			for (Element ee : e) {
				Const.CLASSNAMEMAP.put(ee.attributeValue("id"), ee.attributeValue("class"));
			}
			event.getServletContext().setAttribute(Const.CLASSMAP, Const.CLASSNAMEMAP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println("加载字典值缓存");
		
		//这是动态调用的方法getAll2()
		DictionaryValueService dvs = (DictionaryValueService) ServiceFactory
				.getService(new DictionaryValueServiceImpl());
		Map<String, List<DictionaryValue>> valueMap = dvs.getAll2();
		Set<String> keys = valueMap.keySet();

		for (String key : keys) {
			event.getServletContext().setAttribute(key, valueMap.get(key));
		}

		//System.out.println("加载交易阶段可能性缓存possibilityMap");
		//加载交易阶段可能性配置文件
		ResourceBundle rb = ResourceBundle.getBundle("TransStagePossibillity");
		Enumeration<String> key2 = rb.getKeys();
		Map<String, String> stageMap = new HashMap<>();
		while (key2.hasMoreElements()) {
			String key = key2.nextElement();
			String value = rb.getString(key);
			stageMap.put(key, value);//("valueTypeList",typeList)
		}
		event.getServletContext().setAttribute(Const.PMAP, stageMap);//possibilityMap
		
		System.out.println("-------------------------system init end------------");
	}
}
