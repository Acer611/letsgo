package com.umessage.letsgo.domain.po.notice;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by PW on 2016/8/24.
 * 领队端最新消息响应体
 */
public class LastMessageVo {

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;

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
     * 集合时间
     */
    @ApiModelProperty(value="集合时间")
    private Date time;
    /**
     * 时区
     */
    @ApiModelProperty(value="时区")
    private String timezone;

    /**
     * 时区ID
     */
    @ApiModelProperty(value="时区Id")
    private String timezoneId;
    /**
     * 位置信息
     */
    @ApiModelProperty(value="位置信息")
    private String location;
    /**
     * 展示最新的消息分类1:集合，2:通知，3:公告，4:问卷
     */
    @ApiModelProperty(value="展示最新的消息分类1:集合，2:通知，3:公告，4:问卷")
    private Integer messageType;
    /**
     * 集合或通知或问卷id
     */
    @ApiModelProperty(value="集合或通知或问卷id")
    private Long id;
    /**
     * 腾迅云groupId
     */
    @ApiModelProperty(value="腾迅云groupId")
    private String teamId;

    /**
     * 是否展示	1展示 ，0不展示
     */
    @ApiModelProperty(value="是否展示 1展示 ，0不展示")
    private Integer isShow;

    @ApiModelProperty(value="交通信息")
    private String traffic;

    @ApiModelProperty(value="发送人")
    private String senderName;

    @ApiModelProperty(value="发送人头像")
    private String photoUrl;

    @ApiModelProperty(value="发送时间")
    private Date sendTime;

    @ApiModelProperty(value="是否确认 0：未确认；1：已确认")
    private Integer activeStatus;

    @ApiModelProperty(value="行程名称")
    private String  scheduleName;

    public String getVideoLen() {
        return videoLen;
    }

    public void setVideoLen(String videoLen) {
        this.videoLen = videoLen;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }
}
