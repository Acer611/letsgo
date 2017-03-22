package com.umessage.letsgo.domain.vo.journey.response;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class CurrentTimeZoneResponse extends CommonResponse{
	/**
	 * 当前时区
	 */
	@ApiModelProperty(value="当前时区实体")
	private TimeZoneInfo currentTimeZone;

	public TimeZoneInfo getCurrentTimeZone() {
		return currentTimeZone;
	}

	public void setCurrentTimeZone(TimeZoneInfo currentTimeZone) {
		this.currentTimeZone = currentTimeZone;
	}
}
