package com.test.crm.web.contact.controller;

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
import com.test.crm.domain.Customer;
import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.Remark;
import com.test.crm.domain.Transaction;
import com.test.crm.domain.User;
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
import com.test.crm.web.contact.service.ContactActRelationService;
import com.test.crm.web.contact.service.ContactRemarkService;
import com.test.crm.web.contact.service.ContactService;
import com.test.crm.web.transaction.service.TransactionService;


public class ContactController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UrlparttenUtil.get(this.getClass(), request, response);
	}
	
	protected void exportChk(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ids[] = request.getParameterValues("id");
		ContactService cs = (ContactService) ServiceFactory.getServiceImpl("ContactService");
		List<Contact> dataList = cs.getById3(ids);
		ExcelWriter<Contact> writer = new ExcelWriter<>();
		HSSFWorkbook workbook = writer.getWorkbook(Contact.class, dataList, "联系人列表");
		OutputStream out = response.getOutputStream();
		response.setContentType("octets/stream");
		response.setHeader("Content-Disposition", "attachment;filename=ContactList-" + System.currentTimeMillis() + ".xls");
		workbook.write(out);
	}

	protected void exportAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ContactService cs = (ContactService) ServiceFactory.getServiceImpl("ContactService");
		List<Contact> dataList = cs.getAll();
		ExcelWriter<Contact> writer = new ExcelWriter<>();
		HSSFWorkbook workbook = writer.getWorkbook(Contact.class, dataList, "联系人列表");
		OutputStream out = response.getOutputStream();
		response.setContentType("octets/stream");
		response.setHeader("Content-Disposition", "attachment;filename=ContactList-" + System.currentTimeMillis() + ".xls");
		workbook.write(out);
	}
	
	protected void imports(HttpServletRequest request, HttpServletResponse response) {
		String filePath = ExcelImport.imports(request);
		List<Contact> listContact = new ExcelReader().read(Contact.class, filePath);
		ContactService cs = (ContactService) ServiceFactory.getServiceImpl("ContactService");
		System.out.println("导入的数据条数:"+listContact.size());
		JSONUtil.booleanPrint(cs.add2(listContact), response);
	}
	
	protected void delAssociateAct(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ContactActRelationService cars = (ContactActRelationService)ServiceFactory.getServiceImpl("ContactActRelationService");
		JSONUtil.booleanPrint(cars.delAssociateAct(id), response);
	}
	
	protected void addAssociateAct(HttpServletRequest request, HttpServletResponse response) {
		String actIds[] = request.getParameterValues("id");
		String contactId = request.getParameter("contactId");
		ContactActRelationService cars = (ContactActRelationService)ServiceFactory.getServiceImpl("ContactActRelationService");
		
		JSONUtil.booleanPrint(cars.addAssociateAct(actIds,contactId), response);
		
	}
	
	//通过市场活动名称模糊查询位关联的活动
	protected void queryAssociateAct(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> condition = ParamUtil.getConditionMap(request);
		ContactActRelationService cars = (ContactActRelationService)ServiceFactory.getServiceImpl("ContactActRelationService");
		Map<String, Object> acts = cars.getActByContactIdAndName(condition);
		JSONUtil.getJsonOut(acts, response);
	}
	
	protected void getAssociateActs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		ContactActRelationService cars = (ContactActRelationService)ServiceFactory.getServiceImpl("ContactActRelationService");
		List<Map<String, Object>> carsList = cars.getActsByContactId(id);
		JSONUtil.getJsonOut(carsList, response);
	}
	
	protected void delRemark(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		ContactRemarkService crs = (ContactRemarkService)ServiceFactory.getServiceImpl("ContactRemarkService");
		boolean success = crs.delById(id);
		JSONUtil.booleanPrint(success, response);
	}
	
	
	protected void updateRemark(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Remark contactRrematk = ParamUtil.update(request, Remark.class);
		ContactRemarkService crs = (ContactRemarkService)ServiceFactory.getServiceImpl("ContactRemarkService");
		Map<String,Object> map = new HashMap<>();
		if(crs.update(contactRrematk)){
			map.put("success", true);
			map.put("contactRemark", contactRrematk);
		}else{
			map.put("success", false);
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void getRemark(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contactId = request.getParameter("id");
		ContactRemarkService crs = (ContactRemarkService) ServiceFactory.getServiceImpl("ContactRemarkService");
		List<Remark> remarkList= crs.getRemark(contactId);
		JSONUtil.getJsonOut(remarkList, response);
	}
	protected void addRemark(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Remark contactRrematk = ParamUtil.add(request, Remark.class);
		ContactRemarkService crs = (ContactRemarkService) ServiceFactory.getServiceImpl("ContactRemarkService");
		Map<String,Object> map = new HashMap<>();
		if(crs.add(contactRrematk)){
			map.put("success", true);
			map.put("contactRemark", contactRrematk);
		}else{
			map.put("success", false);
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		ContactService cs = (ContactService) ServiceFactory.getServiceImpl("ContactService");
		Map<String, Object> contact = cs.getById2(id);
		JSONUtil.getJson(contact);
		request.setAttribute("contact", contact);
		request.getRequestDispatcher("detail.jsp").forward(request, response);
	}
	
	protected void query(HttpServletRequest request, HttpServletResponse response) {
		ContactService cs = (ContactService) ServiceFactory.getServiceImpl("ContactService");
		Map<String, Object> condition = ParamUtil.getConditionMap(request);
		PaginationVo<Contact> pv = cs.query(condition);
		JSONUtil.getJsonOut(pv, response);
	}
	
	protected void getOwner(HttpServletRequest request, HttpServletResponse response) {
		UserManageService um = (UserManageService) ServiceFactory.getServiceImpl("UserManageService");

		List<Map<String, Object>> ownerMap = um.getOwner();
		JSONUtil.getJsonOut(ownerMap, response);
	}

	protected void add(HttpServletRequest request, HttpServletResponse response) {
		Contact contact = ParamUtil.add(request, Contact.class);
		JSONUtil.getJson(contact);
		ContactService cs = (ContactService) ServiceFactory.getServiceImpl("ContactService");
		JSONUtil.booleanPrint(cs.add(contact), response);
		
	}
	
	protected void del(HttpServletRequest request, HttpServletResponse response) {
		String ids[] = request.getParameterValues("id");
		ContactService cs = (ContactService) ServiceFactory.getServiceImpl("ContactService");
		Map<String, Boolean> map = new HashMap<>();
		try {
			cs.delByIds(ids);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		
		JSONUtil.getJsonOut(map, response);
		
	}
	
	protected void edit(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ContactService cs = (ContactService) ServiceFactory.getServiceImpl("ContactService");
		Map<String, Object> contact = cs.getById(id);
		JSONUtil.getJsonOut(contact, response);
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String owner = request.getParameter("owner");
		String source = request.getParameter("source");
		String customerId = request.getParameter("customerId");
		String customerName = request.getParameter("customerName");
		String fullname = request.getParameter("fullname");
		String appellation = request.getParameter("appellation");
		String email = request.getParameter("email");
		String mphone = request.getParameter("mphone");
		String job = request.getParameter("job");
		String birth = request.getParameter("birth");
		String description = request.getParameter("description");
		String contactSummary = request.getParameter("contactSummary");
		String nextContactTime = request.getParameter("nextContactTime");
		String address = request.getParameter("address");
		
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		if(user == null){
			user = new User();
			user.setUsername("没有登录，自动添加测试用户");
		}
		Customer customer = new Customer();
		customer.setId(customerId);
		customer.setName(customerName);
		customer.setEditBy(user.getUsername());
		customer.setEditTime(DateUtil.getDate());
		
		
		Contact contact = new Contact();
		contact.setId(id);
		contact.setOwner(owner);
		contact.setSource(source);
		contact.setFullname(fullname);
		contact.setAppellation(appellation);
		contact.setEmail(email);
		contact.setMphone(mphone);
		contact.setJob(job);
		contact.setBirth(birth);
		contact.setDescription(description);
		contact.setContactSummary(contactSummary);
		contact.setNextContactTime(nextContactTime);
		contact.setAddress(address);
		contact.setEditBy(user.getUsername());
		contact.setEditTime(DateUtil.getDate());
		Map<String, Boolean> map = new HashMap<>();
		
		ContactService cts = (ContactService) ServiceFactory.getServiceImpl("ContactService");
		try {
			cts.update(contact,customer);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		JSONUtil.getJsonOut(map, response);
	}
	protected void transInfo(HttpServletRequest request, HttpServletResponse response) {
		String contactId = request.getParameter("contactId");
		TransactionService ts = (TransactionService)ServiceFactory.getServiceImpl("TransactionService");
		List<Transaction> transList = ts.listByContactId(contactId);
		Map<String, String> pMap = (Map<String, String>)request.getServletContext().getAttribute("possibilityMap");
		
		for (Transaction transaction : transList) {
			String stage = transaction.getStage();
			transaction.setPossibility(pMap.get(stage));
		}
		JSONUtil.getJsonOut(transList, response);
	}
	
	protected void delTrans(HttpServletRequest request, HttpServletResponse response) {
		String transId = request.getParameter("transId");
		TransactionService ts = (TransactionService)ServiceFactory.getServiceImpl("TransactionService");
		Map<String, Boolean> map = new HashMap<>();
		try {
			ts.delById(transId);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
}
