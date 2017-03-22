/*
 * AnnouncementEntity.java
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

public class AnnouncementEntity  implements Serializable {

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
     * 发送人用户ID
     */
    @ApiModelProperty(value="发送人用户ID")
    private Long userId;
    /**
     * 发送人姓名
     */
    @ApiModelProperty(value="发送人姓名")
    private String senderName;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;
    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private Long version;

    /**
     * 团队名称
     */
    @ApiModelProperty(value="团队名称")
    private String teamName;

    /**
     * 是否读取 0：未读取；1：已读取】 --临时字段
     */
    @ApiModelProperty(value="是否读取0：未读取；1：已读取")
    private Integer activeStatus;

    @ApiModelProperty(value="发送人头像")
    private String photoUrl;

    @ApiModelProperty(value="行程名称")
    private String scheduleName;

    //临时字段
    /**
     * 发送人
     */
    @ApiModelProperty(value="发送人")
    private Long senderId;

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName == null ? null : senderName.trim();
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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "AnnouncementEntity{" +
                "id=" + id +
                ", tId=" + tId +
                ", teamId='" + teamId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", senderName='" + senderName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}