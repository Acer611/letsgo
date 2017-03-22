package com.umessage.letsgo.domain.vo.notice.request;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class NoticeParamRequest extends CommonRequest {
    /**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    private String teamId;
	/**
	 * 成员ID，如果设置这个，针对发送人和接受人的成员，进行查询
	 */
	@ApiModelProperty(value="成员ID")
	private Long memberId;
	/**
     * 多个团队ID
     */
    @ApiModelProperty(value="多个团队ID")
    private List<String> teamIds;
	/**
	 * 多个成员ID
	 */
	@ApiModelProperty(value="多个成员ID")
	private List<Long> memberIds;
	/**
     * 消息类型【1：集合；2：通知】
     */
    @ApiModelProperty(value="消息类型【1：集合；2：通知】")
    private Integer type;
	/**
	 * 是否需要确认【0：不需要；1：需要】
	 */
	@ApiModelProperty(value="是否需要确认【0：不需要；1：需要】")
	private Integer isActive;
	/**
	 * 确认状态【0：未确认；1：已确认】
	 */
	@ApiModelProperty(value="确认状态【0：未确认；1：已确认】")
	private Integer activeStatus;
	/**
	 * 上次最新的通知/集合ID
	 */
	@ApiModelProperty(value="上次最新的通知/集合ID")
	private Long lastId;
	/**
	 * 接受人ID，如果设置这个，只针对接受通知或者集合的成员，进行查询
	 */
	@ApiModelProperty(value="接受人ID")
	private List<Long> receiverIds;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public List<String> getTeamIds() {
		return teamIds;
	}

	public void setTeamIds(List<String> teamIds) {
		this.teamIds = teamIds;
	}

	public List<Long> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}

	public Integer getType() {
		return type;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

	public List<Long> getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(List<Long> receiverIds) {
		this.receiverIds = receiverIds;
	}
}
