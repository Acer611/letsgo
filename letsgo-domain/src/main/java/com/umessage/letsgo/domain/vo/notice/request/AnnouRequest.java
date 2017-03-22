package com.umessage.letsgo.domain.vo.notice.request;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class AnnouRequest extends CommonRequest {
	/**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    private String teamId;
    /**
     * 多个团队ID
     */
    @ApiModelProperty(value="多个团队ID")
    private List<String> teamIds;
	/**
	 * 上次最新的公告ID
	 */
	@ApiModelProperty(value="上次最新的公告ID")
	private Long lastId;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public List<String> getTeamIds() {
		return teamIds;
	}

	public void setTeamIds(List<String> teamIds) {
		this.teamIds = teamIds;
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}
}
