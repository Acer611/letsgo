/*
 * ScenicEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class ScenicEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 行程id
     */
    @ApiModelProperty(value="行程id")
    private Long scheduleDetailId;
    /**
     * 景点id
     */
    @ApiModelProperty(value="景点id")
    private Long spotId;
    /**
     * 景点名称
     */
    @ApiModelProperty(value="景点名称")
    private String name;
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

    // 开始停留时间
    private String stopStartTime;

    // 结束停留时间
    private String stopEndTime;

    // 纬度
    @ApiModelProperty(value="纬度")
    private Double lat;

    // 经度
    @ApiModelProperty(value="经度")
    private Double lng;

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

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getStopStartTime() {
        return stopStartTime;
    }

    public void setStopStartTime(String stopStartTime) {
        this.stopStartTime = stopStartTime;
    }

    public String getStopEndTime() {
        return stopEndTime;
    }

    public void setStopEndTime(String stopEndTime) {
        this.stopEndTime = stopEndTime;
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
}