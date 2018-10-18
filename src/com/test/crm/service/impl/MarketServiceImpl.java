package com.test.crm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.crm.dao.MarketDao;
import com.test.crm.dao.NoteActDao;
import com.test.crm.domain.MarketActive;
import com.test.crm.domain.PaginationVo;
import com.test.crm.service.MarketService;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.SqlSessionUtil;

public class MarketServiceImpl implements MarketService{
	public static void main(String[] args) {
		MarketServiceImpl sa = new MarketServiceImpl();
		JSONUtil.getJson(sa.getById("10"));
	}
	private MarketDao marketDao = SqlSessionUtil.getSqlSession().getMapper(MarketDao.class);
	private NoteActDao nad = SqlSessionUtil.getSqlSession().getMapper(NoteActDao.class);

	@Override
	
	public PaginationVo<MarketActive> pageCondition(Map<String, Object> condition) {
		PaginationVo<MarketActive> pv = new PaginationVo<>();
		pv.setTotal(marketDao.getMarketActiveCount(condition));
		pv.setDataList(marketDao.getMarketActiveList(condition));
		
		/*Map<String, Object> map = new HashMap<>();
		map.put("total", marketDao.getMarketActiveCount(condition));
		map.put("mList", marketDao.getMarketActiveList(condition));*/
		return pv;
	}
	
	@Override
	public boolean addMarketActive(List<MarketActive> actives) {
		return marketDao.addMarketActive(actives) == actives.size();
	}

	@Override
	public void delMarktActive(String[] ids) {
		 marketDao.delMarktActive(ids);
		 nad.delete(ids);
	}

	@Override
	public boolean updateMarktActive(MarketActive active) {
		return marketDao.updateMarktActive(active) == 1;
	}

	@Override
	public 	Map<String, Object> getGetMarktActiveOwner() {
		Map<String, Object> map = new HashMap<>();
		map.put("ownerMap", marketDao.getGetMarktActiveOwner());
		
		return map;
	}

	@Override
	public String getOwnerById(String ownerid) {
		return marketDao.getOwnerById(ownerid);
	}

	@Override
	public MarketActive getById(String id) {
		return marketDao.getById(id);
	}

	@Override
	public List<MarketActive> getAll() {
		return marketDao.getAll();
	}

	@Override
	public List<MarketActive> exportById(String[] ids) {
		return marketDao.exportById(ids);
	}

	@Override
	public boolean saveImports(List<MarketActive> excelImports) {
		return marketDao.saveImports() == excelImports.size();
	}

	@Override
	public PaginationVo<MarketActive> listByName(Map<String, Object> condition) {
		PaginationVo<MarketActive> pv = new PaginationVo<>();
		pv.setTotal(marketDao.getMarketActiveCount(condition));
		pv.setDataList(marketDao.getMarketActiveList(condition));
		return pv;
	}

}
