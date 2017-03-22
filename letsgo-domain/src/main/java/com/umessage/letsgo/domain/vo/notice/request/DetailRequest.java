package com.umessage.letsgo.domain.vo.notice.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class DetailRequest extends CommonRequest {
	/**
	 * 通知ID
	 */
	@ApiModelProperty(value="通知ID", required=true)
	private Long noticeId;
	/**
	 * 团队ID
	 */
	@ApiModelProperty(value="团队ID", required=true)
	private String teamId;
	/**
	 * 回复内容
	 */
	@ApiModelProperty(value="回复内容")
	private String reply;
	/**
	 * 用户ID
	 */
	@JsonIgnore
	private Long userId;

	@ApiModelProperty(value="消息分类【1：集合；2：通知；3：公告；4：回复】")
	private Integer type;

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
