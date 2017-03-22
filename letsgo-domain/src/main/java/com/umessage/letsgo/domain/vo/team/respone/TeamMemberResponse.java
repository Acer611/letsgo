package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class TeamMemberResponse extends CommonResponse {
	/**
	 * 团队ID
	 */
	@ApiModelProperty(value = "团队ID")
	private String teamId;
	/**
	 * 游客人数
	 */
	@ApiModelProperty(value="游客人数")
	private Integer touristCount;
	/**
     * 领队&导游列表
     */
    @ApiModelProperty(value="领队&导游列表")
	private List<MemberEntity> administratorList;
    /**
     * 小组列表
     */
    @ApiModelProperty(value="小组列表")
    private List<GroupEntity> groupList;
    /**
     * 个人列表
     */
    @ApiModelProperty(value="个人列表")
    private List<MemberEntity> personalList;
	/**
	 * 当前团队出行状态标识
	 */
	@ApiModelProperty(value = "当前团队出行状态标识【值为1，代表出行中；值为2，代表即将出行；值为3，代表已出行】")
	private int teamStatus;
	/**
	 * 当前用户在当前团队的身份标识
	 */
	@ApiModelProperty(value = "当前用户在当前团队的身份标识【值为1，代表领队；值为2，代表导游；值3，代表游客】")
	private int roleStatus;
	/**
	 * 当前用户在当前团队的管理权限标识
	 */
	@ApiModelProperty(value = "当前用户在当前团队的管理权限标识【值为1，代表有管理权；值为0，代表无管理权】")
	private int adminStatus;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Integer getTouristCount() {
		return touristCount;
	}

	public void setTouristCount(Integer touristCount) {
		this.touristCount = touristCount;
	}

	public List<MemberEntity> getAdministratorList() {
		return administratorList;
	}

	public void setAdministratorList(List<MemberEntity> administratorList) {
		this.administratorList = administratorList;
	}

	public List<GroupEntity> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<GroupEntity> groupList) {
		this.groupList = groupList;
	}

	public List<MemberEntity> getPersonalList() {
		return personalList;
	}

	public void setPersonalList(List<MemberEntity> personalList) {
		this.personalList = personalList;
	}

	public int getTeamStatus() {
		return teamStatus;
	}

	public void setTeamStatus(int teamStatus) {
		this.teamStatus = teamStatus;
	}

	public int getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(int roleStatus) {
		this.roleStatus = roleStatus;
	}

	public int getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(int adminStatus) {
		this.adminStatus = adminStatus;
	}

	public static TeamMemberResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends TeamMemberResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.USER_NOT_LOGIN;
			}

			@Override
			public String getRetMsg() {
				return "用户未登录或登录信息过期";
			}
		}

		return new UserNotLoginResponse();
	}
}
