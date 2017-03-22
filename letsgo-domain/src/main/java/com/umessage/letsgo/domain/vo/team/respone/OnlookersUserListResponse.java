package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class OnlookersUserListResponse extends CommonResponse {
	/**
	 * 成员对象
	 */
	@ApiModelProperty(value="围观好友列表")
	private List<OnlookersUser> friendList;

	public List<OnlookersUser> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<OnlookersUser> friendList) {
		this.friendList = friendList;
	}

	public static OnlookersUserListResponse createNotFoundResponse(){
		class NotFoundResponse extends OnlookersUserListResponse {

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

	public static OnlookersUserListResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends OnlookersUserListResponse {

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
