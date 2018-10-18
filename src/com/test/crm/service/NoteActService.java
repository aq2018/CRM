package com.test.crm.service;

import java.util.List;

import com.test.crm.domain.NoteActivity;

public interface NoteActService {

	/**	
	 * @param na 
	 * @return
	 */
	boolean add(NoteActivity na);

	List<NoteActivity> getNode(String p_actid);

	void delete(String[] p_actid);

	/**
	 * 根据备注id删除
	 * @param id
	 * @return true 删除成功
	 */
	boolean delById(String id);

	boolean updateNoteById(NoteActivity na);

}
