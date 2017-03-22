package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class TrafficInfoResponse extends CommonResponse {
	/**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 行程ID
     */
    @ApiModelProperty(value="行程ID")
    private Long jourId;
    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
    private String date;
    /**
     * 交通
     */
    @ApiModelProperty(value="交通")
    private String traffic;

	public static TrafficInfoResponse createNotFoundResponse(String retMsg){
		class NotFoundResponse extends TrafficInfoResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
		}

		TrafficInfoResponse response = new NotFoundResponse();
		response.setRetMsg(retMsg);
		return response;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getJourId() {
		return jourId;
	}
	public void setJourId(Long jourId) {
		this.jourId = jourId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	@Override
	public String toString() {
		return "TrafficInfoResponse {date=" + date + ", id=" + id + ", jourId="
				+ jourId + ", traffic=" + traffic + "}";
	}
}
