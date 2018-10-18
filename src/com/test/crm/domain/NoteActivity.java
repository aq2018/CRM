package com.test.crm.domain;
/**
 * 市场活动备注
 * 以后同一使用Remark
 * @author Administrator
 *
 */
public class NoteActivity {
	private String id;
	private String description;
	private String createBy;
	private String createTime;
	private String editBy;
	private String editTime;
	/**
	 * 修改标识:0未修改,1有修改
	 */
	private String editFlag;
	/**
	 * 活动标识
	 */
	private String p_actId;
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
	public String getP_actId() {
		return p_actId;
	}
	public void setP_actId(String p_actId) {
		this.p_actId = p_actId;
	}
	public String getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createBy == null) ? 0 : createBy.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((editBy == null) ? 0 : editBy.hashCode());
		result = prime * result + ((editFlag == null) ? 0 : editFlag.hashCode());
		result = prime * result + ((editTime == null) ? 0 : editTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((p_actId == null) ? 0 : p_actId.hashCode());
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
		NoteActivity other = (NoteActivity) obj;
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
		if (p_actId == null) {
			if (other.p_actId != null)
				return false;
		} else if (!p_actId.equals(other.p_actId))
			return false;
		return true;
	}
	
}
