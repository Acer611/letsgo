/*
 * CallDetailHistoryEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.domain.po.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class CallDetailHistoryEntity implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 点名ID
     */
    @ApiModelProperty(value="点名ID")
    private Long callId;
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
     * 成员实体
     */
    @ApiModelProperty(value="成员实体")
    @Catalog
    private MemberEntity memberEntity;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;
    /**
     * 小组人数
     */
    @ApiModelProperty(value="小组人数")
    private Integer groupCount;
    /**
     * 签到状态【0：未签到；1：已签到】
     */
    @ApiModelProperty(value="是否签到【0：未签到；1：已签到】")
    private Integer status;

    @ApiModelProperty(value = "是否是手动签到[0:否；1:是]")
    private Integer isManual;
    /**
     * 签到时间
     */
    @ApiModelProperty(value="签到时间",dataType = "java.lang.Long")
    private Date callTime;
    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度")
    private Double latitude;
    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private Double longitude;
    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String address;
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
     * 距离
     */
    @ApiModelProperty(value="距离")
    private Double distance;
    /**
     * 用户id,游客用户id
     */
    @ApiModelProperty(value="用户Id")
    private Long userId;

    @ApiModelProperty(value = "组头像地址")
    private String groupPhotoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
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

    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    public void setMemberEntity(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsManual() {
        return isManual;
    }

    public void setIsManual(Integer isManual) {
        this.isManual = isManual;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupPhotoUrl() {
        return groupPhotoUrl;
    }

    public void setGroupPhotoUrl(String groupPhotoUrl) {
        this.groupPhotoUrl = groupPhotoUrl;
    }
}