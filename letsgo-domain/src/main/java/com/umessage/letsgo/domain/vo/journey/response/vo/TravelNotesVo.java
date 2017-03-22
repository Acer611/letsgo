package com.umessage.letsgo.domain.vo.journey.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TravelNotesVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2919254877854636925L;
	/**
     * 游记时间
     */
    @ApiModelProperty(value="游记时间")
	private String travelDate;
    
    /**
     * 游记完成状态 0为已完成1为未完成
     */
    @ApiModelProperty(value="游记完成状态 0为已完成1为未完成")
	private String travelStatus;
    
    /**
     * 每日行程ID
     */
    @ApiModelProperty(value="每日行程ID")
	private Long scheduleDetailId;
	
	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	public String getTravelStatus() {
		return travelStatus;
	}
	public void setTravelStatus(String travelStatus) {
		this.travelStatus = travelStatus;
	}
	public Long getScheduleDetailId() {
		return scheduleDetailId;
	}
	public void setScheduleDetailId(Long scheduleDetailId) {
		this.scheduleDetailId = scheduleDetailId;
	}
	
}
