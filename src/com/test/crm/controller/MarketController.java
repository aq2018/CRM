package com.test.crm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.crm.domain.MarketActive;
import com.test.crm.domain.NoteActivity;
import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.User;
import com.test.crm.service.MarketService;
import com.test.crm.service.NoteActService;
import com.test.crm.service.UserManageService;
import com.test.crm.service.impl.MarketServiceImpl;
import com.test.crm.service.impl.NoteActServiceImpl;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.Const;
import com.test.crm.util.DateUtil;
import com.test.crm.util.ExcelImport;
import com.test.crm.util.ExcelReader;
import com.test.crm.util.ExcelWriter;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.ParamUtil;
import com.test.crm.util.UUIDUtil;

public class MarketController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println(path);
		if ("/workbench/activity/query.do".equals(path)) {
			doQueryActive(request, response);
		} else if ("/workbench/activity/add.do".equals(path)) {
			doAddMarktActive(request, response);
		} else if ("/workbench/activity/edit.do".equals(path)) {
			doEdit(request, response);
		} else if ("/workbench/activity/detail.do".equals(path)) {
			doGetById(request, response);
		} else if ("/workbench/activity/del.do".equals(path)) {
			doDelMarktActive(request, response);
		} else if ("/workbench/activity/updateAct.do".equals(path)) {
			doUpdateMarktActive(request, response);
		} else if ("/workbench/activity/getOwner.do".equals(path)) {
			doGetMarktActiveOwner(request, response);
		} else if("/workbench/activity/getNotes.do".equals(path)){
			doGetNote(request,response);
		} else if("/workbench/activity/addNote.do".equals(path)){
			doAddNote(request,response);
		} else if("/workbench/activity/delNote.do".equals(path)){
			doDelByActId(request,response);
		} else if("/workbench/activity/delNoteById.do".equals(path)){
			doDelNoteById(request,response);
		} else if("/workbench/activity/updateNote.do".equals(path)){
			UpdateNoteById(request,response);
		} else if("/workbench/activity/exportAll.do".equals(path)){
			doExportAll(request,response);
		} else if("/workbench/activity/exportChk.do".equals(path)){
			doExportChk(request,response);
		} else if("/workbench/activity/import.do".equals(path)){
			doUpload(request,response);
		}

	}

	protected void doEdit(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		MarketService ms = (MarketService)ServiceFactory.getServiceImpl("MarketService");
		MarketActive marketActive = ms.getById(id);
		JSONUtil.getJsonOut(marketActive, response);
	}

	//导入excel格式为xsl
	protected void doUpload(HttpServletRequest request, HttpServletResponse response) {
		String filePath = ExcelImport.imports(request);
		ExcelReader<MarketActive> reader = new ExcelReader<>();
		List<MarketActive> excelImports = reader.read(MarketActive.class, filePath);

		MarketService ms = (MarketService)ServiceFactory.getServiceImpl("MarketService");
		boolean success = ms.addMarketActive(excelImports);
		JSONUtil.booleanPrint(success, response);
		
	}

	protected void doExportChk(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ids[] = request.getParameterValues("id");
		MarketService ms = (MarketService) ServiceFactory.getServiceImpl("MarketService");
		ExcelWriter<MarketActive> writer = new ExcelWriter<>();
		List<MarketActive> dataList = ms.exportById(ids);
		HSSFWorkbook workbook = writer.getWorkbook(MarketActive.class, dataList, "市场活动");
		OutputStream out = response.getOutputStream();
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment;filename=Activity"+System.currentTimeMillis()+".xls");
		workbook.write(out);
		
	}

	//导出
	protected void doExportAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MarketService ms = (MarketService) ServiceFactory.getServiceImpl("MarketService");
		ExcelWriter<MarketActive> reader = new ExcelWriter<>();
		List<MarketActive> dataList = ms.getAll();
		JSONUtil.getJson(dataList);
		HSSFWorkbook workbook = reader.getWorkbook(MarketActive.class, dataList, "市场活动");
		OutputStream out = response.getOutputStream();
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment;filename=Activity-"+System.currentTimeMillis()+".xls");
		workbook.write(out);
	}

		//通过备注id修改
		protected void UpdateNoteById(HttpServletRequest request, HttpServletResponse response) {
			String id = request.getParameter("id");
			String description = request.getParameter("description");
			String flag = "1";
			String editBy = ((User) request.getSession().getAttribute("user")).getUsername();
			String editTime = request.getParameter("editTime");;
			NoteActivity na = new NoteActivity();
			na.setId(id);
			na.setDescription(description);
			na.setEditBy(editBy);
			na.setEditTime(editTime);
			na.setEditFlag(flag);

			NoteActService nas = (NoteActService) ServiceFactory.getService(new NoteActServiceImpl());
			JSONUtil.booleanPrint(nas.updateNoteById(na), response);
			
		}

		//根据备注id删除
		protected void doDelNoteById(HttpServletRequest request, HttpServletResponse response) {
			String id = request.getParameter("id");
			NoteActService nas = (NoteActService) ServiceFactory.getService(new NoteActServiceImpl());
			boolean success = nas.delById(id);
			JSONUtil.booleanPrint(success, response);
			
		}

		//根据活动id删除
		protected void doDelByActId(HttpServletRequest request, HttpServletResponse response) {
			String p_actid[] = request.getParameterValues(("id"));
			NoteActService nas = (NoteActService) ServiceFactory.getService(new NoteActServiceImpl());

			try {
				nas.delete(p_actid);
				JSONUtil.booleanPrint(true, response);
			} catch (Exception e) {
				e.printStackTrace();
				JSONUtil.booleanPrint(false, response);
			}
		}

		protected void doAddNote(HttpServletRequest request, HttpServletResponse response) {
			String id = request.getParameter("id");
			String description = request.getParameter("description");
			String p_actId = request.getParameter("p_actId");
			String createBy = ((User) request.getSession().getAttribute("user")).getUsername();
			String flag = "0";
			
			NoteActivity na = new NoteActivity();
			na.setId(id);
			na.setDescription(description);
			na.setCreateBy(createBy);
			na.setCreateTime(DateUtil.getDate());
			na.setP_actId(p_actId);
			na.setEditFlag(flag);
			
			NoteActService nas = (NoteActService) ServiceFactory.getService(new NoteActServiceImpl());
			boolean success = nas.add(na);
			JSONUtil.booleanPrint(success, response);
		}

		protected void doGetNote(HttpServletRequest request, HttpServletResponse response) {
			String p_actid = request.getParameter("id");
			NoteActService nas = (NoteActService) ServiceFactory.getService(new NoteActServiceImpl());

			List<NoteActivity> nodeList= nas.getNode(p_actid);
			
			JSONUtil.getJsonOut(nodeList, response);
		}
	
	/**
	 * 通过id获取活动
	 */
	protected void doGetById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		MarketService ms = (MarketService) ServiceFactory.getService(new MarketServiceImpl());

		MarketActive marketActive = ms.getById(id);
		request.setAttribute("activity", marketActive);
		request.getRequestDispatcher("detail.jsp").forward(request, response);

	}

	protected void doGetMarktActiveOwner(HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		UserManageService um = (UserManageService) ServiceFactory.getServiceImpl("UserManageService");

		// {"owner":[{"id":"?","owner":"?"},]}
		List<Map<String, Object>> ownerMap = um.getOwner();
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(ownerMap);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(jsonData);

	}

	protected void doUpdateMarktActive(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String owner = request.getParameter("owner");// 
		String name = request.getParameter("name");
		String start_date = request.getParameter("startTime");
		String end_date = request.getParameter("endTime");
		String cost = request.getParameter("cost");
		String description = request.getParameter("description");


		MarketActive active = new MarketActive();
		active.setId(id);
		active.setName(name);
		active.setOwner(owner);
		active.setStart_date(start_date);
		active.setEnd_date(end_date);
		active.setCost(cost);
		active.setDescription(description);
		active.setEditer(((User)request.getSession().getAttribute(Const.SESSION_USER)).getUsername());
		active.setEditTime((DateUtil.getDate()));
		
		MarketService marketService = (MarketService) ServiceFactory.getService(new MarketServiceImpl());
		boolean success = marketService.updateMarktActive(active);

		JSONUtil.booleanPrint(success, response);

	}

	/**
	 * 删除市场活动需要将备注表也删除
	 */
	protected void doDelMarktActive(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ids[] = request.getParameterValues("id");
		JSONUtil.getJson(ids);
		MarketService marketService = (MarketService) ServiceFactory.getService(new MarketServiceImpl());

		try {
			//删除市场活动
			marketService.delMarktActive(ids);
			
			JSONUtil.booleanPrint(true, response);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.booleanPrint(false, response);
		}
		
	}

	protected void doAddMarktActive(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MarketActive active = null;
		try {
			active = ParamUtil.parseRequset(request, MarketActive.class);
			active.setId(UUIDUtil.getUuid());
			active.setCreateTime((DateUtil.getDate()));
			User user = (User) request.getSession().getAttribute("user");
			active.setCreater(user.getUsername());
			JSONUtil.getJson(active);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<MarketActive> list = new ArrayList<>();
		list.add(active);
		MarketService ms = (MarketService)ServiceFactory.getServiceImpl("MarketService");
		boolean success = ms.addMarketActive(list);

		if (success) {
			response.sendRedirect(request.getContextPath() + "/workbench/activity/index.jsp");
		}

	}

	/**
	 * 返回市场列表
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	protected void doQueryActive(HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> conditionMap = ParamUtil.getConditionMap(request);
		MarketService marketService = (MarketService) ServiceFactory.getServiceImpl("MarketService");
		
		PaginationVo<MarketActive> getPageContext = marketService.pageCondition(conditionMap);

		JSONUtil.getJsonOut(getPageContext, response);

	}
}
