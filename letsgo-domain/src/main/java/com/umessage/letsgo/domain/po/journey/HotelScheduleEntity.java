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

/**
 * 每日行程酒店实体类
 */
@Catalog(code = "HotelScheduleEntity")
public class HotelScheduleEntity implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 行程详细ID
     */
    @ApiModelProperty(value="行程详细ID")
    private Long scheduleDetailId;
    /**
     * 酒店中文名称
     */
    @ApiModelProperty(value="酒店中文名称")
    private String hotelName;
    /**
     * 是否是同级酒店
     */
    @ApiModelProperty(value="是否是同级酒店[是：1，否：0]")
    private Integer sameLevel;
    /**
     * 酒店是否确认
     */
    @ApiModelProperty(value="酒店是否确认[确认：1，未确认：0]")
    private Integer hotelConfirm;
    /**
     * 酒店不确定后输入的名称
     */
    @ApiModelProperty(value="酒店不确定后输入的名称")
    private String hotelInput;
    /**
     * 大洲
     */
    @ApiModelProperty(value="大洲")
    private String delta;
    /**
     * 国家
     */
    @ApiModelProperty(value="国家")
    private String country;
    /**
     * 城市
     */
    @ApiModelProperty(value="城市")
    private String city;
    /**
     * 酒店星级
     */
    @ApiModelProperty(value="酒店星级")
    private String starLv;
    /**
     * 酒店简介
     */
    @ApiModelProperty(value="酒店简介")
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

    public Long getScheduleDetailId() {
        return scheduleDetailId;
    }

    public void setScheduleDetailId(Long scheduleDetailId) {
        this.scheduleDetailId = scheduleDetailId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getSameLevel() {
        return sameLevel;
    }

    public void setSameLevel(Integer sameLevel) {
        this.sameLevel = sameLevel;
    }

    public Integer getHotelConfirm() {
        return hotelConfirm;
    }

    public void setHotelConfirm(Integer hotelConfirm) {
        this.hotelConfirm = hotelConfirm;
    }

    public String getHotelInput() {
        return hotelInput;
    }

    public void setHotelInput(String hotelInput) {
        this.hotelInput = hotelInput;
    }

    public String getDelta() {
        return delta;
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStarLv() {
        return starLv;
    }

    public void setStarLv(String starLv) {
        this.starLv = starLv;
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
        return "HotelScheduleEntity{" +
                "id=" + id +
                ", scheduleDetailId=" + scheduleDetailId +
                ", hotelName='" + hotelName + '\'' +
                ", sameLevel=" + sameLevel +
                ", hotelConfirm=" + hotelConfirm +
                ", hotelInput='" + hotelInput + '\'' +
                ", delta='" + delta + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", starLv='" + starLv + '\'' +
                ", briefintroduction='" + briefintroduction + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", photoUrls=" + photoUrls +
                ", albumScheduleList=" + albumScheduleList +
                '}';
    }
}