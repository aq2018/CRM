package com.test.crm.web.chart.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.PaginationVo;

public interface ChartService {

	/**
	 * 获取交易阶段图表
	 * @return [{"name":"","value":""}]
	 */
	PaginationVo<Map<String, Object>> getTrans();

	/**
	 * 获取市场活动所有者
	 * @return
	 */
	List<Map<String, Object>> getActivity();

}
