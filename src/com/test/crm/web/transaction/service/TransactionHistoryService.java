package com.test.crm.web.transaction.service;

import java.util.Map;

import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.TransactionHistory;

public interface TransactionHistoryService {

	PaginationVo<TransactionHistory> listByTransId(Map<String, Object> map);

	/**
	 * 通过交易id 返回最近交易记录的上一条记录
	 * @param id
	 * @return
	 */
	String getPastStageByTransId(String id);

}
