package com.test.crm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.crm.dao.PageDao;
import com.test.crm.domain.Dept;
import com.test.crm.service.PageService;
import com.test.crm.util.SqlSessionUtil;

public class PageServiceImpl implements PageService {
	private PageDao pageDao = SqlSessionUtil.getSqlSession().getMapper(PageDao.class);
	
	@Override
	public int getTotalCount() {
		return pageDao.getTotalCount2();
	}
	
	
	@Override
	public List<Dept> getCurrentPageList(Map<String, Object> map) {
		System.out.println("以map传参"+map);
		return pageDao.getCurrentPageList(map);
	}
	
	@Override
	public Map<String, Object> pageCondition(Map<String, Object> condition) {
		Map<String, Object> map = new HashMap<>();
		map.put("count", pageDao.getTotalCount(condition));
		map.put("pageList", pageDao.getPageList(condition));
		return map;
	}


	@Override
	public Map<String, Object> doGetDept(int pageNo, int pageSize) {
		Map<String, Object> map = new HashMap<>();
		map.put("count", pageDao.getTotalCount2());
		map.put("pList", pageDao.getList(pageNo,pageSize));
		return map;
	}

}
