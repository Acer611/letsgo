package com.umessage.letsgo.domain.vo.journey.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoYidong on 2016/5/31.
 *
 */
public class CreateScheduleRequest implements Serializable {

    @ApiModelProperty(value = "行程开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @ApiModelProperty(value = "行程结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @ApiModelProperty(value = "团号")
    private String teamNum;

    @ApiModelProperty(value = "领队id")
    @JsonIgnore
    private Long leaderId;

    @ApiModelProperty(value = "行程名称")
    private String name;
    /**
     * 出发地
     */
    @ApiModelProperty(value="出发地")
    private String startPlace;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
