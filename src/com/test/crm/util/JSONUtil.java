package com.test.crm.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
	public static void booleanPrint(boolean success,HttpServletResponse response) {
		ObjectMapper ob = new ObjectMapper();
		String json = null;
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("success", success);
			json = ob.writeValueAsString(map);
			//System.out.println(json);
			response.getWriter().print(json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static String getJson(Object obj) {
		ObjectMapper ob = new ObjectMapper();
		String json = null;
		try {
			
			json =  ob.writeValueAsString(obj);
			//System.out.println(json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static void getJsonOut(Object obj,HttpServletResponse response) {
		ObjectMapper ob = new ObjectMapper();
		String json = null;
		try {
			
			json =  ob.writeValueAsString(obj);
			//System.out.println(json);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().println(json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
