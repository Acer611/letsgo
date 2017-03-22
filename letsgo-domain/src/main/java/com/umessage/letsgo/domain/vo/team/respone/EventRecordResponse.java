package com.umessage.letsgo.domain.vo.team.respone;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.EventRecordEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class EventRecordResponse extends CommonResponse {
	/**
	 * 成员对象
	 */
	@ApiModelProperty(value="成员对象")
	@Catalog
	private EventRecordEntity eventRecordEntity;
	/**
	 * 成员对象集合
	 */
	@ApiModelProperty(value = "成员对象集合")
	private List<EventRecordEntity> eventRecordList;

	/**
	 * 团队的出行状态   1为出行中    2 为即将出行   3为已出行
	 */
	@ApiModelProperty(value = "团队的出行状态   1为出行中    2 为即将出行   3为已出行")
	private Integer teamStatus;

	public Integer getTeamStatus() {
		return teamStatus;
	}

	public void setTeamStatus(Integer teamStatus) {
		this.teamStatus = teamStatus;
	}

	public EventRecordEntity getEventRecordEntity() {
		return eventRecordEntity;
	}

	public void setEventRecordEntity(EventRecordEntity eventRecordEntity) {
		this.eventRecordEntity = eventRecordEntity;
	}
	public List<EventRecordEntity> getEventRecordList() {
		return eventRecordList;
	}

	public void setEventRecordList(List<EventRecordEntity> eventRecordList) {
		this.eventRecordList = eventRecordList;
	}

	public static EventRecordResponse createNotFoundResponse(){
		class NotFoundResponse extends EventRecordResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}

			@Override
			public String getRetMsg() {
				return "未找到对应的成员";
			}
		}

		return new NotFoundResponse();
	}

	public static EventRecordResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends EventRecordResponse {

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
