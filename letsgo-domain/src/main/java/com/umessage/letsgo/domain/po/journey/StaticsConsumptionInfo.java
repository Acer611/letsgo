package com.umessage.letsgo.domain.po.journey;

import java.io.Serializable;

public class StaticsConsumptionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5211202095414860588L;

	private Double totalMoney;
	
	private Integer typeId;

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
}
