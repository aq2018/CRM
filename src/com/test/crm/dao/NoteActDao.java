package com.test.crm.dao;

import java.util.List;

import com.test.crm.domain.NoteActivity;

public interface NoteActDao {

	int add(NoteActivity na);

	List<NoteActivity> getNode(String p_actid);

	void  delete(String[] p_actid);

	int delById(String id);

	int updateNoteById(NoteActivity na);

}
