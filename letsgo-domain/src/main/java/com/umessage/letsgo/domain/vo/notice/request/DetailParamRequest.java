package com.umessage.letsgo.domain.vo.notice.request;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class DetailParamRequest extends CommonRequest {
	/**
     * 通知ID
     */
    @ApiModelProperty(value="通知ID")
	private Long noticeId;
    /**
     * 成员ID
     */
    @ApiModelProperty(value="成员ID")
	private Long memberId;
	/**
	 * 是否需要确认【0：不需要；1：需要】
	 */
	@ApiModelProperty(value="是否需要确认【0：不需要；1：需要】")
	private Integer isActive;
    /**
     * 排序字段：确认时间，降序desc
     */
    @ApiModelProperty(value="排序字段：确认时间，降序desc,升序asc")
    private String sort_activeTime;

	@ApiModelProperty(value="消息分类【1：集合；2：通知；3：公告；4：回复】")
	private Integer type;
	@ApiModelProperty(value="是否读取/确认【如果是需要确认：0：未确认；1：已确认；如果是不需要确认：0：未读取；1：已读取】")
	private Integer activeStatus;
	@ApiModelProperty(value="团队ID")
	private String teamId;
	@ApiModelProperty(value="用户ID")
	private Long userId;

	@ApiModelProperty(value="团队状态")
	private Integer teamStatus;

	public DetailParamRequest() {
	}

	public DetailParamRequest(String teamId, Long userId, Integer type) {
		this.type = type;
		this.userId = userId;
		this.teamId = teamId;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getSort_activeTime() {
		return sort_activeTime;
	}

	public void setSort_activeTime(String sort_activeTime) {
		this.sort_activeTime = sort_activeTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getTeamStatus() {
		return teamStatus;
	}

	public void setTeamStatus(Integer teamStatus) {
		this.teamStatus = teamStatus;
	}

}
