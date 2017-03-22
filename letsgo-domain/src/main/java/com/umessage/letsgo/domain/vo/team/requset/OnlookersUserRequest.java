package com.umessage.letsgo.domain.vo.team.requset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

public class OnlookersUserRequest extends CommonRequest {

	/**
	 * 用户id
	 */
	@ApiModelProperty(value="用户id")
	@JsonIgnore
	private  Long userId;

	/**
	 * 围观id
	 */
	@ApiModelProperty(value="围观id")
	private  Long onlookersId;

	/**
	 * 行程ID
	 */
	@ApiModelProperty(value="行程ID")
	private Long scheduleId;

	/**
	 * 围观用户id
	 */
	@ApiModelProperty(value="围观用户id")
	private  Long onlookerUserId;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value="手机号")
	private  String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getOnlookerUserId() {
		return onlookerUserId;
	}

	public void setOnlookerUserId(Long onlookerUserId) {
		this.onlookerUserId = onlookerUserId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Long getOnlookersId() {
		return onlookersId;
	}

	public void setOnlookersId(Long onlookersId) {
		this.onlookersId = onlookersId;
	}

}
