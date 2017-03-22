package com.umessage.letsgo.domain.vo.team.requset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

public class InviteWatchRequest extends CommonRequest {

	/**
	 * 用户id
	 */
	@ApiModelProperty(value="用户id")
	@JsonIgnore
	private  Long userId;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value="手机号")
	private String mobile;

	/**
	 * 围观id
	 */
	@JsonIgnore
	@ApiModelProperty(value="围观id")
	private  Long onlookersId;

	/**
	 * 行程ID
	 */
	@ApiModelProperty(value="行程ID")
	private Long scheduleId;

	/**
	 * 姓名
	 */
	@ApiModelProperty(value="姓名")
	private String name;

	/**
	 * 性别：1男；2女
	 */
	@ApiModelProperty(value="性别：1男；2女")
	private Integer sex;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
