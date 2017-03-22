package com.umessage.letsgo.domain.vo.team.requset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class OnlookersDetailsRequest extends CommonRequest {
	/**
	 * ID
	 */
	@ApiModelProperty(value="ID")
	private Long id;

	/**
	 * 行程ID
	 */
	@ApiModelProperty(value="行程ID")
	private  Long scheduleId;

	/**
	 * 被回复用户id
	 */
	@ApiModelProperty(value="被回复用户id")
	private Long byReplyId;

	/**
	 * 查看1:是，0否
	 */
	@ApiModelProperty(value="查看1:是，0否")
	private  Integer isLook;

	public Integer getIsLook() {
		return isLook;
	}

	public void setIsLook(Integer isLook) {
		this.isLook = isLook;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getByReplyId() {
		return byReplyId;
	}

	public void setByReplyId(Long byReplyId) {
		this.byReplyId = byReplyId;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
}
