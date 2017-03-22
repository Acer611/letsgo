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
import java.util.List;

@Catalog(code = "OwnExpenseAgencyEntity")
public class OwnExpenseAgencyEntity implements Serializable {

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
     * 城市Id
     */
    @ApiModelProperty(value="城市ID")
    private String cityId;
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

    /**
     * 城市id
     */
    private List<String> cities;

    @ApiModelProperty(value="旅行社私有库相册实体列表")
    private List<AlbumAgencyEntity> albumAgencyList;

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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public List<AlbumAgencyEntity> getAlbumAgencyList() {
        return albumAgencyList;
    }

    public void setAlbumAgencyList(List<AlbumAgencyEntity> albumAgencyList) {
        this.albumAgencyList = albumAgencyList;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "OwnExpenseAgencyEntity{" +
                "id=" + id +
                ", travelId=" + travelId +
                ", itemName='" + itemName + '\'' +
                ", limitNumber='" + limitNumber + '\'' +
                ", serviceItem='" + serviceItem + '\'' +
                ", referencePrice='" + referencePrice + '\'' +
                ", briefintroduction='" + briefintroduction + '\'' +
                ", itemCity='" + itemCity + '\'' +
                ", cityId='" + cityId + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", photoUrls=" + photoUrls +
                ", albumAgencyList=" + albumAgencyList +
                '}';
    }
}