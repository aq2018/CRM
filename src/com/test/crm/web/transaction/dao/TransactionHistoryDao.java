package com.test.crm.web.transaction.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.TransactionHistory;

public interface TransactionHistoryDao {

	/**
	 * 保存交易历史
	 * @param transactionHistory
	 */
	void save(TransactionHistory transactionHistory);

	/**
	 * 获取交易历史
	 * @param transId
	 * @return
	 */
	List<TransactionHistory> listByTransId(Map<String, Object> map);

	Long count(Map<String, Object> map);

	/**
	 * 通过交易id 返回最近交易记录的上一条记录
	 * @param id
	 * @return
	 */
	String getPastStageByTransId(String id);

	/**
	 * 判断交易是从哪个阶段开始的
	 * @param transId
	 * @return 返回1表示刚开始的新业务
	 */
	int countByTransId(String transId);

	/**
	 * 通过交易id删除关联的历史
	 * @param transId[]
	 */
	void delByTransIds(String[] transId);

	/**
	 * 通过交易id删除关联的历史
	 * @param transId
	 */
	void delByTransId(String id);

}
