package com.umessage.letsgo.domain.vo.notice.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class NoticeRequest extends CommonRequest {
	/**
	 * 腾迅groupid
	 */
	@ApiModelProperty(value = "腾迅groupid", required = true)
	private String teamId;
	/**
	 * 接受人ID，如果是小组，接收人ID设置组长ID
	 */
	@ApiModelProperty(value = "接受人ID，如果是小组，接收人ID设置组长ID", required = true)
	private List<Long> memberIds = new ArrayList<Long>();
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;
	/**
	 * 语音地址
	 */
	@ApiModelProperty(value = "语音地址")
	private String videoUrl;
	/**
	 * 语音时长
	 */
	@ApiModelProperty(value = "语音时长")
	private String videoLen;
	/**
	 * 事件/集合时间
	 */
	@ApiModelProperty(value = "事件/集合时间")
	private Long time;
	/**
	 * 提醒
	 */
	@ApiModelProperty(value = "提醒")
	private Integer firstRemind;
	/**
	 * 时区
	 */
	@ApiModelProperty(value = "时区")
	private String timezone;

	/**
	 * 时区
	 */
	@ApiModelProperty(value = "时区Id")
	private String timezoneId;
	/**
	 * 经度
	 */
	@ApiModelProperty(value = "经度")
	private Double longitude;
	/**
	 * 纬度
	 */
	@ApiModelProperty(value = "纬度")
	private Double latitude;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	private String location;
	/**
	 * 交通
	 */
	@ApiModelProperty(value = "交通")
	private String traffic;

	/**
	 * 通知是否需要游客签名确认
	 */
	@ApiModelProperty(value = "通知是否需要游客签名确认  0 不需要  1 需要")
	private Integer noticeType;
	/**
	 * 用户ID
	 */
	@JsonIgnore
	private Long userId;
	/**
	 * 类型，集合为1，通知为2
	 *
	 * @return
	 */
	@JsonIgnore
	private Integer type;


	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public List<Long> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoLen() {
		return videoLen;
	}

	public void setVideoLen(String videoLen) {
		this.videoLen = videoLen;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getFirstRemind() {
		return firstRemind;
	}

	public void setFirstRemind(Integer firstRemind) {
		this.firstRemind = firstRemind;
	}

	public String getTimezoneId() {
		return timezoneId;
	}

	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
}