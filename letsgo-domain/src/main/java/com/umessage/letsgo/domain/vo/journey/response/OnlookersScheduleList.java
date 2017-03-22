package com.umessage.letsgo.domain.vo.journey.response;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pw on 2016/9/27.
 */
public class OnlookersScheduleList implements Serializable {
    /**
     * ID
     */
    @ApiModelProperty(value="行程ID")
    private Long id;
    /**
     * 开始日期
     */
    @ApiModelProperty(value="开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 结束日期
     */
    @ApiModelProperty(value="结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 腾讯群ID
     */
    @ApiModelProperty(value="腾讯群ID")
    private String txGroupId;

    @ApiModelProperty(value="围观未读数量")
    private Integer watchUnreadCount;
    /**
     * 行程名称
     */
    @ApiModelProperty(value="行程名称")
    private String name;

    @ApiModelProperty(value="背景图片")
    private String backgroundPhotoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTxGroupId() {
        return txGroupId;
    }

    public void setTxGroupId(String txGroupId) {
        this.txGroupId = txGroupId;
    }

    public Integer getWatchUnreadCount() {
        return watchUnreadCount;
    }

    public void setWatchUnreadCount(Integer watchUnreadCount) {
        this.watchUnreadCount = watchUnreadCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundPhotoUrl() {
        return backgroundPhotoUrl;
    }

    public void setBackgroundPhotoUrl(String backgroundPhotoUrl) {
        this.backgroundPhotoUrl = backgroundPhotoUrl;
    }
}
