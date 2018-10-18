package com.test.crm.domain;

/**
 * 用户
 * @author Administrator
 *
 */
public class User {
	
	private String id;
	/**
	 * 用户账号
	 */
	private String account;
	/**
	 * 用户姓名
	 */
	private String username;
	private String password;
	private String email;
	/**
	 * 失效时间
	 */
	private String invalid_time;
	/**
	 * 锁定状态
	 */
	private String lockState;
	/**
	 * 所属部门
	 */
	private String deptno;
	/**
	 * 允许ip
	 */
	private String permit_ip;
	/**
	 * 创建者姓名
	 */
	private String createBy;
	private String createTime;
	private String editBy;
	private String editTime;
	
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInvalid_time() {
		return invalid_time;
	}
	public void setInvalid_time(String invalid_time) {
		this.invalid_time = invalid_time;
	}
	public String getDeptno() {
		return deptno;
	}
	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}
	public String getPermit_ip() {
		return permit_ip;
	}
	public void setPermit_ip(String permit_ip) {
		this.permit_ip = permit_ip;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLockState() {
		return lockState;
	}
	public void setLockState(String lockState) {
		this.lockState = lockState;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getEditBy() {
		return editBy;
	}
	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((createBy == null) ? 0 : createBy.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((deptno == null) ? 0 : deptno.hashCode());
		result = prime * result + ((editBy == null) ? 0 : editBy.hashCode());
		result = prime * result + ((editTime == null) ? 0 : editTime.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invalid_time == null) ? 0 : invalid_time.hashCode());
		result = prime * result + ((lockState == null) ? 0 : lockState.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((permit_ip == null) ? 0 : permit_ip.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
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
		if (deptno == null) {
			if (other.deptno != null)
				return false;
		} else if (!deptno.equals(other.deptno))
			return false;
		if (editBy == null) {
			if (other.editBy != null)
				return false;
		} else if (!editBy.equals(other.editBy))
			return false;
		if (editTime == null) {
			if (other.editTime != null)
				return false;
		} else if (!editTime.equals(other.editTime))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invalid_time == null) {
			if (other.invalid_time != null)
				return false;
		} else if (!invalid_time.equals(other.invalid_time))
			return false;
		if (lockState == null) {
			if (other.lockState != null)
				return false;
		} else if (!lockState.equals(other.lockState))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (permit_ip == null) {
			if (other.permit_ip != null)
				return false;
		} else if (!permit_ip.equals(other.permit_ip))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
