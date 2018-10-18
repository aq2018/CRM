package com.test.crm.web.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.crm.domain.CustomerRemark;
import com.test.crm.util.JSONUtil;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.web.customer.dao.CustomerRemarkDao;
import com.test.crm.web.customer.service.CustomerRemarkService;

public class CustomerRemarkServiceImpl implements CustomerRemarkService{
	public static void main(String[] args) {
		CustomerRemarkServiceImpl l = new CustomerRemarkServiceImpl();
		CustomerRemark customerRemark = new CustomerRemark();
		customerRemark.setId("1");
		customerRemark.setEditBy("2");
		JSONUtil.getJson(l.delById("1"));
	}
	private CustomerRemarkDao crd = (CustomerRemarkDao) SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
	@Override
	public Map<String, Object> add(CustomerRemark customerRemark) {
		Map<String,Object> map = new HashMap<>();
		if(crd.add(customerRemark) == 1){
			map.put("success", true);
			map.put("remark", crd.getById(customerRemark.getId()));
		}else{
			map.put("success", false);
		}
		return map;
	}
	@Override
	public List<Map<String, Object>> getListByCustomerId(String customerId) {
		return crd.getListByCustomerId(customerId);
	}
	@Override
	public Map<String, Object> update(CustomerRemark customerRemark) {
		Map<String,Object> map = new HashMap<>();
		if(crd.update(customerRemark) == 1){
			map.put("success", true);
			map.put("remark", customerRemark);
		}else{
			map.put("success", false);
		}
		return map;
	}
	@Override
	public boolean delById(String id) {
		return crd.delById(id) == 1;
	}

}
