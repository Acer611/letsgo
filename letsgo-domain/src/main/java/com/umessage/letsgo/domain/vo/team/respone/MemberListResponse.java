package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class MemberListResponse extends CommonResponse {
	/**
	 * 成员对象集合
	 */
	@ApiModelProperty(value="成员对象集合")
	@Catalog
	private List<MemberEntity> memberList;

	public List<MemberEntity> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<MemberEntity> memberList) {
		this.memberList = memberList;
	}

	public static MemberListResponse createNotFoundResponse(){
		class NotFoundResponse extends MemberListResponse {

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

	public static MemberListResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends MemberListResponse {

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

	public static MemberListResponse createNotFoundResponse(String retMsg){
		class NotFoundResponse extends MemberListResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
		}

		MemberListResponse response = new NotFoundResponse();
		response.setRetMsg(retMsg);
		return response;
	}

}
