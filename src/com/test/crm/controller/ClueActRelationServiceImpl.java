package com.test.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.crm.dao.ClueActRelationDao;
import com.test.crm.domain.ClueActRelation;
import com.test.crm.service.ClueActRelationService;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.util.UUIDUtil;

public class ClueActRelationServiceImpl implements ClueActRelationService {
	public static void main(String[] args) {
		ClueActRelationServiceImpl g = new ClueActRelationServiceImpl();
		Map<String, Object> map = new HashMap<>();
		map.put("pageNo", 1);
		map.put("pageSize", 5);
		map.put("name", "");
		map.put("clueId", "1");
		JSONUtil.getJson(g.getActByClueIdAndName(map));
	}
	
	private ClueActRelationDao card = (ClueActRelationDao) SqlSessionUtil.getSqlSession().getMapper(ClueActRelationDao.class);
	@Override
	public List<Map<String, Object>> getActsByClueId(String id) {
		return card.getActsByClueId(id);
	}
	@Override
	public boolean addAssociateAct(String[] actIds, String p_clueId) {
		
		List<ClueActRelation> addList = new ArrayList<>();
		for (String marketId : actIds) {
			ClueActRelation car = new ClueActRelation();
			car.setId(UUIDUtil.get());
			car.setP_clueId(p_clueId);
			car.setP_marketId(marketId);
			addList.add(car);
		}
		return card.addAssociateAct(addList) == addList.size();
	}
	@Override
	public boolean delAssociateAct(String id) {
		return card.delAssociateAct(id) == 1;
	}
	@Override
	public Map<String, Object> getActByClueIdAndName(Map<String, Object> condition) {
		Map<String, Object> map = new HashMap<>();
		map.put("total", card.getTotal(condition));
		map.put("dataList", card.getActByClueIdAndName(condition));
		JSONUtil.getJson(map);
		return map;
	}
	@Override
	public Map<String, Object> getActsOriginByClueIdAndName(Map<String, Object> condition) {
		Map<String, Object> map = new HashMap<>();
		map.put("total", card.getTotal2(condition));
		map.put("dataList", card.getActsOriginByClueIdAndName(condition));
		return map;
	}

}
