package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.*;

/**
 * Created by gaofei on 2017/2/15.
 * 每日行程和景点关联关系实体
 */
public class ScheduleDetailScenicEntity {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 旅行社ID
     */
    @ApiModelProperty(value="每日行程的ID")
    private Long scheduleDetailId;
    /**
     * 景点名称
     */
    @ApiModelProperty(value="景点名称")
    private String scenicName;

    /**
     * 景点城市Id
     */
    @ApiModelProperty(value="城市ID")
    private String cityId;
    /**
     * 景点城市
     */
    @ApiModelProperty(value="景点城市")
    private String scenicCity;
    /**
     * 景点简介
     */
    @ApiModelProperty(value="景点简介")
    private String briefintroduction;
    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private String lon;
    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度")
    private String lat;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="图片实体信息")
    private List<AlbumScheduleEntity> albumScheduleEntities;

    //图片列表信息
    private List<String> imageList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleDetailId() {
        return scheduleDetailId;
    }

    public void setScheduleDetailId(Long scheduleDetailId) {
        this.scheduleDetailId = scheduleDetailId;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getScenicCity() {
        return scenicCity;
    }

    public void setScenicCity(String scenicCity) {
        this.scenicCity = scenicCity;
    }

    public String getBriefintroduction() {
        return briefintroduction;
    }

    public void setBriefintroduction(String briefintroduction) {
        this.briefintroduction = briefintroduction;
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

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<AlbumScheduleEntity> getAlbumScheduleEntities() {
        return albumScheduleEntities;
    }

    public void setAlbumScheduleEntities(List<AlbumScheduleEntity> albumScheduleEntities) {
        this.albumScheduleEntities = albumScheduleEntities;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
