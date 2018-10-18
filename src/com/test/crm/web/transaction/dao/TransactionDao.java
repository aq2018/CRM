package com.test.crm.web.transaction.dao;

import java.util.List;
import java.util.Map;

import com.test.crm.domain.Transaction;

public interface TransactionDao {

	/**
	 * 接收的参数：
		 * owner:所有者姓名
		 * customerId:客户名称
		 * contactId:联系人名称
	 * @param condition
	 * @return 总条数
	 */
	Long countByCondition(Map<String, Object> condition);

	/**
	 * 接收的参数：
		 * owner:所有者姓名
		 * customerId:客户名称
		 * contactId:联系人名称
	 * @param condition
	 * @return 返回List<Map> id、交易所有者姓名、客户名称、交易联系人名称
	 */
	List<Map<String, Object>> listByCondition(Map<String, Object> condition);

	/**
	 * @param id 交易id 
	 * @return Transaction 所有者姓名、返回客户名称、联系人名称、活动名称
	 */
	Transaction getById(String id);

	/**
	 * 通过交易id 
	 * @param id
	 * 需要左连接，万一该交易下没有活动
	 * @return 返回Transaction 所有者id、客户名称、联系人id、联系人名称、活动名称、活动id
	 */
	Map<String, Object> geyById2(String id);

	/**
	 * 保存单条交易
	 * @param transaction
	 * @return 1 保存成功
	 */
	int save(Transaction transaction);

	/**
	 * 更新单条交易
	 * @param transaction
	 * @return
	 */
	void update(Transaction transaction);

	/**
	 * 通过多个客户id，删除关联的交易
	 * @param customerId[]
	 */
	void delByCustomerId(String[] customerId);

	/**
	 * 通过多个交易id删除多条交易
	 * @param ids[]
	 */
	void del(String[] ids);

	/**
	 * 导出选中的交易
	 * @param ids[]
	 * @return
	 */
	List<Transaction> getByIds(String[] ids);

	/**
	 * 导出全部交易
	 * @return
	 */
	List<Transaction> getAll();

	/**
	 * 导入添加多条交易
	 * @param transList
	 * @return 成功添加的条数
	 */
	int save2(List<Transaction> transList);

	/**
	 * 通过联系人id 返回联系人关联的交易、customerId存放客户名称
	 * @param contactId
	 * @return
	 */
	List<Transaction> listByContactId(String contactId);

	/**
	 * 通过交易id删除一条
	 * @param id
	 * @return
	 */
	int delById(String id);

	/**
	 * 通过客户id，删除多条条交易
	 * @param customerId
	 * @return
	 */
	void delByCustomerId2(String customerId);

	/**
	 * 通过id更新点灯图标的阶段
	 * @param updateParam
	 * @return 更新者、更新时间、更新后的阶段
	 */
	int updateByStage(Transaction updateParam);

	/**
	 * 通过客户id返回交易
	 * @param customerId
	 * @return
	 */
	List<Transaction> getByCustomerId(String customerId);

}
