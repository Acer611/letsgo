package com.umessage.letsgo.domain.vo.notice.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.notice.AnnouncementEntity;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

public class InformationResponse extends CommonResponse {
	/**
	 * 消息总数量
	 */
	@ApiModelProperty(value="消息总数量")
	private Integer totalCount = 0;
	/**
	 * 通知数量
	 */
	@ApiModelProperty(value="通知数量")
	private Integer noticeCount = 0;
	/**
	 * 集合数量
	 */
	@ApiModelProperty(value="集合数量")
	private Integer gatherCount = 0;
	/**
	 * 公告数量
	 */
	@ApiModelProperty(value="公告数量")
	private Integer annouCount = 0;
	/**
	 * 最新通知
	 */
	@ApiModelProperty(value="最新通知")
	private NoticeEntity notice;
	
	/**
	 * 最新集合
	 */
	@ApiModelProperty(value="最新集合")
	private NoticeEntity gather;
	
	/**
	 * 最新公告
	 */
	@ApiModelProperty(value="最新公告")
	private AnnouncementEntity announcement;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(Integer noticeCount) {
		this.noticeCount = noticeCount;
	}

	public Integer getGatherCount() {
		return gatherCount;
	}

	public void setGatherCount(Integer gatherCount) {
		this.gatherCount = gatherCount;
	}

	public Integer getAnnouCount() {
		return annouCount;
	}

	public void setAnnouCount(Integer annouCount) {
		this.annouCount = annouCount;
	}

	public NoticeEntity getNotice() {
		return notice;
	}

	public void setNotice(NoticeEntity notice) {
		this.notice = notice;
	}

	public NoticeEntity getGather() {
		return gather;
	}

	public void setGather(NoticeEntity gather) {
		this.gather = gather;
	}

	public AnnouncementEntity getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(AnnouncementEntity announcement) {
		this.announcement = announcement;
	}

	public static InformationResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends InformationResponse {

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
