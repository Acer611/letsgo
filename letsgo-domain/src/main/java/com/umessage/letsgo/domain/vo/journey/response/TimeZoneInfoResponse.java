package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

public class TimeZoneInfoResponse extends CommonResponse {
	/**
	 * 当前时区实体
	 */
	@ApiModelProperty(value="当前时区实体")
	private TimeZoneInfo currentTimeZone;
    /**
     * 时区列表
     */
    @ApiModelProperty(value="时区列表")
    private List<TimeZoneInfo> timeZoneInfoList;

	public TimeZoneInfo getCurrentTimeZone() {
		return currentTimeZone;
	}

	public void setCurrentTimeZone(TimeZoneInfo currentTimeZone) {
		this.currentTimeZone = currentTimeZone;
	}

	public List<TimeZoneInfo> getTimeZoneInfoList() {
		return timeZoneInfoList;
	}

	public void setTimeZoneInfoList(List<TimeZoneInfo> timeZoneInfoList) {
		this.timeZoneInfoList = timeZoneInfoList;
	}

	public static TimeZoneInfoResponse createNotFoundResponse(String retMsg){
		class NotFoundResponse extends TimeZoneInfoResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
		}

		TimeZoneInfoResponse response = new NotFoundResponse();
		response.setRetMsg(retMsg);
		return response;
	}
}
