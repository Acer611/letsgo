package com.umessage.letsgo.domain.vo.team.respone;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wendy on 2016/6/18.
 */
public class TeamMessage implements Serializable {
    @ApiModelProperty(value = "是否有未读公告，值为1，则有，值为0，则没有")
    private Integer existAnnouncement;
    @ApiModelProperty(value = "最新公告ID")
    private Long lastAnnouncementId;
    @ApiModelProperty(value = "是否有未读及未确认通知，值为1，则有，值为0，则没有")
    private Integer existNotice;
    @ApiModelProperty(value = "最新通知ID")
    private Long lastNoticeId;
    @ApiModelProperty(value = "是否有未读及未确认集合，值为1，则有，值为0，则没有")
    private Integer existGather;
    @ApiModelProperty(value = "最新集合ID")
    private Long lastGatherId;

    public Integer getExistAnnouncement() {
        return existAnnouncement;
    }

    public void setExistAnnouncement(Integer existAnnouncement) {
        this.existAnnouncement = existAnnouncement;
    }

    public Long getLastAnnouncementId() {
        return lastAnnouncementId;
    }

    public void setLastAnnouncementId(Long lastAnnouncementId) {
        this.lastAnnouncementId = lastAnnouncementId;
    }

    public Integer getExistNotice() {
        return existNotice;
    }

    public void setExistNotice(Integer existNotice) {
        this.existNotice = existNotice;
    }

    public Long getLastNoticeId() {
        return lastNoticeId;
    }

    public void setLastNoticeId(Long lastNoticeId) {
        this.lastNoticeId = lastNoticeId;
    }

    public Integer getExistGather() {
        return existGather;
    }

    public void setExistGather(Integer existGather) {
        this.existGather = existGather;
    }

    public Long getLastGatherId() {
        return lastGatherId;
    }

    public void setLastGatherId(Long lastGatherId) {
        this.lastGatherId = lastGatherId;
    }
}
