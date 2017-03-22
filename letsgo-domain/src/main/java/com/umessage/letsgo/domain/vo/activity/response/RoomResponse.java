package com.umessage.letsgo.domain.vo.activity.response;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.activity.RoomEntity;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RoomResponse extends CommonResponse {
	/**
	 * 房间列表
	 */
	@ApiModelProperty(value="房间列表")
	@Catalog
	private List<RoomEntity> roomList;
	/**
	 * 小组列表
	 */
	@ApiModelProperty(value="小组列表")
	@Catalog
	private List<GroupEntity> groupList;
	/**
	 * 个人列表
	 */
	@ApiModelProperty(value="个人列表")
	@Catalog
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
	/**
	 * 当前用户所在房间的房间号
	 */
	@ApiModelProperty(value = "当前用户所在房间的房间号")
	private String currentUserRoomNum;

	public List<RoomEntity> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<RoomEntity> roomList) {
		this.roomList = roomList;
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

	public String getCurrentUserRoomNum() {
		return currentUserRoomNum;
	}

	public void setCurrentUserRoomNum(String currentUserRoomNum) {
		this.currentUserRoomNum = currentUserRoomNum;
	}

	public static RoomResponse createNotFoundResponse(){
		class NotFoundResponse extends RoomResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}

			@Override
			public String getRetMsg() {
				return "未找到团队的分房方案";
			}
		}

		return new NotFoundResponse();
	}

	public static RoomResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends RoomResponse {

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

	public static RoomResponse createUserNotAccessResponse(){
		class UserNotAccessResponse extends RoomResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.ACCESS_DENIED;
			}

			@Override
			public String getRetMsg() {
				return "用户不能触发分房事件，没有权限操作！";
			}
		}

		return new UserNotAccessResponse();
	}
}
