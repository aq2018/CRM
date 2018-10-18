package com.test.crm.web.transaction.service.impl;

import java.util.Map;

import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.TransactionHistory;
import com.test.crm.util.SqlSessionUtil;
import com.test.crm.web.transaction.dao.TransactionHistoryDao;
import com.test.crm.web.transaction.service.TransactionHistoryService;

public class TransactionHistoryServiceImpl implements TransactionHistoryService {
	private TransactionHistoryDao thd = SqlSessionUtil.getSqlSession().getMapper(TransactionHistoryDao.class);

	@Override
	public PaginationVo<TransactionHistory> listByTransId(Map<String, Object> map) {
		PaginationVo<TransactionHistory> pv = new PaginationVo<>();
		pv.setTotal(thd.count(map));
		pv.setDataList(thd.listByTransId(map));
		return pv;
	}

	@Override
	public String getPastStageByTransId(String id) {
		//int count = thd.countByTransId(id);
		/*if(count == 1){//新业务
			
		}else{//ch
			
		}*/
		return thd.getPastStageByTransId(id);
	}

}
