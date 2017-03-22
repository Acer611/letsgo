/*
 * TeamEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-12 Created by Administrator
 */
package com.umessage.letsgo.domain.po.team;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 照片私有库实体
 */
@Catalog(code = "AlbumAgencyEntity")
public class AlbumAgencyEntity implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 旅行社ID
     */
    @ApiModelProperty(value="旅行社ID")
    private Long travelId;
    /**
     * 旅行社私有表id
     */
    @ApiModelProperty(value="旅行社私有表id")
    private Long objectId;
    /**
     * 旅行社私有表类型（1：景点，2：酒店，3：自费项目）
     */
    @ApiModelProperty(value="旅行社私有表类型")
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

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
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
        return "AlbumAgencyEntity{" +
                "id=" + id +
                ", travelId=" + travelId +
                ", objectId=" + objectId +
                ", type=" + type +
                ", photoUrl='" + photoUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}