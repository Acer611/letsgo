package com.umessage.letsgo.domain.vo.activity.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.activity.LocationEntity;
import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

public class LocationRespone extends CommonResponse {

	@ApiModelProperty("成员位置")
	@JsonIgnore
	@Catalog
	LocationEntity locationEntity;

	@ApiModelProperty("下次上传的间隔时间")
	Integer interval;
	@ApiModelProperty("下次是否需要上传，0：不需要上传，1：需要上传")
	Integer isUpload;

	public LocationEntity getLocationEntity() {
		return locationEntity;
	}

	public void setLocationEntity(LocationEntity locationEntity) {
		this.locationEntity = locationEntity;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}

	public static LocationRespone createUserNotLoginResponse(){
		class UserNotLoginResponse extends LocationRespone {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.USER_NOT_LOGIN;
			}

			@Override
			public String getRetMsg() {
				return "用户未登录或登录信息过期";
			}
		}

		return new UserNotLoginResponse();
	}

	public static LocationRespone createInvalidParameterResponse(String retMsg){
		class InvalidParameterResponse extends LocationRespone {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.INVALID_PARAMETER;
			}
		}

		LocationRespone response = new InvalidParameterResponse();
		response.setRetMsg(retMsg);
		return response;
	}
}
