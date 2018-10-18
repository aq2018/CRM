package com.test.crm.domain;

/**
 * 线索与市场活动关系
 * @author Administrator
 *
 */
public class ClueActRelation {
	private String id;
	/**
	 * 市场活动标识
	 */
	private String p_marketId;
	/**
	 * 线索标识
	 */
	private String p_clueId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getP_marketId() {
		return p_marketId;
	}
	public void setP_marketId(String p_marketId) {
		this.p_marketId = p_marketId;
	}
	public String getP_clueId() {
		return p_clueId;
	}
	public void setP_clueId(String p_clueId) {
		this.p_clueId = p_clueId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((p_clueId == null) ? 0 : p_clueId.hashCode());
		result = prime * result + ((p_marketId == null) ? 0 : p_marketId.hashCode());
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
		ClueActRelation other = (ClueActRelation) obj;
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
		if (p_marketId == null) {
			if (other.p_marketId != null)
				return false;
		} else if (!p_marketId.equals(other.p_marketId))
			return false;
		return true;
	}
	
}
