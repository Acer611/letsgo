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
import java.util.*;

/**
 * 景点私有库
 */
@Catalog(code = "ScenicAgencyEntity")
public class ScenicAgencyEntity implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="公用库的ID")
    private String mafengId;
    /**
     * 旅行社ID
     */
    @ApiModelProperty(value="旅行社ID")
    private Long travelId;
    /**
     * 景点名称
     */
    @ApiModelProperty(value="景点名称")
    private String scenicName;
    /**
     * 景点城市
     */
    @ApiModelProperty(value="景点城市")
    private String scenicCity;
    /**
     * 景点简介
     */
    @ApiModelProperty(value="景点简介")
    private String briefintroduction;
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
     * 景点英文名
     */
    @ApiModelProperty(value="景点英文名")
    private String scenicEnName;
    /**
     * 城市ID
     */
    @ApiModelProperty(value="城市ID")
    private String cityId;

    ////以下均不是数据库表字段，均为临时字段

    //照片实体列表
    private List<AlbumAgencyEntity> albumAgencyEntities;

    //图片列表信息
    private List<String> imageList;

    private List<String> cities;


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

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getScenicCity() {
        return scenicCity;
    }

    public void setScenicCity(String scenicCity) {
        this.scenicCity = scenicCity;
    }

    public String getBriefintroduction() {
        return briefintroduction;
    }

    public void setBriefintroduction(String briefintroduction) {
        this.briefintroduction = briefintroduction;
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

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getScenicEnName() {
        return scenicEnName;
    }

    public void setScenicEnName(String scenicEnName) {
        this.scenicEnName = scenicEnName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<AlbumAgencyEntity> getAlbumAgencyEntities() {
        return albumAgencyEntities;
    }

    public void setAlbumAgencyEntities(List<AlbumAgencyEntity> albumAgencyEntities) {
        this.albumAgencyEntities = albumAgencyEntities;
    }

    public String getMafengId() {
        return mafengId;
    }

    public void setMafengId(String mafengId) {
        this.mafengId = mafengId;
    }

    @Override
    public String toString() {
        return "ScenicAgencyEntity{" +
                "id=" + id +
                ", travelId=" + travelId +
                ", scenicName='" + scenicName + '\'' +
                ", scenicCity='" + scenicCity + '\'' +
                ", briefintroduction='" + briefintroduction + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}