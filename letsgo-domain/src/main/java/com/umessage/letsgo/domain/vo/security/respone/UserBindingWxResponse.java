package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

public class UserBindingWxResponse extends CommonResponse {
	
	public static UserBindingWxResponse createNotFoundResponse(){
		class NotFoundResponse extends UserBindingWxResponse {

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
	
	public static UserBindingWxResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends UserBindingWxResponse {

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

	public static UserBindingWxResponse createBadInvitationCodeResponse() {
		class BadInvitationCodeResponse extends UserBindingWxResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.BAD_INVITATION_CODE;
			}

			@Override
			public String getRetMsg() {
				return "邀请码不正确或不存在";
			}
		}

		return new BadInvitationCodeResponse();
	}

	public static UserBindingWxResponse createExistPhoneResponse() {
		class ExistPhoneResponse extends UserBindingWxResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.EXIST_PHONENUMBER;
			}

			@Override
			public String getRetMsg() {
				return "手机号码已经被使用";
			}
		}

		return new ExistPhoneResponse();
	}
	
}
