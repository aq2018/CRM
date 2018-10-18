package com.test.crm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.test.crm.domain.Clue;
import com.test.crm.domain.Remark;
import com.test.crm.domain.Transaction;
import com.test.crm.domain.User;
import com.test.crm.service.ClueActRelationService;
import com.test.crm.service.ClueRemarkService;
import com.test.crm.service.ClueService;
import com.test.crm.service.DictionaryValueService;
import com.test.crm.service.UserManageService;
import com.test.crm.service.impl.DictionaryValueServiceImpl;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.Const;
import com.test.crm.util.ExcelImport;
import com.test.crm.util.ExcelReader;
import com.test.crm.util.ExcelWriter;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.ParamUtil;

public class ClueController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println("path:"+path);
		
		
		if("/workbench/clue/save.do".equals(path)){
			doAdd(request,response);
		} else if("/workbench/clue/getByCondition.do".equals(path)){
			doGetByCondition(request,response);
		} else if("/workbench/clue/edit.do".equals(path)){
			doEdit(request,response);
		} else if("/workbench/clue/update.do".equals(path)){
			doUpdate(request,response);
		} else if("/workbench/clue/delete.do".equals(path)){
			doDel(request,response);
		} else if("/workbench/clue/exportAll.do".equals(path)){
			doExportAll(request,response);
		} else if("/workbench/clue/exportChk.do".equals(path)){
			doExportChk(request,response);
		} else if("/workbench/clue/import.do".equals(path)){
			doImport(request,response);
		} else if("/workbench/clue/detail.do".equals(path)){
			doDetail(request,response);
		} else if("/workbench/clue/addRemark.do".equals(path)){
			doAddRemark(request,response);
		} else if("/workbench/clue/getRemarks.do".equals(path)){
			doGetRemarks(request,response);
		} else if("/workbench/clue/updateRemark.do".equals(path)){
			doupdateRemark(request,response);
		} else if("/workbench/clue/delRemark.do".equals(path)){
			doDelRemark(request,response);
		} else if("/workbench/clue/queryAssociateAct.do".equals(path)){
			doQueryAssociateAct(request,response);
		} else if("/workbench/clue/getAssociateActs.do".equals(path)){
			doGetAssociateActs(request,response);
		} else if("/workbench/clue/addAssociateAct.do".equals(path)){
			doAddAssociateAct(request,response);
		} else if("/workbench/clue/delAssociateAct.do".equals(path)){
			doDelAssociateAct(request,response);
		} else if("/workbench/clue/getOwner.do".equals(path)){
			doGetOwner(request,response);
		}  else if("/workbench/clue/getActsOrigin.do".equals(path)){
			doGetActsOriginByName(request,response);
		} else if("/workbench/clue/convert.do".equals(path)){
			doConvert(request,response);
		}
		
	}
	private void doConvert(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("clueId");
		String flag = request.getParameter("flag");
		String amountOfMoney = request.getParameter("amountOfMoney");
		String tradeName = request.getParameter("tradeName");
		String expectedDate = request.getParameter("expectedClosingDate");
		String stage = request.getParameter("stage");
		String activityId = request.getParameter("actId");
		Transaction transaction = null;
		if("1".equals(flag)){
			transaction = new Transaction();
			transaction.setMoney(amountOfMoney);
			transaction.setName(tradeName);
			transaction.setExpectedDate(expectedDate);
			transaction.setStage(stage);
			transaction.setActivityId(activityId);
		}
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		if(user == null){
			user = new User();
			user.setUsername("没有登录，测试用例");
		}
		String operator = user.getUsername();
		ClueService cs = (ClueService) ServiceFactory.getServiceImpl("ClueService");
		try {
			cs.convert(id,operator,transaction);
			response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void doGetActsOriginByName(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> condition = ParamUtil.getConditionMap(request);
		JSONUtil.getJson(condition);
		ClueActRelationService cars = (ClueActRelationService) ServiceFactory.getServiceImpl("ClueActRelationService");
		Map<String, Object> actsOrigin = cars.getActsOriginByClueIdAndName(condition);
		JSONUtil.getJsonOut(actsOrigin, response);
	}
	private void doGetOwner(HttpServletRequest request, HttpServletResponse response) {
		UserManageService um = (UserManageService) ServiceFactory.getServiceImpl("UserManageService");

		List<Map<String, Object>> ownerMap = um.getOwner();
		JSONUtil.getJsonOut(ownerMap, response);
	}
	private void doDelAssociateAct(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ClueActRelationService cars = (ClueActRelationService) ServiceFactory.getServiceImpl("ClueActRelationService");
		JSONUtil.booleanPrint(cars.delAssociateAct(id), response);
	}
	private void doAddAssociateAct(HttpServletRequest request, HttpServletResponse response) {
		String actIds[] = request.getParameterValues("id");
		String p_clueId = request.getParameter("clueId");
		ClueActRelationService cars = (ClueActRelationService) ServiceFactory.getServiceImpl("ClueActRelationService");
		
		JSONUtil.booleanPrint(cars.addAssociateAct(actIds,p_clueId), response);
		
	}
	private void doGetAssociateActs(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ClueActRelationService cars = (ClueActRelationService) ServiceFactory.getServiceImpl("ClueActRelationService");
		List<Map<String, Object>> carsList = cars.getActsByClueId(id);
		JSONUtil.getJsonOut(carsList, response);
	}
	
	//通过市场活动名称模糊查询位关联的活动
	private void doQueryAssociateAct(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String clueId = request.getParameter("clueId");
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		
		Map<String, Object> condition = new HashMap<>();
		condition.put("pageNo", (pageNo - 1) * pageSize);
		condition.put("pageSize", pageSize);
		condition.put("name", name);
		condition.put("clueId", clueId);
		
		ClueActRelationService cars = (ClueActRelationService) ServiceFactory.getServiceImpl("ClueActRelationService");
		Map<String, Object> acts = cars.getActByClueIdAndName(condition);
		JSONUtil.getJsonOut(acts, response);
	}

	//根据备注id删除一条备注
	private void doDelRemark(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ClueRemarkService crs = (ClueRemarkService)ServiceFactory.getServiceImpl("ClueRemarkService");
		boolean success = crs.delById(id);
		JSONUtil.booleanPrint(success, response);
	}

	private void doupdateRemark(HttpServletRequest request, HttpServletResponse response) {
		Remark clueRrematk = ParamUtil.update(request, Remark.class);
		ClueRemarkService crs = (ClueRemarkService)ServiceFactory.getServiceImpl("ClueRemarkService");
		Map<String,Object> map = new HashMap<>();
		if(crs.update(clueRrematk)){
			map.put("success", true);
			map.put("clueRemark", clueRrematk);
		}else{
			map.put("success", false);
		}
		JSONUtil.getJsonOut(map, response);
	}

	private void doGetRemarks(HttpServletRequest request, HttpServletResponse response) {
		String p_clueid = request.getParameter("id");
		ClueRemarkService crs = (ClueRemarkService)ServiceFactory.getServiceImpl("ClueRemarkService");
		List<Remark> remarkList= crs.getRemarks(p_clueid);
		JSONUtil.getJsonOut(remarkList, response);
		
	}

	private void doAddRemark(HttpServletRequest request, HttpServletResponse response) {
		Remark clueRrematk = ParamUtil.add(request, Remark.class);
		ClueRemarkService crs = (ClueRemarkService)ServiceFactory.getServiceImpl("ClueRemarkService");
		Map<String,Object> map = new HashMap<>();
		if(crs.add(clueRrematk)){
			map.put("success", true);
			map.put("clueRemark", clueRrematk);
		}else{
			map.put("success", false);
		}
		JSONUtil.getJsonOut(map, response);
	}

	private void doDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String ids[] = request.getParameterValues("id");
		ClueService cs = (ClueService) ServiceFactory.getServiceImpl("ClueService");
		List<Clue> listClue = cs.getById(ids);
		JSONUtil.getJsonOut(listClue.get(0), response);
		request.setAttribute("clue", listClue.get(0));
		request.getRequestDispatcher("detail.jsp").forward(request, response);;
	}

	private void doImport(HttpServletRequest request, HttpServletResponse response) {
		String filePath = ExcelImport.imports(request);
		List<Clue> listClue = new ExcelReader().read(Clue.class, filePath);
		System.out.println("导入的数据条数:"+listClue.size());
		JSONUtil.getJson(listClue);
		ClueService cs = (ClueService) ServiceFactory.getServiceImpl("ClueService");
		JSONUtil.booleanPrint(cs.add(listClue), response);
	}

	private void doExportChk(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ids[] = request.getParameterValues("id");
		ClueService cs = (ClueService) ServiceFactory.getServiceImpl("ClueService");
		List<Clue> dataList = cs.getById(ids);
		
		HSSFWorkbook workbook = new ExcelWriter<Clue>().getWorkbook(Clue.class, dataList, "线索列表");
		OutputStream out = response.getOutputStream();
		response.setContentType("octets/stream");
		response.setHeader("Content-Disposition", "attachment;filename=ClueList-" + System.currentTimeMillis() + ".xls");
		workbook.write(out);
	}

	private void doExportAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ClueService cs = (ClueService) ServiceFactory.getServiceImpl("ClueService");
		List<Clue> dataList = cs.getAll();
		ExcelWriter<Clue> writer = new ExcelWriter<>();
		HSSFWorkbook workbook = writer.getWorkbook(Clue.class, dataList, "线索列表");
		OutputStream out = response.getOutputStream();
		response.setContentType("octets/stream");
		response.setHeader("Content-Disposition", "attachment;filename=ClueList-" + System.currentTimeMillis() + ".xls");
		workbook.write(out);
	}

	//删除线索同时删除线索关联的备注和关联的市场
	private void doDel(HttpServletRequest request, HttpServletResponse response) {
		String ids[] = request.getParameterValues("id");
		Map<String, Object> map = new HashMap<>();
		ClueService cs = (ClueService) ServiceFactory.getServiceImpl("ClueService");
		try {
			cs.del(ids);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		JSONUtil.getJsonOut(map, response);
		
	}

	private void doGetClueSource(HttpServletRequest request, HttpServletResponse response) {
		DictionaryValueService dvs = (DictionaryValueService) ServiceFactory.getServiceImpl("DictionaryValueService");
		JSONUtil.getJsonOut(dvs.getClueSource(), response);
	}

	private void doGetClueState(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DictionaryValueService dvs = (DictionaryValueService) ServiceFactory.getServiceImpl("DictionaryValueService");
		String json = JSONUtil.getJson(dvs.getClueState());
		response.getWriter().print(json);
		
	}

	private void doEdit(HttpServletRequest request, HttpServletResponse response) {
		String ids[] = request.getParameterValues("id");
		ClueService cs = (ClueService) ServiceFactory.getServiceImpl("ClueService");
		List<Clue> list = cs.getById(ids);
		JSONUtil.getJsonOut(list, response);
	}

	private void doUpdate(HttpServletRequest request, HttpServletResponse response) {
		Clue clue = ParamUtil.update(request, Clue.class);
		ClueService cs = (ClueService) ServiceFactory.getServiceImpl("ClueService");
 		JSONUtil.booleanPrint(cs.update(clue), response);
	}

	private void doGetCalls(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DictionaryValueService dvs = (DictionaryValueService) ServiceFactory.getService(new DictionaryValueServiceImpl());
		String json = JSONUtil.getJson(dvs.getCalls());
		response.getWriter().print(json);
	}

	private void doGetByCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> condition = ParamUtil.getConditionMap(request);
		ClueService cs =  (ClueService) ServiceFactory.getServiceImpl("ClueService");
		Map<String, Object> map = cs.doGetByCondition(condition);
		
		JSONUtil.getJsonOut(map, response);
	}

	private void doAdd(HttpServletRequest request, HttpServletResponse response) {
		
		Clue clue = ParamUtil.add(request, Clue.class);
		List<Clue> listClue = new ArrayList<>();
		listClue.add(clue);
		JSONUtil.getJson(listClue);
		ClueService cs =  (ClueService) ServiceFactory.getServiceImpl("ClueService");
		
		JSONUtil.booleanPrint(cs.add(listClue), response);
		
	}
}
