package com.umessage.letsgo.domain.vo.notice.respone;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

public class NoticeRespone extends CommonResponse {
	/**
	 * 通知/集合对象
	 */
	@ApiModelProperty(value="通知/集合对象")
	private NoticeEntity noticeEntity;

	@ApiModelProperty(value = "是否微信")
	private String isWeChat;

	public NoticeEntity getNoticeEntity() {
		return noticeEntity;
	}

	public void setNoticeEntity(NoticeEntity noticeEntity) {
		this.noticeEntity = noticeEntity;
	}

	@Override
	public String toString() {
		return "NoticeRespone{" +
				"noticeEntity=" + noticeEntity +
				'}';
	}

	public static NoticeRespone createNotFoundResponse(){
		class NotFoundResponse extends NoticeRespone {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
			
			@Override
			public String getRetMsg() {
				return "未找到对应的通知/集合";
			}
		}
		
		return new NotFoundResponse();
	}

	public static NoticeRespone createUserNotLoginResponse(){
		class UserNotLoginResponse extends NoticeRespone {

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

	public String getIsWeChat() {
		return isWeChat;
	}

	public void setIsWeChat(String isWeChat) {
		this.isWeChat = isWeChat;
	}
}
