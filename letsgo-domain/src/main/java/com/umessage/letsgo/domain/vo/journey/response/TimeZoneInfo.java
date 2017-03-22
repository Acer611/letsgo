package com.umessage.letsgo.domain.vo.journey.response;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/29.
 */
public class TimeZoneInfo implements Serializable {
    /**
     * 时区ID
     */
    @ApiModelProperty(value="时区ID，类似于：Asia/Shanghai，Europe/Rome")
    private String timeZoneId;

    /**
     * 行程目的地
     */
    @ApiModelProperty(value="行程目的地")
    private String destination;

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "TimeZoneInfo{" +
                "timeZoneId='" + timeZoneId + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeZoneInfo that = (TimeZoneInfo) o;

        return timeZoneId.equals(that.timeZoneId) && destination.equals(that.destination);

    }

    @Override
    public int hashCode() {
        String result = timeZoneId + destination;
        return result.hashCode();
    }
}
