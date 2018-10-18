package com.test.crm.web.chart.dao;

import java.util.List;
import java.util.Map;

public interface ChartDao {

	/**
	 * 
	 * @return {"total","","dataList2":[{"name":"","value":""},{}]}
	 */
	List<Map<String, Object>> getTrans();

	Long getTransTotal();

	/**
	 * 
	 * @return [{"name":"","value":""},{}]}
	 */
	List<Map<String, Object>> getActivity();

}
