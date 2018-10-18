package com.test.crm.domain;

import java.util.List;
import java.util.Map;

/**
 * 分页展示数据实体类
 * @author Administrator
 *
 * @param <T>
 */
public class PaginationVo<T> {
	/**
	 * 分页总记录条数
	 */
	private Long total;
	/*
	 * 分页数据列表
	 */
	private List<T> dataList;
	private List<Map<String, Object>> dataList2;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public List<Map<String, Object>> getDataList2() {
		return dataList2;
	}
	public void setDataList2(List<Map<String, Object>> dataList2) {
		this.dataList2 = dataList2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataList == null) ? 0 : dataList.hashCode());
		result = prime * result + ((dataList2 == null) ? 0 : dataList2.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		PaginationVo other = (PaginationVo) obj;
		if (dataList == null) {
			if (other.dataList != null)
				return false;
		} else if (!dataList.equals(other.dataList))
			return false;
		if (dataList2 == null) {
			if (other.dataList2 != null)
				return false;
		} else if (!dataList2.equals(other.dataList2))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}
	
}
