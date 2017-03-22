/*
 * YahooWeather.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-06-23 Created by wendy
 */
package com.umessage.letsgo.domain.po.system;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class YahooWeather  implements Serializable {

    private Long id;
    /**
     * 城市名
     */
    @ApiModelProperty(value="城市名")
    private String name;
    /**
     * 城市中文名
     */
    @ApiModelProperty(value="城市中文名")
    private String cityName;
    /**
     * 最高温度
     */
    @ApiModelProperty(value="最高温度")
    private String high;
    /**
     * 最低温度
     */
    @ApiModelProperty(value="最低温度")
    private String low;
    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
    private String date;
    /**
     * 星期
     */
    @ApiModelProperty(value="星期")
    private String day;
    /**
     * 天气描述码
     */
    @ApiModelProperty(value="天气描述码")
    private String code;
    /**
     * 天气描述
     */
    @ApiModelProperty(value="天气描述")
    private String text;
    /**
     * 天气中文描述
     */
    @ApiModelProperty(value="天气中文描述")
    private String descn;
    /**
     * 最新更新时间
     */
    @ApiModelProperty(value="最新更新时间")
    private Date updateTime;
    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high == null ? null : high.trim();
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low == null ? null : low.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn == null ? null : descn.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "YahooWeather{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cityName='" + cityName + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", code='" + code + '\'' +
                ", text='" + text + '\'' +
                ", descn='" + descn + '\'' +
                ", updateTime=" + updateTime +
                ", version=" + version +
                '}';
    }

}