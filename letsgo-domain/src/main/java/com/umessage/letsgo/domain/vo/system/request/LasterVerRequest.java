package com.umessage.letsgo.domain.vo.system.request;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

import java.io.Serializable;

public class LasterVerRequest extends CommonRequest {
	/**
     * 设备类型
     */
    @ApiModelProperty(value="设备类型", allowableValues="ios, android")
	private String deviceType;
	
    /**
     * 模块
     */
    @ApiModelProperty(value="模块", allowableValues="tour_escort, traveller")
	private String module;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public String toString() {
		return "{"
				+ "                        \"deviceType\":\"" + deviceType + "\""
				+ ",                         \"module\":\"" + module + "\""
				+ "}";
	}
}
