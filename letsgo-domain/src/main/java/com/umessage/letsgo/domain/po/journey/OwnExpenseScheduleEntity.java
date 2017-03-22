/*
 * TeamEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-12 Created by Administrator
 */
package com.umessage.letsgo.domain.po.journey;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Catalog(code = "OwnExpenseScheduleEntity")
public class OwnExpenseScheduleEntity implements Serializable {

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
     * 自费项目名
     */
    @ApiModelProperty(value="自费项目名")
    private String itemName;
    /**
     * 人数限制
     */
    @ApiModelProperty(value="人数限制")
    private String limitNumber;
    /**
     * 服务项目
     */
    @ApiModelProperty(value="服务项目")
    private String serviceItem;
    /**
     * 参考价格
     */
    @ApiModelProperty(value="参考价格")
    private String referencePrice;
    /**
     * 简介
     */
    @ApiModelProperty(value="简介")
    private String briefintroduction;
    /**
     * 项目城市
     */
    @ApiModelProperty(value="项目城市")
    private String itemCity;
    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private String lon;
    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度")
    private String lat;
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
     * 照片地址
     */
    private List<String> photoUrls;

    @ApiModelProperty(value="每日行程相册实体列表")
    private List<AlbumScheduleEntity> albumScheduleList;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(String limitNumber) {
        this.limitNumber = limitNumber;
    }

    public String getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(String serviceItem) {
        this.serviceItem = serviceItem;
    }

    public String getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(String referencePrice) {
        this.referencePrice = referencePrice;
    }

    public String getBriefintroduction() {
        return briefintroduction;
    }

    public void setBriefintroduction(String briefintroduction) {
        this.briefintroduction = briefintroduction;
    }

    public String getItemCity() {
        return itemCity;
    }

    public void setItemCity(String itemCity) {
        this.itemCity = itemCity;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
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

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<AlbumScheduleEntity> getAlbumScheduleList() {
        return albumScheduleList;
    }

    public void setAlbumScheduleList(List<AlbumScheduleEntity> albumScheduleList) {
        this.albumScheduleList = albumScheduleList;
    }

    @Override
    public String toString() {
        return "OwnExpenseScheduleEntity{" +
                "id=" + id +
                ", scheduleId=" + scheduleId +
                ", scheduleDetailId=" + scheduleDetailId +
                ", itemName='" + itemName + '\'' +
                ", limitNumber=" + limitNumber +
                ", serviceItem='" + serviceItem + '\'' +
                ", referencePrice=" + referencePrice +
                ", briefintroduction='" + briefintroduction + '\'' +
                ", itemCity='" + itemCity + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", photoUrls=" + photoUrls +
                ", albumScheduleList=" + albumScheduleList +
                '}';
    }
}