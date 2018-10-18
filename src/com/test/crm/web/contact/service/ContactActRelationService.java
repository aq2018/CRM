package com.test.crm.web.contact.service;

import java.util.List;
import java.util.Map;

public interface ContactActRelationService {

	/**
	 * 通过联系人id 返回关联活动列表
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getActsByContactId(String id);
	/**
	 * 通过联系人id和活动名称 返回未关联市场活动
	 * @param condition
	 * @return
	 */
	Map<String, Object> getActByContactIdAndName(Map<String, Object> condition);
	/**
	 * 通过联系人id关联多个市场活动id
	 * @param actIds
	 * @param contactId
	 * @return
	 */
	boolean addAssociateAct(String[] actIds, String contactId);
	
	/**
	 * 通过活动id删除联系人关联的活动
	 * @param id
	 * @return
	 */
	boolean delAssociateAct(String id);
}
