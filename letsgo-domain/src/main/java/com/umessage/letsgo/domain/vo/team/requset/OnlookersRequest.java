package com.umessage.letsgo.domain.vo.team.requset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/5.
 */
public class OnlookersRequest extends CommonRequest {
    //行程ID
    private Long scheduleId;
    //围观用户（围观别人的）-- otherId
    private Long otherId;
    //发布围观用户(他是被围观的)-- ownerId
    private Long ownerId;

    @ApiModelProperty(value="当前页数")
    @JsonIgnore
    private int pageNum = 1;
    @ApiModelProperty(value="每页条数")
    @JsonIgnore
    private int pageSize = 10;
    //围观ID
    private Long onlookersId;

    //被围观用户ID集合
    private List<Long> userIds;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public Long getOnlookersId() {
        return onlookersId;
    }

    public void setOnlookersId(Long onlookersId) {
        this.onlookersId = onlookersId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOtherId() {
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
    }
}
