package com.test.crm.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.MarketActive;
import com.test.crm.domain.Page;

public interface MarketDao {

	Long getMarketActiveCount(Map<String, Object> condition);

	List<MarketActive> getMarketActiveList(Map<String, Object> condition);

	int addMarketActive(List<MarketActive> actives);

	/**
	 * 通过市场活动id批量删除
	 * @param ids
	 */
	void delMarktActive(String[] ids);

	int updateMarktActive(MarketActive active);

	List<String> getGetMarktActiveOwner();

	String getOwnerById(String ownerid);

	MarketActive getById(String id);

	/**
	 * 删除单条市场活动
	 * @param id
	 * @return
	 */
	int delete(String id);

	List<MarketActive> getAllActiveList(Page p);

	List<MarketActive> getAll();

	List<MarketActive> exportById(String[] ids);

	int saveImports();

	/**
	 * 通过市场活动返回挥动列表
	 * @param actName
	 * @return
	 */
	List<MarketActive> listByName(Map<String, Object> condition);

	/**
	 * 通过name返回market 所有者姓名
	 * @param condition
	 * @return
	 */
	Long countByName(Map<String, Object> condition);

}
