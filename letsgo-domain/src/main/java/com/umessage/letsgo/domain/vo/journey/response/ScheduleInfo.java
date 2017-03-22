package com.umessage.letsgo.domain.vo.journey.response;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wendy on 2016/6/16.
 */
public class ScheduleInfo implements Serializable {
    @ApiModelProperty(value = "团队名称")
    private String teamName;
    @ApiModelProperty(value = "团号")
    private String teamNumber;
    @ApiModelProperty(value = "出发地")
    private String startPlace;
    @ApiModelProperty(value = "出发时间")
    private Date startDate;
    @ApiModelProperty(value = "集合时间")
    private Date collectionTime;
    @ApiModelProperty(value = "集合地点")
    private String collectionPlace;

    // H5链接及参数
    @ApiModelProperty(value = "H5行程链接")
    private String targetURL;
    @ApiModelProperty(value = "链接参数flag")
    private String flag;
    @ApiModelProperty(value = "链接参数teamId")
    private String teamId;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getCollectionPlace() {
        return collectionPlace;
    }

    public void setCollectionPlace(String collectionPlace) {
        this.collectionPlace = collectionPlace;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
