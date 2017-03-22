/*
 * TravelNotesEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-10-09 Created by Administrator
 */
package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

public class TravelNotesEntity {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 行程id
     */
    @ApiModelProperty(value="行程id")
    private Long scheduleId;
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Long userId;
    /**
     * 行程图片
     */
    @ApiModelProperty(value="行程图片")
    private String scheduleImgurl;
    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;
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

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getScheduleImgurl() {
        return scheduleImgurl;
    }

    public void setScheduleImgurl(String scheduleImgurl) {
        this.scheduleImgurl = scheduleImgurl == null ? null : scheduleImgurl.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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