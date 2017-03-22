package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.EventRecordEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RedPointResponse extends CommonResponse {
	/**
	 * 1展示红点0不展示
	 */
	@ApiModelProperty(value="1展示红点0不展示")
	private Integer isRed;

	public Integer getIsRed() {
		return isRed;
	}

	public void setIsRed(Integer isRed) {
		this.isRed = isRed;
	}

	public static RedPointResponse createNotFoundResponse(){
		class NotFoundResponse extends RedPointResponse {

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

	public static RedPointResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends RedPointResponse {

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
