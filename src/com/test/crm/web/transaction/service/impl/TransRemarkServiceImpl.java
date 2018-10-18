package com.test.crm.web.transaction.service.impl;

import java.util.List;
import com.test.crm.domain.Remark;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.web.transaction.dao.TransRemarkDao;
import com.test.crm.web.transaction.service.TransRemarkService;

public class TransRemarkServiceImpl implements TransRemarkService{
	public static void main(String[] args) {
		TransRemarkServiceImpl t = new TransRemarkServiceImpl();
		
		JSONUtil.getJson(t.listByCustomerId("50eeb8bcba8c4b08bafb53b1e29214c2"));
	}
	private TransRemarkDao trd = SqlSessionUtil.getSqlSession().getMapper(TransRemarkDao.class);

	@Override
	public List<Remark> listByCustomerId(String transId) {
		return trd.listByCustomerId(transId);
	}

	@Override
	public boolean save(Remark remark) {
		return trd.save(remark) == 1;
	}

	@Override
	public boolean delById(String id) {
		return trd.delById(id) == 1;
	}

	@Override
	public boolean update(Remark remark) {
		return trd.update(remark) == 1;
	}
}
