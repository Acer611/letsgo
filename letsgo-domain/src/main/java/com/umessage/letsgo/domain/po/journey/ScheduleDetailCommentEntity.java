/*
 * ScheduleDetailCommentEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-09-20 Created by Administrator
 */
package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleDetailCommentEntity {

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
     * 5为非常满意 4为满意 3为一般 2为不满意 1为非常不满意
     */
    @ApiModelProperty(value="5为非常满意 4为满意 3为一般 2为不满意 1为非常不满意")
    private Integer satisfiedStatus;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 版本
     */
    @ApiModelProperty(value="版本")
    private Long version;
    /**
     * 行程明细ID
     */
    @ApiModelProperty(value="行程明细ID")
    private Long scheduleDetailId;

    /**
     * 点评明细列表
     */
    @ApiModelProperty(value="点评明细列表")
    private List<ScoreEntity> scoreEntities = new ArrayList<ScoreEntity>();

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

    public Integer getSatisfiedStatus() {
        return satisfiedStatus;
    }

    public void setSatisfiedStatus(Integer satisfiedStatus) {
        this.satisfiedStatus = satisfiedStatus;
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

    public Long getScheduleDetailId() {
        return scheduleDetailId;
    }

    public void setScheduleDetailId(Long scheduleDetailId) {
        this.scheduleDetailId = scheduleDetailId;
    }

    public List<ScoreEntity> getScoreEntities() {
        return scoreEntities;
    }

    public void setScoreEntities(List<ScoreEntity> scoreEntities) {
        this.scoreEntities = scoreEntities;
    }
}