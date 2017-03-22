/*
 * TravelNotesDetailEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-10-09 Created by Administrator
 */
package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

public class TravelNotesDetailEntity {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 行程明细id
     */
    @ApiModelProperty(value="行程明细id")
    private Long scheduleDetaiIid;
    /**
     * 游记id
     */
    @ApiModelProperty(value="游记id")
    private Long travelNotesId;
    /**
     * 天气
     */
    @ApiModelProperty(value="天气")
    private String weather;
    /**
     * 每日行程图片
     */
    @ApiModelProperty(value="每日行程图片")
    private String scheduleDetaiImgurl;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date updateTime;
    /**
     * 版本
     */
    @ApiModelProperty(value="版本")
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleDetaiIid() {
        return scheduleDetaiIid;
    }

    public void setScheduleDetaiIid(Long scheduleDetaiIid) {
        this.scheduleDetaiIid = scheduleDetaiIid;
    }

    public Long getTravelNotesId() {
        return travelNotesId;
    }

    public void setTravelNotesId(Long travelNotesId) {
        this.travelNotesId = travelNotesId;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather == null ? null : weather.trim();
    }

    public String getScheduleDetaiImgurl() {
        return scheduleDetaiImgurl;
    }

    public void setScheduleDetaiImgurl(String scheduleDetaiImgurl) {
        this.scheduleDetaiImgurl = scheduleDetaiImgurl == null ? null : scheduleDetaiImgurl.trim();
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}