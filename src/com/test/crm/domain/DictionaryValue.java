package com.test.crm.domain;
/**
 * 字典值
 * @author Administrator
 *
 */
public class DictionaryValue {
	private String id;
	/**
	 * 字典值
	 */
	private String value;
	/**
	 * 文本
	 */
	private String content;
	/**
	 * 排序号
	 */
	private int sortNo;
	/**
	 * 字典类型标识,前期使用了该属性,后期将器删除,不建议使用
	 */
	@Deprecated
	private String pid_dictType;
	/**
	 * 字典类型编码
	 */
	private String type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public String getP_dictionary_type() {
		return pid_dictType;
	}
	public void setP_dictionary_type(String pid_dictType) {
		this.pid_dictType = pid_dictType;
	}
	public String getPid_dictType() {
		return pid_dictType;
	}
	public void setPid_dictType(String pid_dictType) {
		this.pid_dictType = pid_dictType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pid_dictType == null) ? 0 : pid_dictType.hashCode());
		result = prime * result + sortNo;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		DictionaryValue other = (DictionaryValue) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pid_dictType == null) {
			if (other.pid_dictType != null)
				return false;
		} else if (!pid_dictType.equals(other.pid_dictType))
			return false;
		if (sortNo != other.sortNo)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
