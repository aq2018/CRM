package com.test.crm.dao;

import java.util.List;
import java.util.Map;
import com.test.crm.domain.ClueActRelation;

public interface ClueActRelationDao {

	/**
	 * 连表查询
	 * @param id
	 * @return 返回 所有者
	 */
	List<Map<String, Object>> getActsByClueId(String id);

	int addAssociateAct(List<ClueActRelation> addList);

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	int delAssociateAct(String id);

	Long getTotal(Map<String, Object> condition);

	List<Map<String, Object>> getActByClueIdAndName(Map<String, Object> condition);

	Long getTotal2(Map<String, Object> condition);

	List<Map<String, Object>> getActsOriginByClueIdAndName(Map<String, Object> condition);

	/**
	 * 通过线索id
	 * @param id
	 * @return 线索市场关联List<ClueActRelation>
	 */
	List<ClueActRelation> listByClueId(String id);

	/**
	 * 转换线索后删除关联市场活动
	 * @param p_clueId
	 */
	void delByClueId(String p_clueId);

	/**
	 * 同时删除多条线索删除关联的市场活动
	 * @param clueIds[]
	 */
	void delByClueIds(String[] ids);

}
