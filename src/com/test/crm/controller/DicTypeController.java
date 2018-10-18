package com.test.crm.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.crm.domain.DictionaryType;
import com.test.crm.service.DictionaryTypeService;
import com.test.crm.service.DictionaryValueService;
import com.test.crm.service.impl.DictionaryTypeServiceImpl;
import com.test.crm.service.impl.DictionaryValueServiceImpl;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.UUIDUtil;

public class DicTypeController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println(path);
		
		DictionaryValueService dictValueService = (DictionaryValueService) ServiceFactory.getService(new DictionaryValueServiceImpl());
		
		if("/settings/dictionary/type/getList.do".equals(path)){
			doGetDictionaryTypeList(request,response);
		}else if("/settings/dictionary/type/addType.do".equals(path)){
			doAddDictionaryType(request,response);
		}else if("/settings/dictionary/type/eidtType.do".equals(path)){
			doEditDictionaryType(request,response);
		}else if("/settings/dictionary/type/deltType.do".equals(path)){
			doDelDictionaryType(request,response);
		}else if("/settings/dictionary/type/updateType.do".equals(path)){
			doUpdateDictionaryType(request,response);
		}else if("/settings/dictionary/type/checkByCode.do".equals(path)){
			doCheckDictionaryTypeByCode(request,response);
		}
	}

	//获取字典类型列表
	private void doGetDictionaryTypeList(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		DictionaryTypeService dictionaryService = (DictionaryTypeService) ServiceFactory.getService(new DictionaryTypeServiceImpl());
		//{"dictList":[{"type":"?","name":"?"},{}]}
		Map<String, Object> dictMap = dictionaryService.getDictionaryTypeList();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(dictMap);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(json);
		
	}
	
	//添加字典列表
	private void doAddDictionaryType(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		DictionaryType dictionaryType = new DictionaryType();
		dictionaryType.setName(name);
		dictionaryType.setType(type);
		dictionaryType.setDescription(description);
		dictionaryType.setId(UUIDUtil.getUuid());
		
		DictionaryTypeService dictionaryService = (DictionaryTypeService) ServiceFactory.getService(new DictionaryTypeServiceImpl());
		boolean success = dictionaryService.addDictionaryType(dictionaryType);
		if(success){
			response.sendRedirect(request.getContextPath()+"/settings/dictionary/type/index.jsp");
		}
	}

	//通过id返回要编辑字典类型
	private void doEditDictionaryType(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException, ServletException {
		String id = request.getParameter("id");
		
		DictionaryTypeService dictionaryService = (DictionaryTypeService) ServiceFactory.getService(new DictionaryTypeServiceImpl());
		DictionaryType dicType = dictionaryService.getDictionaryTypeById(id);
		
		request.setAttribute("dicType", dicType);
		request.getRequestDispatcher("edit.jsp").forward(request, response);;
	}

	//删除
	private void doDelDictionaryType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ids[] = request.getParameterValues("id");
		System.out.println(Arrays.toString(ids) );
		DictionaryTypeService dictionaryService = (DictionaryTypeService) ServiceFactory.getService(new DictionaryTypeServiceImpl());
		
		boolean success = dictionaryService.delDictionaryType(ids);
		if(success){
			response.sendRedirect(request.getContextPath()+"/settings/dictionary/type/index.jsp");
		}
		
	}
	//select count(*) from tbl_dictionaryvalue where value="man" and pid_dictType =3;
	private void doCheckDictionaryTypeByCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String type = request.getParameter("type");
		DictionaryTypeService dictionaryService = (DictionaryTypeService) ServiceFactory.getService(new DictionaryTypeServiceImpl());

		boolean success = dictionaryService.checkDictTypeByCode(type);
		String msg = null;
		if(success){//编号可用
			msg = "{\"success\":true}";
		}else{
			msg = "{\"success\":false}";			
		}
		
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(msg);
		
	}
	
	//编辑后通过id更新字典类型数据
	private void doUpdateDictionaryType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		DictionaryType dictionaryType = new DictionaryType();
		dictionaryType.setName(name);
		dictionaryType.setType(type);
		dictionaryType.setDescription(description);
		dictionaryType.setId(id);
		
		DictionaryTypeService dictionaryService = (DictionaryTypeService) ServiceFactory.getService(new DictionaryTypeServiceImpl());
		
		boolean success = dictionaryService.updateDictionaryType(dictionaryType);
		if(success){
			response.sendRedirect(request.getContextPath()+"/settings/dictionary/type/index.jsp");
		}
	}


}
