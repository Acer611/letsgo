package com.umessage.letsgo.domain.vo.activity.request;

import java.util.Date;
import java.util.List;

/**
 * Created by wendy on 2016/8/25.
 */
public class LocationHistoryRequest {
    private List<Long> userIds;
    private String date;
    private Date startDate;
    private Date endDate;
    private Double slongitude;
    private Double slatitude;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Double getSlongitude() {
        return slongitude;
    }

    public void setSlongitude(Double slongitude) {
        this.slongitude = slongitude;
    }

    public Double getSlatitude() {
        return slatitude;
    }

    public void setSlatitude(Double slatitude) {
        this.slatitude = slatitude;
    }
}
