package com.umessage.letsgo.domain.po.journey;

import java.io.Serializable;
import java.util.List;

public class StaticsConsumptionInfoList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8490630212412017110L;
	
	private Integer typeId;
	
	private List<StaticsConsumptionInfo> consumptionInfoList;

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public List<StaticsConsumptionInfo> getConsumptionInfoList() {
		return consumptionInfoList;
	}

	public void setConsumptionInfoList(
			List<StaticsConsumptionInfo> consumptionInfoList) {
		this.consumptionInfoList = consumptionInfoList;
	}
}
