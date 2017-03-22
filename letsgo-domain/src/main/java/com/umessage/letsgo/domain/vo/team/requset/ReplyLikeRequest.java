package com.umessage.letsgo.domain.vo.team.requset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

public class ReplyLikeRequest extends CommonRequest {
	/**
	 * 类型
	 * 1为点赞，2为回复
	 */
	@ApiModelProperty(value="类型：1为点赞，2为回复，3为评论")
	private Integer type;

	/**
	 * 点赞人/回复人id
	 */
	@ApiModelProperty(value="点赞人/回复人 腾讯组id")
	private  String userId;

	/**
	 * 回复图片地址	多个图片地址，以分号隔开
	 */
	@ApiModelProperty(value="回复图片地址，多个图片地址，以分号隔开")
	private String imgUrl;

	/**
	 * 围观id
	 */
	@ApiModelProperty(value="围观id")
	private  Long onlookersId;

	/**
	 * 是否点赞	1为点赞 0未点赞
	 */
	@ApiModelProperty(value="是否点赞：1为点赞 0未点赞")
	private  Integer isLike;

	/**
	 * 回复内容
	 */
	@ApiModelProperty(value="回复内容")
	private String content;

	/**
	 * 被回复人ID/被点赞人ID
	 */
	@ApiModelProperty(value="被回复人ID/被点赞人ID 腾讯组id")
	private String byReplyId;

	/**
	 * 行程ID
	 */
	@ApiModelProperty(value="行程ID")
	private Long scheduleId;

	/**
	 * 登陆用户ID
	 */
	@ApiModelProperty(value="登陆用户ID")
	@JsonIgnore
	private Long uId;

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getByReplyId() {
		return byReplyId;
	}

	public void setByReplyId(String byReplyId) {
		this.byReplyId = byReplyId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getOnlookersId() {
		return onlookersId;
	}

	public void setOnlookersId(Long onlookersId) {
		this.onlookersId = onlookersId;
	}

	public Integer getIsLike() {
		return isLike;
	}

	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}
}
