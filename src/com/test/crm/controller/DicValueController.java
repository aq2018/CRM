package com.test.crm.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.crm.domain.DictionaryValue;
import com.test.crm.service.DictionaryTypeService;
import com.test.crm.service.DictionaryValueService;
import com.test.crm.service.impl.DictionaryTypeServiceImpl;
import com.test.crm.service.impl.DictionaryValueServiceImpl;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.UUIDUtil;

public class DicValueController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println(path);
		
		DictionaryValueService dictValueService = (DictionaryValueService) ServiceFactory.getService(new DictionaryValueServiceImpl());
		if("/settings/dictionary/value/getList.do".equals(path)){
			doGetDictionaryValueList(request,response,dictValueService);
		}else if("/settings/dictionary/value/getTypeCode.do".equals(path)){//dictList
			doGetDictionaryTypeCodeList(request,response);
		}else if("/settings/dictionary/value/add.do".equals(path)){
			doAddDictionaryValue(request,response,dictValueService);
		}else if("/settings/dictionary/value/edit.do".equals(path)){
			doEditDictionaryValue(request,response,dictValueService);
		}else if("/settings/dictionary/value/update.do".equals(path)){
			doUpdateDictionaryValue(request,response,dictValueService);
		}else if("/settings/dictionary/value/del.do".equals(path)){
			doDelDictionaryValue(request,response,dictValueService);
		}else if("/settings/dictionary/value/getValue.do".equals(path)){
			doGetById(request,response,dictValueService);
		}else if("/settings/dictionary/value/checkUniqueByTypeAndValue.do".equals(path)){
			doCheckUniqueByTypeAndValue(request,response,dictValueService);
		} 
	
	}
	
	private void doCheckUniqueByTypeAndValue(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictValueService) throws ServletException, IOException {
		String typeid = request.getParameter("typeid");
		String value = request.getParameter("value");
		
		//select count(*) from tbl_dictionaryvalue where value="man" and pid_dictType =3;
		boolean success = dictValueService.checkUniqueByTypeAndValue(typeid,value);
		String msg = "{\"success\":"+success+"}";			
		
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(msg);
	}
	
	private void doGetById(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictValueService) throws ServletException, IOException {
		String id = request.getParameter("id");
		Map<String, Object> valueMap = dictValueService.editDictionaryValue(id);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(valueMap);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(json);
		
	}

	private void doDelDictionaryValue(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictValueService) throws IOException {
		String ids[] = request.getParameterValues("id");
		
		boolean success = dictValueService.delDictionaryValue(ids);
		if(success){
			response.sendRedirect(request.getContextPath()+"/settings/dictionary/value/index.jsp");
		}
	}

	private void doUpdateDictionaryValue(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictValueService) throws IOException {
		//{"sortNo":2,"code":"sex","id":"2","value":"f","content":"女"}
		String id = request.getParameter("id");
		String value = request.getParameter("value");
		String content = request.getParameter("content");
		int sortNo = Integer.valueOf(request.getParameter("sortNo"));
		
		DictionaryValue dictionaryValue = new DictionaryValue();
		dictionaryValue.setId(id);
		dictionaryValue.setContent(content);
		dictionaryValue.setSortNo(sortNo);
		dictionaryValue.setValue(value);
		
		boolean success = dictValueService.updateDictValue(dictionaryValue);
		if(success){
			response.sendRedirect(request.getContextPath()+"/settings/dictionary/value/index.jsp");
		}
	}

	//通过字典值id获取字典类型type字段+value字段
	private void doEditDictionaryValue(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictValueService) throws IOException, ServletException {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}

	//添加字典值
	private void doAddDictionaryValue(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictValueService) throws IOException {
		String pid_dictType = request.getParameter("pid_dictType");//现在直接传的是type
		String value = request.getParameter("dictValue");
		String content = request.getParameter("content");
		String sortNo = request.getParameter("sortNo");
		DictionaryValue dictionaryValue = new DictionaryValue();
		dictionaryValue.setId(UUIDUtil.getUuid());
		dictionaryValue.setValue(value);
		dictionaryValue.setContent(content);
		dictionaryValue.setSortNo(Integer.valueOf(sortNo));
		dictionaryValue.setType(pid_dictType);

		boolean success = dictValueService.addDictionaryValue(dictionaryValue);
		if(success){
			response.sendRedirect(request.getContextPath()+"/settings/dictionary/value/index.jsp");
		}
	}

	//save.jsp线索编码类型 返回类型编码
	private void doGetDictionaryTypeCodeList(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		//[{"id":"?","type":"?"},]
		DictionaryTypeService dts = (DictionaryTypeService) ServiceFactory.getService(new DictionaryTypeServiceImpl());

//		List<DictionaryType> typeCodeList = dts.getDictionaryTypeList();
		//dictList
		Map<String, Object> typeCodeList = dts.getDictionaryTypeList();
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(typeCodeList);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(jsonData);
	}

	/**
	 * 获取字典类型列表list<Map>
	 * 	map1:valueList
	 *  map2:通过map1 id获取字典类型type字段
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	private void doGetDictionaryValueList(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictionaryValueService) throws JsonGenerationException, JsonMappingException, IOException {
		List<DictionaryValue> map = dictionaryValueService.getDictionaryValueList();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(map);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(json);
	}
}
