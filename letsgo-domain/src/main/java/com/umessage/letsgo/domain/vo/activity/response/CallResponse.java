package com.umessage.letsgo.domain.vo.activity.response;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.activity.CallEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class CallResponse extends CommonResponse {
	/**
     * 点名对象
     */
    @ApiModelProperty(value="点名对象")
	@Catalog
	private CallEntity callEntity;
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

	public CallEntity getCallEntity() {
		return callEntity;
	}

	public void setCallEntity(CallEntity callEntity) {
		this.callEntity = callEntity;
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

	public static CallResponse createNotFoundResponse(String retMsg){
		class NotFoundResponse extends CallResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
		}

		CallResponse response = new NotFoundResponse();
		response.setRetMsg(retMsg);
		return response;
	}
	public static CallResponse createNotFoundResponse(){
		class NotFoundResponse extends CallResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
			
			@Override
			public String getRetMsg() {
				return "未找到对应的点名";
			}
		}
		
		return new NotFoundResponse();
	}

	public static CallResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends CallResponse {

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

	public static CallResponse createUserNotAccessResponse(){
		class UserNotAccessResponse extends CallResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.ACCESS_DENIED;
			}

			@Override
			public String getRetMsg() {
				return "用户不能触发点名事件，没有权限操作！";
			}
		}

		return new UserNotAccessResponse();
	}

	public static CallResponse createInvalidParameterResponse(String retMsg){
		class InvalidParameterResponse extends CallResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.INVALID_PARAMETER;
			}
		}

		CallResponse response = new InvalidParameterResponse();
		response.setRetMsg(retMsg);
		return response;
	}
}
