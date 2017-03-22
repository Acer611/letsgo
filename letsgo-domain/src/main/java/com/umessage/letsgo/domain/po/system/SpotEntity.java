/*
 * SpotEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-15 Created by wendy
 */
package com.umessage.letsgo.domain.po.system;

import io.swagger.annotations.ApiModelProperty;

public class SpotEntity {

    private Long mafengId;
    private String name;
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
    private String telno;
    private String url;
    /**
     * 交通
     */
    @ApiModelProperty(value="交通")
    private String traffic;
    /**
     * 门票
     */
    @ApiModelProperty(value="门票")
    private String ticket;
    /**
     * 开放时间
     */
    @ApiModelProperty(value="开放时间")
    private String openTime;
    /**
     * 用时参考
     */
    @ApiModelProperty(value="用时参考")
    private String costTime;
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

    private String DBID;

    @ApiModelProperty(value="英文名字")
    private String enName;

    @ApiModelProperty(value="景点拼音")
    private String spotpinyinname;

    @ApiModelProperty(value="温馨小提示")
    private String entrace;

    @ApiModelProperty(value="景点地址")
    private String address;

    @ApiModelProperty(value="营业时间")
    private String businessHours;

    @ApiModelProperty(value="抓取数据来源")
    private String remark;

    public Long getMafengId() {
        return mafengId;
    }

    public void setMafengId(Long mafengId) {
        this.mafengId = mafengId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDestionationId() {
        return destionationId;
    }

    public void setDestionationId(String destionationId) {
        this.destionationId = destionationId == null ? null : destionationId.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno == null ? null : telno.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic == null ? null : traffic.trim();
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime == null ? null : openTime.trim();
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime == null ? null : costTime.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    public String getDBID() {
        return DBID;
    }

    public void setDBID(String DBID) {
        this.DBID = DBID;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getSpotpinyinname() {
        return spotpinyinname;
    }

    public void setSpotpinyinname(String spotpinyinname) {
        this.spotpinyinname = spotpinyinname;
    }

    public String getEntrace() {
        return entrace;
    }

    public void setEntrace(String entrace) {
        this.entrace = entrace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}