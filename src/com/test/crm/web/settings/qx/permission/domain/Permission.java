package com.test.crm.web.settings.qx.permission.domain;

/**
 * 许可
 * @author Administrator
 *
 */
public class Permission {
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 模块url
	 */
	private String modalURL;
	/**
	 * 操作url
	 */
	private String operateURL;
	/**
	 * 排序号
	 */
	private String sortNo;
	/**
	 * 父许可标识
	 */
	private String pid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModalURL() {
		return modalURL;
	}
	public void setModalURL(String modalURL) {
		this.modalURL = modalURL;
	}
	public String getOperateURL() {
		return operateURL;
	}
	public void setOperateURL(String operateURL) {
		this.operateURL = operateURL;
	}
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modalURL == null) ? 0 : modalURL.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((operateURL == null) ? 0 : operateURL.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result + ((sortNo == null) ? 0 : sortNo.hashCode());
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
		Permission other = (Permission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modalURL == null) {
			if (other.modalURL != null)
				return false;
		} else if (!modalURL.equals(other.modalURL))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (operateURL == null) {
			if (other.operateURL != null)
				return false;
		} else if (!operateURL.equals(other.operateURL))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		if (sortNo == null) {
			if (other.sortNo != null)
				return false;
		} else if (!sortNo.equals(other.sortNo))
			return false;
		return true;
	}
}
