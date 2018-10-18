package com.test.crm.domain;

import java.io.Serializable;

/**
 * 客户备注
 * @author Administrator
 *
 */
public class CustomerRemark implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 备注内容
	 */
	private String noteContent;
	private String createBy;
	private String createTime;
	private String editBy;
	private String editTime;
	/**
	 * 修改标识:创建备注时默认为0,修改后为1
	 */
	private String editFlag;
	/**
	 * 客户标识
	 */
	private String customerId;
	
	public CustomerRemark() {}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createBy == null) ? 0 : createBy.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((editBy == null) ? 0 : editBy.hashCode());
		result = prime * result + ((editFlag == null) ? 0 : editFlag.hashCode());
		result = prime * result + ((editTime == null) ? 0 : editTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((noteContent == null) ? 0 : noteContent.hashCode());
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
		CustomerRemark other = (CustomerRemark) obj;
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
		if (noteContent == null) {
			if (other.noteContent != null)
				return false;
		} else if (!noteContent.equals(other.noteContent))
			return false;
		return true;
	}
}
