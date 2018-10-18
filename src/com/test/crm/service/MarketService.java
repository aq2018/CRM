package com.test.crm.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.MarketActive;
import com.test.crm.domain.PaginationVo;

public interface MarketService {

	/**
	 * 
	 * @param condition
	 * @param pageNo 页码
	 * @param pageSize 显示数量
	 * @param name 活动名称
	 * @param owner 活动所有者
	 * @param startDate 活动开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	PaginationVo<MarketActive> pageCondition(Map<String, Object> condition);

	/**
	 * 添加活动
	 * @param name 名称
	 * @param owner 所有者
	 * @param startDate 
	 * @param endDate
	 * @param cost 成本
	 * @param descript 
	 */

	boolean addMarketActive(List<MarketActive> actives);

	void delMarktActive(String[] ids);

	boolean updateMarktActive(MarketActive active);

	Map<String, Object> getGetMarktActiveOwner();

	String getOwnerById(String ownerid);

	/**
	 * 通过id获取活动信息
	 * @param id
	 * @return
	 */
	MarketActive getById(String id);

	List<MarketActive> getAll();

	List<MarketActive> exportById(String[] ids);

	boolean saveImports(List<MarketActive> excelImports);

	/**
	 * 通过市场活动返回挥动列表
	 * @param actName
	 * @return
	 */
	PaginationVo<MarketActive> listByName(Map<String, Object> condition);

	
}
