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

public class ScheduleDetailIdUserIdEntity {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleDetailIdUserIdEntity that = (ScheduleDetailIdUserIdEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (satisfiedStatus != null ? !satisfiedStatus.equals(that.satisfiedStatus) : that.satisfiedStatus != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        return !(scheduleDetailId != null ? !scheduleDetailId.equals(that.scheduleDetailId) : that.scheduleDetailId != null);

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (satisfiedStatus != null ? satisfiedStatus.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (scheduleDetailId != null ? scheduleDetailId.hashCode() : 0);
        return result;
    }
}