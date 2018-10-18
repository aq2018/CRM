package com.test.crm.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Dept;

public interface PageService {

	int getTotalCount();
	
	List<Dept> getCurrentPageList(Map<String, Object> map);

	/**
	 * 模糊查询
	 * @param condition
	 * @return
	 */
	Map<String, Object> pageCondition(Map<String, Object> condition);

	Map<String, Object> doGetDept(int pageNo, int pageSize);

}
