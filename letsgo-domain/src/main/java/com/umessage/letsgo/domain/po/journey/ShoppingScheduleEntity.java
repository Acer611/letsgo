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

/**
 * 每日行程购物场所实体类
 */
@Catalog(code = "ShoppingAgencyEntity")
public class ShoppingScheduleEntity implements Serializable {

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

    @Override
    public String toString() {
        return "ShoppingScheduleEntity{" +
                "id=" + id +
                ", scheduleId=" + scheduleId +
                ", scheduleDetailId=" + scheduleDetailId +
                ", shoppingName='" + shoppingName + '\'' +
                ", shoppingCity='" + shoppingCity + '\'' +
                ", sellProducts='" + sellProducts + '\'' +
                ", residenceTime='" + residenceTime + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}