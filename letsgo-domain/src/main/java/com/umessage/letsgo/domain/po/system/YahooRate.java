/*
 * YahooRate.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-06-06 Created by wendy
 */
package com.umessage.letsgo.domain.po.system;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class YahooRate  implements Serializable {

    /**
     * 国家ID
     */
    @ApiModelProperty(value="国家ID")
    private String id;
    /**
     * 汇率国家
     */
    @ApiModelProperty(value="汇率国家")
    private String name;
    /**
     * 汇率
     */
    @ApiModelProperty(value="汇率")
    private String rate;
    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
    private String date;
    /**
     * 时间
     */
    @ApiModelProperty(value="时间")
    private String time;
    /**
     * 卖出价
     */
    @ApiModelProperty(value="卖出价")
    private String ask;
    /**
     * 买入价
     */
    @ApiModelProperty(value="买入价")
    private String bid;
    /**
     * 最新更新时间
     */
    @ApiModelProperty(value="最新更新时间")
    private Date lastupdatetime;
    /**
     * 是否删除【0：未删除；1：删除】
     */
    @ApiModelProperty(value="是否删除【0：未删除；1：删除】")
    private Integer isdel;
    /**
     * 批次号
     */
    @ApiModelProperty(value="批次号")
    private Integer batch;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask == null ? null : ask.trim();
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid == null ? null : bid.trim();
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YahooRate yahooRate = (YahooRate) o;

        return id != null ? id.equals(yahooRate.id) : yahooRate.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}