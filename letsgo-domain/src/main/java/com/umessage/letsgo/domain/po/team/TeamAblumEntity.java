package com.umessage.letsgo.domain.po.team;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by zengguoqing on 2016/8/18.
 */
public class TeamAblumEntity {
    @ApiModelProperty(value="ID")
    private Long id;
    @ApiModelProperty(value="腾讯云相册id")
    private String teamId;
    @ApiModelProperty(value="图片地址")
    private String photoUrl;
    @ApiModelProperty(value="缩略图地址")
    private String thumbnailUrl;
    @ApiModelProperty(value="IM原图地址")
    private String imPhotoUrl;
    @ApiModelProperty(value="IM大图地址")
    private String imLargeUrl;
    @ApiModelProperty(value="IM缩略图地址")
    private String imThumbnailUrl;
    @ApiModelProperty(value="拍摄人")
    private String author;
    @ApiModelProperty(value="发图人所在地")
    private String location;
    @ApiModelProperty(value="拍摄地所在纬度")
    private String lat;
    @ApiModelProperty(value="拍摄地所在经度")
    private String lng;
    @ApiModelProperty(value="拍摄时间")
    private Date photographTime;
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    @ApiModelProperty(value="版本号")
    private Integer version;

    @ApiModelProperty(value="类型 1可见 2隐藏")
    private Integer type;

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getImPhotoUrl() {
        return imPhotoUrl;
    }

    public void setImPhotoUrl(String imPhotoUrl) {
        this.imPhotoUrl = imPhotoUrl;
    }

    public String getImLargeUrl() {
        return imLargeUrl;
    }

    public void setImLargeUrl(String imLargeUrl) {
        this.imLargeUrl = imLargeUrl;
    }

    public String getImThumbnailUrl() {
        return imThumbnailUrl;
    }

    public void setImThumbnailUrl(String imThumbnailUrl) {
        this.imThumbnailUrl = imThumbnailUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Date getPhotographTime() {
        return photographTime;
    }

    public void setPhotographTime(Date photographTime) {
        this.photographTime = photographTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TeamAblumEntity{" +
                "id=" + id +
                ", teamId='" + teamId + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", imPhotoUrl='" + imPhotoUrl + '\'' +
                ", imLargeUrl='" + imLargeUrl + '\'' +
                ", imThumbnailUrl='" + imThumbnailUrl + '\'' +
                ", author='" + author + '\'' +
                ", location='" + location + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", photographTime=" + photographTime +
                ", createTime=" + createTime +
                ", version=" + version +
                ", type=" + type +
                '}';
    }
}
