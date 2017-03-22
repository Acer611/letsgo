package com.umessage.letsgo.domain.vo.team.requset;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class GroupRequest extends CommonRequest {
	/**
     * 小组ID
     */
    @ApiModelProperty(value="小组ID，该字段用于修改小组。调用修改小组接口时，需要把要修改的小组ID传入")
    private Long groupId;
	/**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID", required=true)
    private String teamId;
    /**
     * 小组名称
     */
    @ApiModelProperty(value="小组名称", required=true)
    private String name;
    /**
     * 组长ID
     */
    @ApiModelProperty(value="组长ID，就是组长的memberId", required=true)
    private Long leaderId;
    /**
     * 小组组员ID列表
     */
    @ApiModelProperty(value="小组组员ID列表，就是组员的memberId", required=true)
    private List<Long> memberIds;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public List<Long> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}

	@Override
	public String toString() {
		return "GroupRequest{" +
				"groupId=" + groupId +
				", teamId=" + teamId +
				", name='" + name + '\'' +
				", leaderId=" + leaderId +
				", memberIds=" + memberIds +
				'}';
	}
}
