package com.umessage.letsgo.domain.po.team;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public class OnlookersEntity {
    private Long  id;
    private Long  scheduleId;
    private Long  userId;
    private Integer isSystemInfo;
    private String content;
    private String thumbnailUrl;
    private String  photosUrl;
    private Date sendTime;
    private Date createTime;
    private Long version;
    @ApiModelProperty(value = "图片宽")
    private String wide;
    @ApiModelProperty(value = "图片高")
    private String height;

    public String getWide() {
        return wide;
    }

    public void setWide(String wide) {
        this.wide = wide;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

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

    public Integer getIsSystemInfo() {
        return isSystemInfo;
    }

    public void setIsSystemInfo(Integer isSystemInfo) {
        this.isSystemInfo = isSystemInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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

}
