/*
 * AirportEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-15 Created by wendy
 */
package com.umessage.letsgo.domain.po.system;

import io.swagger.annotations.ApiModelProperty;

public class AirportEntity {

    /**
     * 航班号
     */
    @ApiModelProperty(value="航班号")
    private String fightNo;
    /**
     * 出发城市三字码
     */
    @ApiModelProperty(value="出发城市三字码")
    private String fromCode;
    /**
     * 出发城市中文
     */
    @ApiModelProperty(value="出发城市中文")
    private String fromName;
    /**
     * to_code
     */
    @ApiModelProperty(value="to_code")
    private String toCode;
    private String toName;
    /**
     * 经停城市1三字代码
     */
    @ApiModelProperty(value="经停城市1三字代码")
    private String stopCode1;
    /**
     * 经停城市1中文
     */
    @ApiModelProperty(value="经停城市1中文")
    private String stopName1;
    /**
     * 经停城市2三字代码
     */
    @ApiModelProperty(value="经停城市2三字代码")
    private String stopCode2;
    /**
     * 经停城市2中文
     */
    @ApiModelProperty(value="经停城市2中文")
    private String stopName2;
    /**
     * 起飞时间
     */
    @ApiModelProperty(value="起飞时间")
    private String takeoffTime;
    /**
     * 到达时间
     */
    @ApiModelProperty(value="到达时间")
    private String arrivelTime;
    /**
     * 航空公司代码
     */
    @ApiModelProperty(value="航空公司代码")
    private String airlineCode;
    /**
     * 航空公司中文
     */
    @ApiModelProperty(value="航空公司中文")
    private String airlineName;
    /**
     * 经停状态
     */
    @ApiModelProperty(value="经停状态")
    private String isStop;

    public String getFightNo() {
        return fightNo;
    }

    public void setFightNo(String fightNo) {
        this.fightNo = fightNo == null ? null : fightNo.trim();
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode == null ? null : fromCode.trim();
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName == null ? null : fromName.trim();
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode == null ? null : toCode.trim();
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName == null ? null : toName.trim();
    }

    public String getStopCode1() {
        return stopCode1;
    }

    public void setStopCode1(String stopCode1) {
        this.stopCode1 = stopCode1 == null ? null : stopCode1.trim();
    }

    public String getStopName1() {
        return stopName1;
    }

    public void setStopName1(String stopName1) {
        this.stopName1 = stopName1 == null ? null : stopName1.trim();
    }

    public String getStopCode2() {
        return stopCode2;
    }

    public void setStopCode2(String stopCode2) {
        this.stopCode2 = stopCode2 == null ? null : stopCode2.trim();
    }

    public String getStopName2() {
        return stopName2;
    }

    public void setStopName2(String stopName2) {
        this.stopName2 = stopName2 == null ? null : stopName2.trim();
    }

    public String getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(String takeoffTime) {
        this.takeoffTime = takeoffTime == null ? null : takeoffTime.trim();
    }

    public String getArrivelTime() {
        return arrivelTime;
    }

    public void setArrivelTime(String arrivelTime) {
        this.arrivelTime = arrivelTime == null ? null : arrivelTime.trim();
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode == null ? null : airlineCode.trim();
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName == null ? null : airlineName.trim();
    }

    public String getIsStop() {
        return isStop;
    }

    public void setIsStop(String isStop) {
        this.isStop = isStop == null ? null : isStop.trim();
    }
}