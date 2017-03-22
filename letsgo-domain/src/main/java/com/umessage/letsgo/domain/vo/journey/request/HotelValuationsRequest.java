package com.umessage.letsgo.domain.vo.journey.request;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoYidong on 2016/5/31.
 *
 */
public class HotelValuationsRequest implements Serializable {


    @ApiModelProperty(value = "行程开始日期")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date startDate;
    @ApiModelProperty(value = "行程结束日期")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date endDate;


    @ApiModelProperty(value = "当前第几页")
    private int pageNum=1;

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    @ApiModelProperty(value = "当前第几页")
    private Long travelId;
    @ApiModelProperty(value = "每页条数")
    private int pageSize=10;


    public String getHotelLevel() {
        return hotelLevel;
    }

    public void setHotelLevel(String hotelLevel) {
        this.hotelLevel = hotelLevel;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }



    public HotelValuationsRequest() {
    }

    public HotelValuationsRequest(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    private String hotelLevel;
    private String continent;
    private String state;
    @Override
    public String toString() {
        return "HotelValuationsRequest{" +
                "continent=" + continent +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", state=" + state +
                ", hotelLevel='" + travelId + '\'' +
                ", hotelLevel='" + hotelLevel + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
