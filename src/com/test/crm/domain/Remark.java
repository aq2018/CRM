package com.test.crm.domain;

import java.io.Serializable;

/**
 * 所有的备注实体类
 * @author Administrator
 *
 */
public class Remark implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	id;
	private String	description;
	private String	createBy;
	private String	createTime;
	private String	editBy;
	private String	editTime;
	/**
	 * 修改标识:0未修改,1有修改
	 */
	private String	editFlag;
	/**
	 * 线索标识
	 */
	private String	p_clueId;
	/**
	 * 市场活动标识
	 */
	private String	activityId;
	/**
	 * 联系人标识
	 */
	private String	contactId;
	/**
	 * 客户标识
	 */
	private String	customerId;
	/**
	 * 交易标识
	 */
	private String	transId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getEditBy() {
		return editBy;
	}
	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public String getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}
	public String getP_clueId() {
		return p_clueId;
	}
	public void setP_clueId(String p_clueId) {
		this.p_clueId = p_clueId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activityId == null) ? 0 : activityId.hashCode());
		result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
		result = prime * result + ((createBy == null) ? 0 : createBy.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((editBy == null) ? 0 : editBy.hashCode());
		result = prime * result + ((editFlag == null) ? 0 : editFlag.hashCode());
		result = prime * result + ((editTime == null) ? 0 : editTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((p_clueId == null) ? 0 : p_clueId.hashCode());
		result = prime * result + ((transId == null) ? 0 : transId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Remark other = (Remark) obj;
		if (activityId == null) {
			if (other.activityId != null)
				return false;
		} else if (!activityId.equals(other.activityId))
			return false;
		if (contactId == null) {
			if (other.contactId != null)
				return false;
		} else if (!contactId.equals(other.contactId))
			return false;
		if (createBy == null) {
			if (other.createBy != null)
				return false;
		} else if (!createBy.equals(other.createBy))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (editBy == null) {
			if (other.editBy != null)
				return false;
		} else if (!editBy.equals(other.editBy))
			return false;
		if (editFlag == null) {
			if (other.editFlag != null)
				return false;
		} else if (!editFlag.equals(other.editFlag))
			return false;
		if (editTime == null) {
			if (other.editTime != null)
				return false;
		} else if (!editTime.equals(other.editTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (p_clueId == null) {
			if (other.p_clueId != null)
				return false;
		} else if (!p_clueId.equals(other.p_clueId))
			return false;
		if (transId == null) {
			if (other.transId != null)
				return false;
		} else if (!transId.equals(other.transId))
			return false;
		return true;
	}
}
