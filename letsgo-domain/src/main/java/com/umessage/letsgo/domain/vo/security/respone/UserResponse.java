package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class UserResponse extends CommonResponse {
	
	/**
	 * 用户对象
	 */
	@ApiModelProperty(value="用户对象")
	@Catalog
	private UserEntity userEntity;

	@ApiModelProperty(value="邀请码下载链接")
	private String inviteUrl;

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getInviteUrl() {
		return inviteUrl;
	}

	public void setInviteUrl(String inviteUrl) {
		this.inviteUrl = inviteUrl;
	}

	public static UserResponse createNotFoundResponse(){
		class NotFoundResponse extends UserResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
			
			@Override
			public String getRetMsg() {
				return "未找到对应的用户";
			}
		}
		
		return new NotFoundResponse();
	}
	
	public static UserResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends UserResponse {

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
