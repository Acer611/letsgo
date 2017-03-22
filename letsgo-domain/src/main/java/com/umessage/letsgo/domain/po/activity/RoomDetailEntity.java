/*
 * RoomDetailEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-08 Created by Administrator
 */
package com.umessage.letsgo.domain.po.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@Catalog(code = "RoomDetailEntity")
public class RoomDetailEntity implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 房间ID
     */
    @ApiModelProperty(value="房间ID")
    private Long roomId;
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
     * 房间明细列表
     */
    @ApiModelProperty(value="成员表实体")
    @Catalog
    private MemberEntity memberEntity;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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

    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    public void setMemberEntity(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    @Override
    public String toString() {
        return "RoomDetailEntity{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", groupId=" + groupId +
                ", memberId=" + memberId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", memberEntity=" + memberEntity +
                '}';
    }
}