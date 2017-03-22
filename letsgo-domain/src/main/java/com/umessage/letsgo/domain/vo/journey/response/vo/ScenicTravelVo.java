package com.umessage.letsgo.domain.vo.journey.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ScenicTravelVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1246977788072099958L;
	 /**
     * 景点id
     */
    @ApiModelProperty(value="景点id")
	private long scenicId; 
    
    /**
     * 景点名称 
     */
    @ApiModelProperty(value="景点名称")
	private String scenicName;
	
	public long getScenicId() {
		return scenicId;
	}
	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}
	public String getScenicName() {
		return scenicName;
	}
	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}
	
	
	
}
