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

@Catalog(code = "HotelAgencyEntity")
public class HotelAgencyEntity implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 公有库酒店的ID
     */
    @ApiModelProperty(value="hotelId")
    private String hotelId;
    /**
     * 旅行社ID
     */
    @ApiModelProperty(value="旅行社ID")
    private Long travelId;
    /**
     * 酒店中文名称
     */
    @ApiModelProperty(value="酒店中文名称")
    private String hotelName;
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
     * 城市Id
     */
    @ApiModelProperty(value="城市ID")
    private String cityId;
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

    /**
     * 城市id
     */
    private List<String> cities;

    @ApiModelProperty(value="旅行社私有库相册实体列表")
    private List<AlbumAgencyEntity> albumAgencyList;

    //以下为临时字段
    private String hotelEnglishName;

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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<AlbumAgencyEntity> getAlbumAgencyList() {
        return albumAgencyList;
    }

    public void setAlbumAgencyList(List<AlbumAgencyEntity> albumAgencyList) {
        this.albumAgencyList = albumAgencyList;
    }

    public String getHotelEnglishName() {
        return hotelEnglishName;
    }

    public void setHotelEnglishName(String hotelEnglishName) {
        this.hotelEnglishName = hotelEnglishName;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "HotelAgencyEntity{" +
                "id=" + id +
                ", travelId=" + travelId +
                ", hotelName='" + hotelName + '\'' +
                ", delta='" + delta + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", cityId='" + cityId + '\'' +
                ", starLv='" + starLv + '\'' +
                ", briefintroduction='" + briefintroduction + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", photoUrls=" + photoUrls +
                ", cities=" + cities +
                ", albumAgencyList=" + albumAgencyList +
                ", hotelEnglishName='" + hotelEnglishName + '\'' +
                '}';
    }
}