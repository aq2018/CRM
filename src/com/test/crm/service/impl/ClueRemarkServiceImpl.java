package com.test.crm.service.impl;

import java.util.List;

import com.test.crm.dao.ClueRemarkDao;
import com.test.crm.domain.Remark;
import com.test.crm.service.ClueRemarkService;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.SqlSessionUtil;

public class ClueRemarkServiceImpl implements ClueRemarkService {
	public static void main(String[] args) {
		ClueRemarkServiceImpl s = new ClueRemarkServiceImpl();
		JSONUtil.getJson(s.getRemarks("1"));
		String ids[] = {"1"};
		s.delByClueId(ids);
	}
	private ClueRemarkDao crd = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
	
	@Override
	public boolean add(Remark clueRrematk) {
		return crd.add(clueRrematk) == 1;
	}

	@Override
	public List<Remark> getRemarks(String p_clueid) {
		return crd.getRemarks(p_clueid);
	}

	@Override
	public boolean update(Remark clueRrematk) {
		return crd.update(clueRrematk) == 1;
	}

	@Override
	public boolean delById(String id) {
		return crd.delById(id) == 1;
	}

	@Override
	public void delByClueId(String[] ids) {
		crd.delByClueId(ids);
	}

}
