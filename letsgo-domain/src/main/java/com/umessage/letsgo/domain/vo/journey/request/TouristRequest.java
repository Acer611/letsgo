package com.umessage.letsgo.domain.vo.journey.request;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

/**
 * Created by wendy on 2016/8/31.
 */
public class TouristRequest extends CommonRequest {
    private String continent;
    private String destination;
    private String endTime;
    private String startTime;

    private Long travelId;
    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }
}
