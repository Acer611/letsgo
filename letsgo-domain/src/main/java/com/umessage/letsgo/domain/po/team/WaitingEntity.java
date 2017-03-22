/*
 * WaitingEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-09-02 Created by wendy
 */
package com.umessage.letsgo.domain.po.team;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

public class WaitingEntity {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Long userId;
    /**
     * 空闲日期
     */
    @ApiModelProperty(value="空闲日期")
    private String leisureDate;
    /**
     * 忙碌日期
     */
    @ApiModelProperty(value="忙碌日期")
    private String workDate;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLeisureDate() {
        return leisureDate;
    }

    public void setLeisureDate(String leisureDate) {
        this.leisureDate = leisureDate == null ? null : leisureDate.trim();
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate == null ? null : workDate.trim();
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