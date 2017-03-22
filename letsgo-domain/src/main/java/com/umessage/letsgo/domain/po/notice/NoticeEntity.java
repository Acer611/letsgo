/*
 * NoticeEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by Administrator
 */
package com.umessage.letsgo.domain.po.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NoticeEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    @JsonIgnore
    private Long tId;
    /**
     * 腾讯群组ID
     */
    @ApiModelProperty(value="腾讯群组ID")
    private String teamId;
    /**
     * 发送人用户ID
     */
    @ApiModelProperty(value="发送人用户ID")
    private Long userId;
    /**
     * 发送人
     */
    @ApiModelProperty(value="发送人")
    private Long senderId;
    /**
     * 发送人姓名
     */
    @ApiModelProperty(value="发送人姓名")
    private String senderName;
    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;
    /**
     * 语音地址
     */
    @ApiModelProperty(value="语音地址")
    private String videoUrl;
    /**
     * 语音时长
     */
    @ApiModelProperty(value="语音时长")
    private String videoLen;
    /**
     * 消息类型【1：集合；2：通知】
     */
    @JsonIgnore
    private Integer type;
    /**
     * 事件/集合时间
     */
    @ApiModelProperty(value="事件/集合时间")
    private Date time;
    /**
     * 提醒
     */
    @ApiModelProperty(value="提醒")
    private Integer firstRemind;
    /**
     * 时区
     */
    @ApiModelProperty(value="时区")
    private String timezone;
    /**
     * 时区Id
     */
    @ApiModelProperty(value="时区Id")
    private String timezoneId;
    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private Double longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度")
    private Double latitude;
    /**
     * 地址
     */
    @ApiModelProperty(value="位置信息")
    private String location;
    /**
     * 交通
     */
    @ApiModelProperty(value="交通")
    private String traffic;
    /**
     * 已确认人数
     */
    @ApiModelProperty(value="已确认人数")
    private Integer sureCount;
    /**
     * 未确认人数
     */
    @ApiModelProperty(value="未确认人数")
    private Integer notsureCount;


    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",dataType = "java.lang.Long")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonIgnore
    private Date updateTime;
    /**
     * 版本号
     */
    @JsonIgnore
    private Long version;
    /**
     * 通知回复列表
     */
    @JsonIgnore
    private List<NoticeReplyEntity> noticeReplyList;

    /**
     * 通知确认列表
     */
    @JsonIgnore
    private List<NoticeDetailEntity> noticeDetailList;
    /**
     * 团队名称
     */
    @ApiModelProperty(value="团队名称")
    private String teamName;

    /**
     * 发送人头像
     */
    @ApiModelProperty(value="发送人头像")
    private String senderPhotoUrl;
    /**
     * 总人数
     */
    @ApiModelProperty(value="总人数")
    private Integer totalCount;
    /**
     * 回复条数
     */
    @ApiModelProperty(value="回复条数")
    private Integer replyCount;
    /**
     * 是否需要确认，1为需要确认，0为不需要确认
     */
    @ApiModelProperty(value="通知是否需要确认，1为需要确认，0为不需要确认")
    private Integer isConfirm;
    /**
     * 签名人数
     */
    @ApiModelProperty(value="签名人数")
    private Integer signCount;

    /**
     * 通知是否需要游客签名的状态
     */
    @ApiModelProperty(value="通知是否需要游客签名的状态  0 不需要签名  1 需要游客签名 ")
    private Integer noticeType;
    /**
     * 是否读取 0：未读取；1：已读取】 --临时字段
     */
    @ApiModelProperty(value="是否读取0：未读取；1：已读取")
    private Integer activeStatus;

    @ApiModelProperty(value="行程名称")
    private String scheduleName;

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName == null ? null : senderName.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getVideoLen() {
        return videoLen;
    }

    public void setVideoLen(String videoLen) {
        this.videoLen = videoLen == null ? null : videoLen.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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
        this.timezone = timezone == null ? null : timezone.trim();
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
        this.location = location == null ? null : location.trim();
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic == null ? null : traffic.trim();
    }

    public Integer getSureCount() {
        return sureCount;
    }

    public void setSureCount(Integer sureCount) {
        this.sureCount = sureCount;
    }

    public Integer getNotsureCount() {
        return notsureCount;
    }

    public void setNotsureCount(Integer notsureCount) {
        this.notsureCount = notsureCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<NoticeReplyEntity> getNoticeReplyList() {
        return noticeReplyList;
    }

    public void setNoticeReplyList(List<NoticeReplyEntity> noticeReplyList) {
        this.noticeReplyList = noticeReplyList;
    }

    public List<NoticeDetailEntity> getNoticeDetailList() {
        return noticeDetailList;
    }

    public void setNoticeDetailList(List<NoticeDetailEntity> noticeDetailList) {
        this.noticeDetailList = noticeDetailList;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getSenderPhotoUrl() {
        return senderPhotoUrl;
    }

    public void setSenderPhotoUrl(String senderPhotoUrl) {
        this.senderPhotoUrl = senderPhotoUrl;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(Integer isConfirm) {
        this.isConfirm = isConfirm;
    }


    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }
}