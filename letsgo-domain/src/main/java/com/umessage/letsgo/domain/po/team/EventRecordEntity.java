package com.umessage.letsgo.domain.po.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by pw on 2016/8/18.
 */

@Catalog(code = "EventRecordEntity")
public class EventRecordEntity implements Serializable {
    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 腾迅groupid
     */
    @ApiModelProperty(value="腾迅groupid")
    private String teamId;

    /**
     * 领队id
     */
    @ApiModelProperty(value="领队id")
    private Long leaderId;

    /**
     * 语音地址
     */
    @ApiModelProperty(value="语音地址")
    private String voiceUrl;

    /**
     * 文本内容
     */
    @ApiModelProperty(value="文本内容")
    private String content;

    /**
     * 记录时间
     */
    @ApiModelProperty(value="记录时间")
    private String recordTime;

    /**
     * 事件经度
     */
    @ApiModelProperty(value="事件经度")
    private String lon;

    /**
     * 事件纬度
     */
    @ApiModelProperty(value="事件纬度")
    private String lat;

    /**
     * 事件位置
     */
    @ApiModelProperty(value="事件位置")
    private String location;

    /**
     * 图片地址
     */
    @ApiModelProperty(value="图片地址")
    @JsonIgnore
    private String photosUrl;

    /**
     * 缩略图地址
     */
    @ApiModelProperty(value="缩略图地址")
    private String thumbnailUrl;

    /**
     * 创建人(用户id)
     */
    @ApiModelProperty(value="创建人(用户id)")
    private Long createBy;
    /**
     * 版本号
     */
    @JsonIgnore
    private Long version;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 图片列表---临时字段
     */
    @ApiModelProperty(value="图片列表")
    private List<String> photosList;

    /**
     * 领队名程---临时字段
     */
    @ApiModelProperty(value="领队名程")
    private String leadName;

    /**
     * 领队电话---临时字段
     */
    @ApiModelProperty(value="领队电话")
    private String phone;

    /**
     * 语音时长
     */
    @ApiModelProperty(value="语音时长")
    private String videoLen;

    /**
     * 团号---临时字段
     */
    @JsonIgnore
    private String teamNum;
    /**
     * 开始时间---临时字段
     */
    @JsonIgnore
    private Date startDate;
    /**
     * 结束时间---临时字段
     */
    @JsonIgnore
    private Date endDate;
    /**
     * 团名称---临时字段
     */
    @JsonIgnore
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public String getVideoLen() {
        return videoLen;
    }

    public void setVideoLen(String videoLen) {
        this.videoLen = videoLen;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(List<String> photosList) {
        this.photosList = photosList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
}
