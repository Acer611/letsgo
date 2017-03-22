package com.umessage.letsgo.domain.po.journey;

import java.io.Serializable;
import java.util.List;

public class ConsumptionInfoList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2588473273608673154L;

	private String date;
	
	private List<TallyBookEntity> consumption;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<TallyBookEntity> getConsumption() {
		return consumption;
	}

	public void setConsumption(List<TallyBookEntity> consumption) {
		this.consumption = consumption;
	}
	
}
