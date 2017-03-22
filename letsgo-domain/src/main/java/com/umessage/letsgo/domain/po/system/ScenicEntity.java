package com.umessage.letsgo.domain.po.system;

import io.swagger.annotations.ApiModelProperty;
import java.util.*;
/**
 * Created by gaofei on 2017/2/16.
 */
public class ScenicEntity {

    @ApiModelProperty(value="私有库的id 公用库没有这个字段")
    private Long id;
    /**
     * 景点的马蜂id
     */
    @ApiModelProperty(value="景点的马蜂id 私有库没有此字段")
    private long mafengId;


    /**
     * 所属目的地
     */
    @ApiModelProperty(value="所属目的地")
    private String destionationId;
    /**
     * 简介信息
     */
    @ApiModelProperty(value="简介信息")
    private String brief;
    /**
     * 城市名称
     */
    @ApiModelProperty(value="城市")
    private String city;

    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private String lat;
    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度")
    private String lng;


    @ApiModelProperty(value="英文名字")
    private String enName;

    @ApiModelProperty(value="景点中文名")
    private String Name;


    @ApiModelProperty(value="景点照片")
    private List<String> imageList;

    public String getDestionationId() {
        return destionationId;
    }

    public void setDestionationId(String destionationId) {
        this.destionationId = destionationId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public long getMafengId() {
        return mafengId;
    }

    public void setMafengId(long mafengId) {
        this.mafengId = mafengId;
    }
}
