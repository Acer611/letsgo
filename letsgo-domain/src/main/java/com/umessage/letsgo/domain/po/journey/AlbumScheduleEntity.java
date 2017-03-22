/*
 * ScheduleDetailEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class AlbumScheduleEntity implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 行程ID
     */
    @ApiModelProperty(value="行程ID")
    private Long scheduleId;
    /**
     * 行程详细ID
     */
    @ApiModelProperty(value="行程详细ID")
    private Long scheduleDetailId;
    /**
     * 每日行程关联表id
     */
    @ApiModelProperty(value="每日行程关联表id")
    private Long objectId;
    /**
     * 类型（1：景点，2：酒店，3：自费项目）
     */
    @ApiModelProperty(value="类型")
    private Integer type;
    /**
     * 图片地址
     */
    @ApiModelProperty(value="图片地址")
    private String photoUrl;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleDetailId() {
        return scheduleDetailId;
    }

    public void setScheduleDetailId(Long scheduleDetailId) {
        this.scheduleDetailId = scheduleDetailId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    @Override
    public String toString() {
        return "AlbumScheduleEntity{" +
                "id=" + id +
                ", scheduleId=" + scheduleId +
                ", scheduleDetailId=" + scheduleDetailId +
                ", objectId=" + objectId +
                ", type=" + type +
                ", photoUrl='" + photoUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}