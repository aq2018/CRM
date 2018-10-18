package com.test.crm.service;

import java.util.List;
import java.util.Map;

public interface ClueActRelationService {

	List<Map<String, Object>> getActsByClueId(String id);

	boolean addAssociateAct(String[] actIds, String p_clueId);

	boolean delAssociateAct(String id);

	Map<String, Object> getActByClueIdAndName(Map<String, Object> condition);

	Map<String, Object> getActsOriginByClueIdAndName(Map<String, Object> condition);



}
