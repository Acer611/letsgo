package com.umessage.letsgo.domain.vo.security.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.po.security.UserEntity;
import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class DeviceRequest extends CommonRequest {
	/**
	 * 设备唯一标识
	 */
	@ApiModelProperty(value="设备唯一标识")
	private String deviceUuid;
	/**
	 * 设备类别【IOS、Android】
	 */
	@ApiModelProperty(value="设备类别【IOS、Android】")
	private String deviceType;
	/**
	 * 设备型号
	 */
	@ApiModelProperty(value="设备型号")
	private String deviceModel;
	/**
	 * 设备版本号
	 */
	@ApiModelProperty(value="设备版本号")
	private String deviceVersion;
	/**
	 * 小米注册ID//或者华为手机的token
	 */
	@ApiModelProperty(value = "小米注册ID//或者华为手机的token")
	private String regid;

	/**
	 * 是否为华为手机：1是；0否
	 */
	@ApiModelProperty(value = "是否为华为手机：1是；0否")
	private String isHuaWei;

	public String getIsHuaWei() {
		return isHuaWei;
	}

	public void setIsHuaWei(String isHuaWei) {
		this.isHuaWei = isHuaWei;
	}

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getRegid() {
		return regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}

}
