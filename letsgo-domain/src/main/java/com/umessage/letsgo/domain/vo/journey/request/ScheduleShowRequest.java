package com.umessage.letsgo.domain.vo.journey.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZhaoYidong on 2016/6/8.
 */
public class ScheduleShowRequest implements Serializable {

    @ApiModelProperty(value = "腾迅云组id")
    private String teamId;

    @ApiModelProperty(value = "行程详细id")
    private Long scheduleDetaildId;

    @ApiModelProperty(value = "H5调用Controller的返回页面标识")
    private String flag;

    private String hotelId; // 酒店ID
    private Long mafengId;  // 景点ID

    @ApiModelProperty(value = "行程id")
    private Long scheduleId;

    @ApiModelProperty(value = "变更日期")
    private Date scheduleDate;


    private Long scenicId;
    //私有酒店id
    private Long hotelScheduleId;
    //其他说明id
    private Long descId;

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Long getScheduleDetaildId() {
        return scheduleDetaildId;
    }

    public void setScheduleDetaildId(Long scheduleDetaildId) {
        this.scheduleDetaildId = scheduleDetaildId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public Long getMafengId() {
        return mafengId;
    }

    public void setMafengId(Long mafengId) {
        this.mafengId = mafengId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Long getHotelScheduleId() {
        return hotelScheduleId;
    }

    public void setHotelScheduleId(Long hotelScheduleId) {
        this.hotelScheduleId = hotelScheduleId;
    }

    public Long getDescId() {
        return descId;
    }

    public void setDescId(Long descId) {
        this.descId = descId;
    }
}
