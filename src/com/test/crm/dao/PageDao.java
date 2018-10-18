package com.test.crm.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Dept;

public interface PageDao {

	int getTotalCount2();

	List<Dept> getCurrentPageList(Map<String, Object> map);

	Long getTotalCount(Map<String, Object> condition);

	List<Dept> getPageList(Map<String, Object> condition);

	List<Dept> getList(int pageNo, int pageSize);

}
