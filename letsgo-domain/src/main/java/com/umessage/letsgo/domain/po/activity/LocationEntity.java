/*
 * LocationEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-13 Created by Administrator
 */
package com.umessage.letsgo.domain.po.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class LocationEntity implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 成员实体
     */
    @ApiModelProperty(value="成员实体")
    @Catalog
    private MemberEntity memberEntity;
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
    @ApiModelProperty(value="地址")
    private String location;
    /**
     * 提交时间
     */
    @ApiModelProperty(value="提交时间")
    private Date finishTime;
    private String ip;
    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 版本号
     */
    @JsonIgnore
    private Long version;

    /**
     * 是否为实时位置
     */
    @ApiModelProperty(value="是否为实时位置；1：实时位置；2：历史位置")
    private Integer isCurrentLocation;

    @ApiModelProperty(value="提交位置时间和现在时间的差数，单位为分钟")
    private long beforeTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getIsCurrentLocation() {
        return isCurrentLocation;
    }

    public void setIsCurrentLocation(Integer isCurrentLocation) {
        this.isCurrentLocation = isCurrentLocation;
    }

    public long getBeforeTime() {
        return beforeTime;
    }

    public void setBeforeTime(long beforeTime) {
        this.beforeTime = beforeTime;
    }

    @Override
    public String toString() {
        return "LocationEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", memberEntity=" + memberEntity +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", location='" + location + '\'' +
                ", finishTime=" + finishTime +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                ", version=" + version +
                ", isCurrentLocation=" + isCurrentLocation +
                '}';
    }
}