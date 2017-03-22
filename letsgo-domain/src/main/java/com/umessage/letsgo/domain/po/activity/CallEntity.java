/*
 * CallEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by Administrator
 */
package com.umessage.letsgo.domain.po.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CallEntity implements Serializable {

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
     * 用户ID，点名人
     */
    @ApiModelProperty(value="用户ID，点名人")
    private Long userId;
    /**
     * 位置ID，点名人位置
     */
    @ApiModelProperty(value="位置ID，点名人位置")
    private Long locationId;
    /**
     * 进行状态【0：进行中；1：已完成】
     */
    @ApiModelProperty(value="是否还在进行点名【0：进行中；1：已完成】")
    private Integer status;
    /**
     * 未到人数
     */
    @ApiModelProperty(value="未到人数")
    private Integer notarrivedCount;
    /**
     * 已到人数
     */
    @ApiModelProperty(value="已到人数")
    private Integer arrivedCount;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String mark;
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
     * 点名明细列表
     */
    @ApiModelProperty(value="点名明细列表")
    @JsonIgnore
    private List<CallDetailEntity> callDetailList;
    /**
     * 已到的点名明细列表
     */

    @ApiModelProperty(value="已到的点名明细列表")
    @Catalog
    private List<CallDetailEntity> arrivedList;
    /**
     * 未到的点名明细列表
     */
    @ApiModelProperty(value="未到的点名明细列表")
    @Catalog
    private List<CallDetailEntity> notarrivedList;

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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNotarrivedCount() {
        return notarrivedCount;
    }

    public void setNotarrivedCount(Integer notarrivedCount) {
        this.notarrivedCount = notarrivedCount;
    }

    public Integer getArrivedCount() {
        return arrivedCount;
    }

    public void setArrivedCount(Integer arrivedCount) {
        this.arrivedCount = arrivedCount;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
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

	public List<CallDetailEntity> getCallDetailList() {
		return callDetailList;
	}

	public void setCallDetailList(List<CallDetailEntity> callDetailList) {
		this.callDetailList = callDetailList;
	}

	public List<CallDetailEntity> getArrivedList() {
		return arrivedList;
	}

	public void setArrivedList(List<CallDetailEntity> arrivedList) {
		this.arrivedList = arrivedList;
	}

	public List<CallDetailEntity> getNotarrivedList() {
		return notarrivedList;
	}

	public void setNotarrivedList(List<CallDetailEntity> notarrivedList) {
		this.notarrivedList = notarrivedList;
	}

    @Override
    public String toString() {
        return "CallEntity{" +
                "id=" + id +
                ", tId=" + tId +
                ", teamId='" + teamId + '\'' +
                ", userId=" + userId +
                ", locationId=" + locationId +
                ", status=" + status +
                ", notarrivedCount=" + notarrivedCount +
                ", arrivedCount=" + arrivedCount +
                ", mark='" + mark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", callDetailList=" + callDetailList +
                ", arrivedList=" + arrivedList +
                ", notarrivedList=" + notarrivedList +
                '}';
    }
}