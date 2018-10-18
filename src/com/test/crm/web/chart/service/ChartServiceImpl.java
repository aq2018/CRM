package com.test.crm.web.chart.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.PaginationVo;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.web.chart.dao.ChartDao;

public class ChartServiceImpl implements ChartService {
	public static void main(String[] args) {
		ChartServiceImpl c = new ChartServiceImpl();
		JSONUtil.getJson(c.getTrans());
	}
	private ChartDao cd = (ChartDao)SqlSessionUtil.getSqlSession().getMapper(ChartDao.class);

	@Override
	public PaginationVo<Map<String, Object>> getTrans() {
		PaginationVo<Map<String, Object>> pv = new PaginationVo<>();
		pv.setTotal(cd.getTransTotal());
		pv.setDataList2(cd.getTrans());
		return pv;
	}

	@Override
	public List<Map<String, Object>> getActivity() {
		return cd.getActivity();
	}
}
