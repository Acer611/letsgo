/*
 * DestinationEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-07-11 Created by wendy
 */
package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class DestinationEntity implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 目的地
     */
    @ApiModelProperty(value="目的地")
    private String destination;
    /**
     * 所在城市
     */
    @ApiModelProperty(value="所在城市")
    private String city;
    /**
     * 所在时区
     */
    @ApiModelProperty(value="所在时区")
    private String timezone;
    /**
     * 所在国家
     */
    @ApiModelProperty(value="所在国家")
    private String country;
    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private Double lat;
    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度")
    private Double lng;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone == null ? null : timezone.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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

    @Override
    public String toString() {
        return "DestinationEntity{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", city='" + city + '\'' +
                ", timezone='" + timezone + '\'' +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", createTime=" + createTime +
                ", version=" + version +
                '}';
    }
}