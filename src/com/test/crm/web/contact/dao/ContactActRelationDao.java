package com.test.crm.web.contact.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.ContactActRelation;

public interface ContactActRelationDao {
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
	List<Map<String, Object>> getActByContactIdAndName(Map<String, Object> condition);
	/**
	 * 通过联系人id和活动名称 返回未关联市场活动总条数
	 * @param condition
	 * @return
	 */
	Long getTotalByContactIdAndName(Map<String, Object> condition);
	/**
	 * 通过联系人id关联多个市场活动id
	 * @param actIds
	 * @param contactId
	 * @return
	 */
	int addAssociateAct(List<ContactActRelation> list);
	/**
	 * 通过活动id删除联系人关联的活动
	 * @param id
	 * @return
	 */
	int delAssociateAct(String id);
	/**
	 * 通过联系人id删除单张联系人市场活动关联表
	 * @param id
	 */
	void delByContactId(String id);

	/**
	 * 通过联系人id批量删除联系人市场活动关联表
	 * @param ids
	 */
	void delByContactIds(String[] ids);
}
