package com.test.crm.service.impl;

import java.util.List;

import com.test.crm.dao.NoteActDao;
import com.test.crm.domain.NoteActivity;
import com.test.crm.service.NoteActService;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.SqlSessionUtil;

public class NoteActServiceImpl implements NoteActService{
	public static void main(String[] args) {
		NoteActServiceImpl g = new NoteActServiceImpl();
		NoteActivity na = new NoteActivity();
		na.setId("321321");
		na.setDescription("asdas");
		na.setEditBy("asd");
		na.setEditFlag("1");
		System.out.println(JSONUtil.getJson(g.updateNoteById(na)));
	}
	
	private NoteActDao naDao = (NoteActDao) SqlSessionUtil.getSqlSession().getMapper(NoteActDao.class);

	@Override
	public boolean add(NoteActivity na) {
		return naDao.add(na) == 1;
	}

	@Override
	public List<NoteActivity> getNode(String p_actid) {
		return naDao.getNode(p_actid);
	}

	@Override
	public void delete(String[] p_actid) {
		naDao.delete(p_actid);
	}

	@Override
	public boolean delById(String id) {
		return naDao.delById(id) == 1;
	}

	@Override
	public boolean updateNoteById(NoteActivity na) {
		return naDao.updateNoteById(na) == 1 ;
	}

}
