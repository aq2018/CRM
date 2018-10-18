package com.test.crm.service.impl;

import java.util.List;
import java.util.Map;

import com.test.crm.dao.DeptDao;
import com.test.crm.domain.Dept;
import com.test.crm.domain.PaginationVo;
import com.test.crm.service.DeptService;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.SqlSessionUtil;

public class DeptServiceImpl implements DeptService {
	public static void main(String[] args) {
		DeptServiceImpl s = new DeptServiceImpl();
		String id[] = {"1","2","3"};
		JSONUtil.getJson(s.delDept(id));
	}
	private DeptDao deptDao = SqlSessionUtil.getSqlSession().getMapper(DeptDao.class);
	
	public List<Dept> getDeptList() {

		return deptDao.getDeptList();
	}

	public boolean addDept(Dept dept) {
		
		return deptDao.addDept(dept) == 1;
	}

	public Dept getDeptById(String id) {
		
		return deptDao.getDeptById(id);
	}

	public void updateDeptByNo(Dept dept) {
		deptDao.updateDeptByNo(dept);
		
	}

	public boolean delDept(String[] ids) {
		return deptDao.delDept(ids) == ids.length;
	}

	public int getDeptIdByDno(String deptno) {
		return deptDao.getDeptIdByDno(deptno);
	}

	@Override
	public boolean checkDeptByNo(String deptno) {
		return deptDao.checkDeptByNo(deptno) == 0;
	}

	@Override
	public List<Dept> getdeptNameList() {
		return deptDao.getdeptNameList();
	}
	

	@Override
	public PaginationVo<Dept> getCondition(int pageNo, int pageSize) {
		/*Map<String, Object> map = new HashMap<>();
		map.put("total", deptDao.getTotalCount());
		map.put("pageList", deptDao.getPageList(pageNo,pageSize));*/
		PaginationVo<Dept> pv = new PaginationVo<>();
		pv.setTotal(deptDao.getTotalCount());
		pv.setDataList(deptDao.getPageList(pageNo,pageSize));
		return pv;
	}

	@Override
	public Map<String, Object> getNoByName(String deptname) {
		return deptDao.getNoByName(deptname);
	}

	@Override
	public boolean update(Dept dept) {
		return deptDao.update(dept) == 1;
	}

	@Override
	public List<Map<String, String>> mapNoAndNameByName(String deptname) {
		return deptDao.mapNoAndNameByName(deptname);
	}

}
