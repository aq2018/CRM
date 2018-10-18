package com.test.crm.web.transaction.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.test.crm.domain.Contact;
import com.test.crm.domain.DictionaryValue;
import com.test.crm.domain.MarketActive;
import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.Remark;
import com.test.crm.domain.Transaction;
import com.test.crm.domain.TransactionHistory;
import com.test.crm.domain.User;
import com.test.crm.service.MarketService;
import com.test.crm.service.UserManageService;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.Const;
import com.test.crm.util.DateUtil;
import com.test.crm.util.ExcelImport;
import com.test.crm.util.ExcelReader;
import com.test.crm.util.ExcelWriter;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.ParamUtil;
import com.test.crm.util.UrlparttenUtil;
import com.test.crm.web.contact.service.ContactService;
import com.test.crm.web.transaction.service.TransRemarkService;
import com.test.crm.web.transaction.service.TransactionHistoryService;
import com.test.crm.web.transaction.service.TransactionService;

public class TransactionController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UrlparttenUtil.get(this.getClass(), request, response);
	}
	
	protected void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserManageService ums = (UserManageService) ServiceFactory.getServiceImpl("UserManageService");
		List<Map<String, Object>> ownerList = ums.getOwner();
		request.setAttribute("ownerList", ownerList);
		request.getRequestDispatcher("save.jsp").forward(request, response);
	}
	
	protected void queryAct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> condition = ParamUtil.getConditionMap(request);
		MarketService ms = (MarketService) ServiceFactory.getServiceImpl("MarketService");
		PaginationVo<MarketActive> list = ms.listByName(condition);
		JSONUtil.getJsonOut(list, response);
	}
	
	protected void queryContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> condition = ParamUtil.getConditionMap(request);
		ContactService cs = (ContactService) ServiceFactory.getServiceImpl("ContactService");
		PaginationVo<Contact> list = cs.queryByName(condition);
		JSONUtil.getJsonOut(list, response);
	}
	
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TransactionService cs = (TransactionService) ServiceFactory.getServiceImpl("TransactionService");
		Map<String, Object> condition = ParamUtil.getConditionMap(request);
		PaginationVo<Map<String, Object>> list = cs.queryByCondition(condition);
		JSONUtil.getJsonOut(list, response);
	}
	
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Transaction transaction = ParamUtil.add(request, Transaction.class);
		TransactionService cs = (TransactionService) ServiceFactory.getServiceImpl("TransactionService");
		Map<String, Object> map = new HashMap<>();
		
		try {
			cs.save(transaction);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		TransactionService cs = (TransactionService) ServiceFactory.getServiceImpl("TransactionService");
		Transaction transaction = cs.getById(id);
		Map<String, String> possibilityMap = (Map<String, String>) request.getServletContext().getAttribute("possibilityMap");
		transaction.setPossibility(possibilityMap.get(transaction.getStage()));
		
		TransactionHistoryService ths = (TransactionHistoryService) ServiceFactory.getServiceImpl("TransactionHistoryService");
		String pastStage = ths.getPastStageByTransId(id);
		request.setAttribute("transaction", transaction);
		request.setAttribute("pastStage", pastStage);
		JSONUtil.getJson(transaction);
		request.getRequestDispatcher("detail.jsp").forward(request, response);
	}
	
	protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		TransactionService cs = (TransactionService) ServiceFactory.getServiceImpl("TransactionService");
		Map<String, Object> map = cs.getById2(id);
		
		request.setAttribute("map", map);
		JSONUtil.getJson(map);
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Transaction transaction = ParamUtil.update(request, Transaction.class);
		TransactionService cs = (TransactionService) ServiceFactory.getServiceImpl("TransactionService");
		Map<String, Boolean> map = new HashMap<>();
		try {
			cs.update(transaction);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void updateByStage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String  stageVal = request.getParameter("stageVal");
		String  expectedDate = request.getParameter("expectedDate");
		String  money = request.getParameter("money");
		
		List<DictionaryValue> dvList = (List<DictionaryValue>) request.getServletContext().getAttribute("transactionStageList");
		String stage = null;
		for (DictionaryValue dv : dvList) {
			if(stageVal.equals(dv.getValue())){
				stage = dv.getContent();
				break;
			}
		}
		Transaction updateParam = new Transaction();
		updateParam.setId(id);
		updateParam.setStage(stage);
		updateParam.setMoney(money);
		updateParam.setExpectedDate(expectedDate);
		updateParam.setEditTime(DateUtil.getDate());
		updateParam.setEditBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getUsername());
		TransactionService cs = (TransactionService) ServiceFactory.getServiceImpl("TransactionService");
		
		boolean success = cs.updateByStage(updateParam);
		Map<String, Object> pMap = (Map<String, Object>) request.getServletContext().getAttribute("possibilityMap");
		String possibility = (String) pMap.get(stage);
		updateParam.setPossibility(possibility);
		
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("trans", updateParam);
			map.put("success", success);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	//交易备注
	protected void delRemark(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		TransRemarkService crs = (TransRemarkService)ServiceFactory.getServiceImpl("TransRemarkService");
		JSONUtil.booleanPrint(crs.delById(id), response);
	}

	protected void updateRemark(HttpServletRequest request, HttpServletResponse response) {
		Remark remark = ParamUtil.update(request, Remark.class);
		TransRemarkService crs = (TransRemarkService)ServiceFactory.getServiceImpl("TransRemarkService");
		Map<String,Object> map = new HashMap<>();
		if(crs.update(remark)){
			map.put("success", true);
			map.put("remark", remark);
		}else{
			map.put("success", false);
		}
		JSONUtil.getJsonOut(map, response);
	}

	protected void getRemarks(HttpServletRequest request, HttpServletResponse response) {
		String transId = request.getParameter("transId");
		TransRemarkService crs = (TransRemarkService)ServiceFactory.getServiceImpl("TransRemarkService");
		List<Remark> map = crs.listByCustomerId(transId);
		JSONUtil.getJsonOut(map, response);
	}

	protected void addRemark(HttpServletRequest request, HttpServletResponse response) {
		Remark remark = ParamUtil.add(request, Remark.class);
		TransRemarkService crs = (TransRemarkService)ServiceFactory.getServiceImpl("TransRemarkService");
		Map<String,Object> map = new HashMap<>();
		if(crs.save(remark)){
			map.put("success", true);
			map.put("remark", remark);
		}else{
			map.put("success", false);
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	//交易历史
	protected void history(HttpServletRequest request, HttpServletResponse response) {
		String transId = request.getParameter("transId");
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		Map<String, Object> map = new HashMap<>();
		map.put("transId", transId);
		map.put("pageNo", (pageNo-1)*pageSize);
		map.put("pageSize", pageSize);
		TransactionHistoryService ths = (TransactionHistoryService)ServiceFactory.getServiceImpl("TransactionHistoryService");
		PaginationVo<TransactionHistory> pv = ths.listByTransId(map);
		JSONUtil.getJsonOut(pv, response);
	}
	
	protected void del(HttpServletRequest request, HttpServletResponse response) {
		TransactionService cs = (TransactionService) ServiceFactory.getServiceImpl("TransactionService");
		String ids[] = request.getParameterValues("id");
		Map<String, Boolean> map = new HashMap<>();
		
		try {
			cs.del(ids);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void imports(HttpServletRequest request, HttpServletResponse response) {
		TransactionService cs = (TransactionService) ServiceFactory.getServiceImpl("TransactionService");
		String filePath = ExcelImport.imports(request);
		List<Transaction> transList = new ExcelReader().read(Transaction.class, filePath);
		System.out.println("导入的数据条数:"+transList.size());
		Map<String, Object> map = new HashMap<>();
		
		try {
			int count = cs.save2(transList);
			map.put("success", true);
			map.put("count", count);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		
		JSONUtil.getJsonOut(map, response);
	}
	protected void exportAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TransactionService cs = (TransactionService) ServiceFactory.getServiceImpl("TransactionService");
		List<Transaction> dataList = cs.getAll();
		ExcelWriter<Transaction> reader = new ExcelWriter<>();
		HSSFWorkbook workbook = reader.getWorkbook(Transaction.class, dataList, "客户列表");
		OutputStream out = response.getOutputStream();
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment;filename=Transaction-"+System.currentTimeMillis()+".xls");
		workbook.write(out);

	}

	protected void exportChk(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TransactionService cs = (TransactionService) ServiceFactory.getServiceImpl("TransactionService");
		String ids[] = request.getParameterValues("id");
		List<Transaction> dataList = cs.getByIds(ids);
		JSONUtil.getJson(dataList);
		ExcelWriter<Transaction> reader = new ExcelWriter<>();
		HSSFWorkbook workbook = reader.getWorkbook(Transaction.class, dataList, "客户列表");
		OutputStream out = response.getOutputStream();
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment;filename=Transaction-"+System.currentTimeMillis()+".xls");
		workbook.write(out);
	}
}
