package com.umessage.letsgo.domain.vo.team.requset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class MemberAddRequest extends CommonRequest {
	/**
     * 成员ID
     */
    @ApiModelProperty(value="成员ID")
    private Long id;
    /**
     * 用户
     */
    @ApiModelProperty(value="用户")
	@JsonIgnore
    private UserEntity user;
	/**
     *腾迅群组ID
     */
    @ApiModelProperty(value="腾迅群组ID")
    private String teamId;
	/**
	 * 团队ID
	 */
	@ApiModelProperty(value="团队ID")
	@JsonIgnore
	private Long tId;
	/**
	 * 加入类型
	 */
	@ApiModelProperty(value="加入类型【0选名字加入；1自己填写名字加入】")
	private Integer type;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value="姓名")
	private String realName;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Long gettId() {
		return tId;
	}

	public void settId(Long tId) {
		this.tId = tId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
