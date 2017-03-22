package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.annotation.dataitem.DataItem;
import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

public class TeamRespone extends CommonResponse {
	/**
	 * 团队对象
	 */
	@ApiModelProperty(value="团队对象")
	@Catalog
	private TeamEntity teamEntity;
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

	public TeamEntity getTeamEntity() {
		return teamEntity;
	}

	public void setTeamEntity(TeamEntity teamEntity) {
		this.teamEntity = teamEntity;
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

	public static TeamRespone createNotFoundResponse(){
		class NotFoundResponse extends TeamRespone {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
			
			@Override
			public String getRetMsg() {
				return "未找到对应的团队";
			}
		}
		
		return new NotFoundResponse();
	}

	public static TeamRespone createUserNotLoginResponse(){
		class UserNotLoginResponse extends TeamRespone {

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

	public static TeamRespone createNotAnyTravelResponse(){
		class NotAnyTravelResponse extends TeamRespone {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_TRAVEL;
			}

			@Override
			public String getRetMsg() {
				return "没有出行中和即将出行的团队";
			}
		}

		return new NotAnyTravelResponse();
	}
}
