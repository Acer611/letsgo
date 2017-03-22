package com.umessage.letsgo.domain.vo.notice.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class AnnouncementRequest extends CommonRequest {
	/**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID", required=true)
    private String teamId;
    /**
     * 标题
     */
    @ApiModelProperty(value="标题", required=true)
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty(value="内容", required=true)
    private String content;
    /**
     * 用户ID
     */
	@JsonIgnore
    private Long userId;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
