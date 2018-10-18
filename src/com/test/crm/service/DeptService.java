package com.test.crm.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Dept;
import com.test.crm.domain.PaginationVo;

public interface DeptService {

	List<Dept> getDeptList();

	boolean addDept(Dept dept);

	Dept getDeptById(String id);

	void updateDeptByNo(Dept dept);

	boolean delDept(String[] ids);

	int getDeptIdByDno(String deptno);

	boolean checkDeptByNo(String deptno);

	List<Dept> getdeptNameList();

	PaginationVo<Dept> getCondition(int pageNo, int pageSize);

	Map<String, Object> getNoByName(String deptname);

	boolean update(Dept dept);

	List<Map<String, String>> mapNoAndNameByName(String deptname);

}
