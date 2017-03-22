package com.umessage.letsgo.domain.vo.activity.request;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

import java.io.Serializable;
import java.util.Date;

public class CallDetailRequest extends CommonRequest implements Serializable {
	/**
     * 点名ID
     */
    @ApiModelProperty(value="点名ID")
    private Long callId;
    /**
     * 签到状态【0：未签到；1：已签到】
     */
    @ApiModelProperty(value="签到状态【0：未签到；1：已签到】")
    private Integer status;
    /**
     * 领队坐标纬度
     */
    @ApiModelProperty(value="领队坐标纬度")
    private Double latitude;
    /**
     * 领队坐标经度
     */
    @ApiModelProperty(value="领队坐标经度")
    private Double longitude;
	/**
	 * 排序字段：更新时间，desc降序；asc升序
	 */
	@ApiModelProperty(value="排序字段：更新时间，desc降序；asc升序")
	private String sort_updateTime;

	public Long getCallId() {
		return callId;
	}

	public void setCallId(Long callId) {
		this.callId = callId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getSort_updateTime() {
		return sort_updateTime;
	}

	public void setSort_updateTime(String sort_updateTime) {
		this.sort_updateTime = sort_updateTime;
	}
}
