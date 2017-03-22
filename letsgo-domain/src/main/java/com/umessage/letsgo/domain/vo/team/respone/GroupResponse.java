package com.umessage.letsgo.domain.vo.team.respone;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class GroupResponse extends CommonResponse {
	/**
     * 小组对象
     */
    @ApiModelProperty(value="小组对象")
    private GroupEntity groupEntity;

	public GroupEntity getGroupEntity() {
		return groupEntity;
	}

	public void setGroupEntity(GroupEntity groupEntity) {
		this.groupEntity = groupEntity;
	}

	public static GroupResponse createNotFoundResponse(){
		class NotFoundResponse extends GroupResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
			
			@Override
			public String getRetMsg() {
				return "未找到对应的小组";
			}
		}
		
		return new NotFoundResponse();
	}
	
}
