package com.umessage.letsgo.core.timezone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Created by zhajl on 16/11/11.
 */
public class ApiKeyItem {
    /*
Google Maps Geocoding API Key
 */
    private String apikey;
    /*
    apikey 状态, 0, 不可用, 1可用
     */
    private Integer status;
    /*
    状态不可用的时间
     */
    private DateTime dateTime;

    private String timeZone;

    public ApiKeyItem(String apikey, Integer status, String timeZone){
        this.apikey = apikey;
        this.status = status;
        this.timeZone = timeZone;
        this.dateTime = new DateTime(DateTimeZone.forID(timeZone));
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return "ApiKeyItem{" +
                "apikey='" + apikey + '\'' +
                ", status=" + status +
                ", dateTime=" + dateTime +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
