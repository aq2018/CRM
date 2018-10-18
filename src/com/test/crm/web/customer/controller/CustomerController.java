package com.test.crm.web.customer.controller;

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
import com.test.crm.domain.CustomerRemark;
import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.Transaction;
import com.test.crm.service.UserManageService;
import com.test.crm.service.impl.ServiceFactory;
import com.test.crm.util.Const;
import com.test.crm.util.ExcelImport;
import com.test.crm.util.ExcelReader;
import com.test.crm.util.ExcelWriter;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.ParamUtil;
import com.test.crm.util.UrlparttenUtil;
import com.test.crm.web.contact.service.ContactService;
import com.test.crm.web.customer.service.CustomerRemarkService;
import com.test.crm.web.customer.service.CustomerService;
import com.test.crm.web.transaction.service.TransactionService;

public class CustomerController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UrlparttenUtil.get(this.getClass(), request, response);
		
	}

	protected void delRemark(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		CustomerRemarkService crs = (CustomerRemarkService)ServiceFactory.getServiceImpl("CustomerRemarkService");
		JSONUtil.booleanPrint(crs.delById(id), response);
	}

	protected void updateRemark(HttpServletRequest request, HttpServletResponse response) {
		CustomerRemark customerRemark = ParamUtil.update(request, CustomerRemark.class);
		CustomerRemarkService crs = (CustomerRemarkService)ServiceFactory.getServiceImpl("CustomerRemarkService");
		Map<String, Object> map = crs.update(customerRemark);
		JSONUtil.getJsonOut(map, response);
	}

	protected void getRemarks(HttpServletRequest request, HttpServletResponse response) {
		String customerId = request.getParameter("customerId");
		CustomerRemarkService crs = (CustomerRemarkService)ServiceFactory.getServiceImpl("CustomerRemarkService");
		List<Map<String, Object>> map = crs.getListByCustomerId(customerId);
		JSONUtil.getJsonOut(map, response);
	}

	protected void addRemark(HttpServletRequest request, HttpServletResponse response) {
		CustomerRemark customerRemark = ParamUtil.add(request, CustomerRemark.class);
		CustomerRemarkService crs = (CustomerRemarkService)ServiceFactory.getServiceImpl("CustomerRemarkService");
		Map<String, Object> map = crs.add(customerRemark);
		JSONUtil.getJsonOut(map, response);
		
	}

	protected void del(HttpServletRequest request, HttpServletResponse response) {
		CustomerService cs = (CustomerService) ServiceFactory.getServiceImpl("CustomerService");
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

	protected void exportAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CustomerService cs = (CustomerService) ServiceFactory.getServiceImpl("CustomerService");
		List<Customer> dataList = cs.getAll();
		ExcelWriter<Customer> reader = new ExcelWriter<>();
		HSSFWorkbook workbook = reader.getWorkbook(Customer.class, dataList, "客户列表");
		OutputStream out = response.getOutputStream();
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment;filename=Customer-"+System.currentTimeMillis()+".xls");
		workbook.write(out);

	}

	protected void exportChk(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CustomerService cs = (CustomerService) ServiceFactory.getServiceImpl("CustomerService");
		String ids[] = request.getParameterValues("id");
		List<Customer> dataList = cs.getById(ids);
		JSONUtil.getJson(dataList);
		ExcelWriter<Customer> reader = new ExcelWriter<>();
		HSSFWorkbook workbook = reader.getWorkbook(Customer.class, dataList, "客户列表");
		OutputStream out = response.getOutputStream();
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment;filename=Customer-"+System.currentTimeMillis()+".xls");
		workbook.write(out);
		
	}

	protected void update(HttpServletRequest request, HttpServletResponse response) {
		CustomerService cs = (CustomerService) ServiceFactory.getServiceImpl("CustomerService");
		Customer customer = ParamUtil.update(request, Customer.class);
		JSONUtil.booleanPrint(cs.update(customer), response);
		
	}

	protected void edit(HttpServletRequest request, HttpServletResponse response) {
		CustomerService cs = (CustomerService) ServiceFactory.getServiceImpl("CustomerService");
		String ids[] = request.getParameterValues("id");
		List<Customer> list = cs.getById(ids);
		Customer customer = list.get(0);; 
		JSONUtil.getJsonOut(customer, response);
	}

	protected void imports(HttpServletRequest request, HttpServletResponse response) {
		CustomerService cs = (CustomerService) ServiceFactory.getServiceImpl("CustomerService");
		String filePath = ExcelImport.imports(request);
		List<Customer> liCustomers = new ExcelReader().read(Customer.class, filePath);
		System.out.println("导入的数据条数:"+liCustomers.size());
		Map<String, Object> map = new HashMap<>();
		map.put("success", cs.add(liCustomers));
		map.put("count", liCustomers.size());
		JSONUtil.getJsonOut(map, response);
//		JSONUtil.booleanPrint(cs.add(liCustomers), response);
		//这里重构下输出成功导入多少条数据
		/*try {
			int count = cs.add(liCustomers);
			map.put("success", true);
			map.put("count", count);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		JSONUtil.getJsonOut(map, response);*/
	}

	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerService cs = (CustomerService) ServiceFactory.getServiceImpl("CustomerService");
		String id = request.getParameter("id");
		Map<String, Object> customer = cs.getById2(id);
		JSONUtil.getJson(customer);
		request.setAttribute("customer", customer);
		request.getRequestDispatcher("detail.jsp").forward(request, response);
		
	}

	protected void query(HttpServletRequest request, HttpServletResponse response) {
		CustomerService cs = (CustomerService) ServiceFactory.getServiceImpl("CustomerService");
		Map<String, Object> condition = ParamUtil.getConditionMap(request);
		PaginationVo<Customer> pv = cs.query(condition);
		JSONUtil.getJsonOut(pv, response);
		
	}

	protected void add(HttpServletRequest request, HttpServletResponse response) {
		CustomerService cs = (CustomerService) ServiceFactory.getServiceImpl("CustomerService");
		Customer customer = ParamUtil.add(request, Customer.class);
		
		JSONUtil.booleanPrint(cs.add2(customer), response);
	}

	protected void getOwner(HttpServletRequest request, HttpServletResponse response) {
		UserManageService us = (UserManageService) ServiceFactory.getServiceImpl("UserManageService");
		JSONUtil.getJsonOut(us.getOwner(), response);
	}
	
	protected void addContact(HttpServletRequest request, HttpServletResponse response) {
		Contact contact = ParamUtil.add(request, Contact.class);
		ContactService cs = (ContactService)ServiceFactory.getServiceImpl("ContactService");
		JSONUtil.booleanPrint(cs.add(contact), response);
	}
	
	protected void getContacts(HttpServletRequest request, HttpServletResponse response) {
		String customerId = request.getParameter("id");
		ContactService cs = (ContactService)ServiceFactory.getServiceImpl("ContactService");
		List<Contact> contactList = cs.listByCustomerId(customerId);
		JSONUtil.getJsonOut(contactList, response);
	}
	
	protected void delContact(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ContactService cs = (ContactService)ServiceFactory.getServiceImpl("ContactService");
		Map<String, Boolean> map = new HashMap<>();
		try {
			cs.delById(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		JSONUtil.getJsonOut(map, response);
	}
	
	protected void searchCustome(HttpServletRequest request, HttpServletResponse response) {
		String customerName = request.getParameter("name");
		CustomerService cs = (CustomerService)ServiceFactory.getServiceImpl("CustomerService");
		List<String> listName = cs.listByName(customerName);
		JSONUtil.getJsonOut(listName, response);
	}
	
	protected void transInfo(HttpServletRequest request, HttpServletResponse response) {
		String customerId = request.getParameter("customerId");
		TransactionService ts = (TransactionService)ServiceFactory.getServiceImpl("TransactionService");
		List<Transaction> transList =	ts.getByCustomerId(customerId);
		Map<String, String> pMap = (Map<String, String>)request.getServletContext().getAttribute(Const.PMAP);
		for (Transaction trans : transList) {
			trans.setPossibility(pMap.get(trans.getStage()));
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
