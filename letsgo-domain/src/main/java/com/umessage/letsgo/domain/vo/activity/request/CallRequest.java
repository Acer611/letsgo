package com.umessage.letsgo.domain.vo.activity.request;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class CallRequest extends CommonRequest {
	/**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
	/**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    private String teamId;
    /**
     * 进行状态【0：进行中；1：已完成】
     */
    @ApiModelProperty(value="进行状态【0：进行中；1：已完成】")
    private Integer status;

	/**
	 * 用户ID，游客用户
	 */
	@ApiModelProperty(value="用户ID,游客用户")
	private Long userId;
	/**
	 * 用户ID，点名人
	 */
	@ApiModelProperty(value="用户ID,点名人")
	private Long leadUserId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLeadUserId() {
		return leadUserId;
	}

	public void setLeadUserId(Long leadUserId) {
		this.leadUserId = leadUserId;
	}

	@Override
	public String toString() {
		return "CallRequest{" +
				"id=" + id +
				", teamId=" + teamId +
				", status=" + status +
				", userId=" + userId +
				", leadUserId=" + leadUserId +
				'}';
	}
}
