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

@Catalog(code = "ShoppingAgencyEntity")
public class ShoppingAgencyEntity implements Serializable {

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
     * 购物场所名称
     */
    @ApiModelProperty(value="购物场所名称")
    private String shoppingName;
    /**
     * 城市名称
     */
    @ApiModelProperty(value="城市名称")
    private String shoppingCity;
    /**
     * 城市Id
     */
    @ApiModelProperty(value="城市ID")
    private String cityId;
    /**
     * 售卖产品
     */
    @ApiModelProperty(value="售卖产品")
    private String sellProducts;
    /**
     * 停留时间
     */
    @ApiModelProperty(value="停留时间")
    private String residenceTime;
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
     * 城市id
     */
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

    public String getShoppingName() {
        return shoppingName;
    }

    public void setShoppingName(String shoppingName) {
        this.shoppingName = shoppingName;
    }

    public String getShoppingCity() {
        return shoppingCity;
    }

    public void setShoppingCity(String shoppingCity) {
        this.shoppingCity = shoppingCity;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getSellProducts() {
        return sellProducts;
    }

    public void setSellProducts(String sellProducts) {
        this.sellProducts = sellProducts;
    }

    public String getResidenceTime() {
        return residenceTime;
    }

    public void setResidenceTime(String residenceTime) {
        this.residenceTime = residenceTime;
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

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "ShoppingAgencyEntity{" +
                "id=" + id +
                ", travelId=" + travelId +
                ", shoppingName='" + shoppingName + '\'' +
                ", shoppingCity='" + shoppingCity + '\'' +
                ", cityId='" + cityId + '\'' +
                ", sellProducts='" + sellProducts + '\'' +
                ", residenceTime='" + residenceTime + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}