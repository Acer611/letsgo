/*
 * NoticeDetailEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-06-29 Created by wendy
 */
package com.umessage.letsgo.domain.po.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.*;

public class NoticeDetailEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 通知ID
     */
    @ApiModelProperty(value="通知ID")
    private Long noticeId;
    /**
     * 小组ID
     */
    @ApiModelProperty(value="小组ID")
    private Long groupId;
    /**
     * 成员ID
     */
    @ApiModelProperty(value="成员ID")
    private Long memberId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Long userId;
    /**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    private String teamId;
    /**
     * 消息分类【1：集合；2：通知；3：公告；4：回复】
     */
    @ApiModelProperty(value="消息分类【1：集合；2：通知；3：公告；4：回复】")
    private Integer type;
    /**
     * 是否需要确认【0：不需要；1：需要】
     */
    @ApiModelProperty(value="是否需要确认【0：不需要；1：需要】")
    private Integer isActive;
    /**
     * 是否读取/确认【如果是需要确认：0：未确认；1：已确认；如果是不需要确认：0：未读取；1：已读取】
     */
    @ApiModelProperty(value="是否读取/确认【如果是需要确认：0：未确认；1：已确认；如果是不需要确认：0：未读取；1：已读取】")
    private Integer activeStatus;
    /**
     * 确认时间
     */
    @ApiModelProperty(value="确认时间")
    private Date activeTime;
    /**
     * 用户是否点击查看列表动作
     */
    @JsonIgnore
    private Integer isOption;
    /**
     * 创建时间
     */
    @JsonIgnore
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
     * 小组实体
     */
    @ApiModelProperty(value="小组实体")
    private GroupEntity groupEntity;
    /**
     * 成员实体
     */
    @ApiModelProperty(value="成员实体")
    private MemberEntity memberEntity;

    /**
     * 签名实体
     */
    @ApiModelProperty(value="签名实体")
    private List<NoticeSignEntity> noticeSignEntityList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Integer getIsOption() {
        return isOption;
    }

    public void setIsOption(Integer isOption) {
        this.isOption = isOption;
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

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    public void setMemberEntity(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<NoticeSignEntity> getNoticeSignEntityList() {
        return noticeSignEntityList;
    }

    public void setNoticeSignEntityList(List<NoticeSignEntity> noticeSignEntityList) {
        this.noticeSignEntityList = noticeSignEntityList;
    }
}