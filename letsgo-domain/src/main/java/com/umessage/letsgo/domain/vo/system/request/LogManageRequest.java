package com.umessage.letsgo.domain.vo.system.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class LogManageRequest implements Serializable {
    /**
     * 账号类型:0旅行社管理员，1门店，2计调，3领队
     */
    @ApiModelProperty(value="账号类型:0旅行社管理员，1门店，2计调，3领队")
    private Integer accountType;
    /**
     * 旅行社ID
     */
    @ApiModelProperty(value="旅行社ID")
    private Long travelId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Long userId;
    /**
     * 用户ID集合
     */
    @ApiModelProperty(value="用户ID集合")
    private List<Long> userIds;

    /**
     * 操作模块
     * 0团队行程管理；1统计报表；2领队导游管理；3日志管理；4游客管理；5账户管理；6我的设置
     */
    @ApiModelProperty(value="操作模块：0团队行程管理；1统计报表；2领队导游管理；3日志管理；4游客管理；5账户管理；6我的设置")
    private String operateModel;

    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value="当前页数")
    private int pageNum = 1;
    @ApiModelProperty(value="每页条数")
    private int pageSize = 10;

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
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

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(String operateModel) {
        this.operateModel = operateModel;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
