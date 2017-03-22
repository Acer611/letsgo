package com.umessage.letsgo.domain.vo.team.requset;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class GuideMemberRequest extends CommonRequest {
	/**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID", required=true)
    private String teamId;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名", required=true)
    private String realName;
    /**
     * 性别【1：男性；2：女性】
     */
    @ApiModelProperty(value="性别【1：男性；2：女性】", required=true)
    private Integer sex;
    /**
     * 手机号码
     */
    @ApiModelProperty(value="手机号码", required=true)
    private String phone;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
