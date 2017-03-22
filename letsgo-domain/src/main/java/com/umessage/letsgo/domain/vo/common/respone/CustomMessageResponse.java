package com.umessage.letsgo.domain.vo.common.respone;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wendy on 2016/6/8.
 */
public class CustomMessageResponse implements Serializable {
    @JsonProperty(value = "date")
    private long date;

    @JsonProperty(value = "msg")
    private List<CustomMsg> msg;

    @JsonProperty(value = "msgType")
    private Integer msgType;

    @JsonProperty(value = "targetURL")
    private String targetUrl;

    @JsonProperty(value = "flag")
    private String flag;

    @JsonProperty(value = "scheduleDetaildId")
    private Long scheduleDetaildId;

    @JsonProperty(value = "teamId")
    private String teamId;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<CustomMsg> getMsg() {
        return msg;
    }

    public void setMsg(List<CustomMsg> msg) {
        this.msg = msg;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getScheduleDetaildId() {
        return scheduleDetaildId;
    }

    public void setScheduleDetaildId(Long scheduleDetaildId) {
        this.scheduleDetaildId = scheduleDetaildId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "CustomMessageResponse{" +
                "date=" + date +
                ", msg=" + msg +
                ", msgType=" + msgType +
                ", targetUrl='" + targetUrl + '\'' +
                ", flag='" + flag + '\'' +
                ", scheduleDetaildId=" + scheduleDetaildId +
                ", teamId='" + teamId + '\'' +
                '}';
    }
}
