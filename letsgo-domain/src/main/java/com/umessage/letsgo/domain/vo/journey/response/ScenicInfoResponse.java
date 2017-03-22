package com.umessage.letsgo.domain.vo.journey.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.ScenicEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class ScenicInfoResponse extends CommonResponse{
	
	/**
     * 景点列表
     */
    @ApiModelProperty(value="景点列表")
    private List<ScenicEntity> scenicInfoList;

	public static ScenicInfoResponse createNotFoundResponse(String retMsg){
		class NotFoundResponse extends ScenicInfoResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
		}

		ScenicInfoResponse response = new NotFoundResponse();
		response.setRetMsg(retMsg);
		return response;
	}

	public List<ScenicEntity> getScenicInfoList() {
		return scenicInfoList;
	}



	public void setScenicInfoList(List<ScenicEntity> scenicInfoList) {
		this.scenicInfoList = scenicInfoList;
	}

}
