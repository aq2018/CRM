package com.test.crm.web.transaction.service;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.PaginationVo;
import com.test.crm.domain.Transaction;

public interface TransactionService {

	/**
	 * 接收的参数：
		 * owner:所有者姓名
		 * customerId:客户名称
		 * contactId:联系人名称
	 * @param condition 所有者姓名、交易名称、客户名称、交易阶段、交易类型、交易来源、交易联系人名称
	 * @return 返回id、交易所有者姓名、客户名称、交易联系人名称
	 */
	PaginationVo<Map<String, Object>> queryByCondition(Map<String, Object> condition);

	/**
	 * 通过交易id 所有者姓名、返回客户名称、联系人名称、客户名称
	 * @param id
	 * @return
	 */
	Transaction getById(String id);

	/**
	 * 通过交易id 
	 * @param id
	 * @return 返回Map包括：
	 * 			  1.transaction 所有者id、客户名称、联系人名称、客户名称
	 * 			  2.ownerList [{"id","?","username","?"}]
	 */
	Map<String, Object> getById2(String id);

	/**
	 * 保存单条交易
	 * @param transaction 其中customerId暂时存放客户名称 dao层查询是否存在该客户名，
	 * 不存在则创建客户
	 * 存在则取出该客户id赋值给transaction customerId
	 * @return
	 */
	void save(Transaction transaction);

	/**
	 * 更新单条交易
	 * @param transaction 其中customerId暂时存放客户名称 dao层查询是否存在该客户名，
	 * 不存在则创建客户
	 * 存在则取出该客户id赋值给transaction customerId
	 * @return
	 */
	void update(Transaction transaction);

	/**
	 * 通过交易id删除多条交易记录
	 * @param ids
	 */
	void del(String[] ids);

	/**
	 * @param ids[] 交易id
	 * @return 导出选中交易
	 */
	List<Transaction> getByIds(String[] ids);

	/**
	 * 导出全部交易
	 * @return
	 */
	List<Transaction> getAll();

	/**
	 * 导入批量添加
	 * @param transList
	 * @return
	 */
	int save2(List<Transaction> transList);

	/**
	 * 通过联系人id 返回联系人关联的交易、customerId存放客户名称
	 * @param contactId
	 * @return
	 */
	List<Transaction> listByContactId(String contactId);

	/**
	 * 通过交易id删除交易及相关备注及历史
	 * @param transId
	 * @return
	 */
	void delById(String id);

	/**
	 * 通过客户id删除一条交易,可能是多条
	 * 同时删除交易历史 -->暂时没做
	 * @param customerId
	 * @return
	 */
	void delByCustomerId2(String customerId);

	/**
	 * 通过id更新点灯图标的阶段
	 * @param updateParam
	 * @return 更新者、更新时间、更新后的阶段
	 */
	boolean updateByStage(Transaction updateParam);

	/**
	 * 通过客户id获取 客户关联的交易
	 * @param customerId
	 * @return
	 */
	List<Transaction> getByCustomerId(String customerId);
}
