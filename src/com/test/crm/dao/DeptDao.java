package com.test.crm.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Dept;

public interface DeptDao {

	List<Dept> getDeptList();

	int addDept(Dept dept);

	void updateDeptByNo(Dept dept);

	int delDept(String[] ids);

	Dept getDeptById(String id);

	int getDeptIdByDno(String deptno);

	void updateDeptById(Map<String, String> map);

	Dept getDeptByno(String deptno);

	int checkDeptByNo(String deptno);

	List<Dept> getdeptNameList();

	Long getTotalCount();

	List<Dept> getPageList(int pageNo, int pageSize);

	Map<String, Object> getNoByName(String deptname);

	int update(Dept dept);

	List<Map<String, String>> mapNoAndNameByName(String deptname);

}
