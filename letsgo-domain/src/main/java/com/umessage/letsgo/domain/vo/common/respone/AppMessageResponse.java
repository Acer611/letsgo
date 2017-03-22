package com.umessage.letsgo.domain.vo.common.respone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.umessage.letsgo.domain.vo.common.request.ParameMsg;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lizhen on 2016/11/29.
 */
public class AppMessageResponse implements Serializable {
    @JsonProperty(value = "scheduleName")
    private String scheduleName;

    @JsonProperty(value = "date")
    private long date;

    //下個頁面的主題（收到推送後要跳轉的頁面）
    @JsonProperty(value = "subject")
    private String subject;

    @JsonProperty(value = "desc")
    private String desc;

    @JsonProperty(value = "pictureUrl")
    private String pictureUrl;

    @JsonProperty(value = "msg")
    private List<CustomMsg> msg;

    @JsonProperty(value = "bottom")
    private String bottom;

    @JsonProperty(value = "targetUrl")
    private String targetUrl;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "flag")
    private String flag;

    @JsonProperty(value = "msgType")
    private Integer msgType;

    @JsonProperty(value = "scheduleDetaildId")
    private Long scheduleDetaildId;

    @JsonProperty(value = "teamId")
    private String teamId;

    @JsonProperty(value = "parameMsg")
    private List<ParameMsg> parameMsg;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<CustomMsg> getMsg() {
        return msg;
    }

    public void setMsg(List<CustomMsg> msg) {
        this.msg = msg;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
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

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<ParameMsg> getParameMsg() {
        return parameMsg;
    }

    public void setParameMsg(List<ParameMsg> parameMsg) {
        this.parameMsg = parameMsg;
    }

    @Override
    public String toString() {
        return "AppMessageResponse{" +
                "scheduleName='" + scheduleName + '\'' +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", msg=" + msg +
                ", bottom='" + bottom + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", flag='" + flag + '\'' +
                ", msgType=" + msgType +
                ", scheduleDetaildId=" + scheduleDetaildId +
                ", teamId='" + teamId + '\'' +
                ", parameMsg=" + parameMsg +
                '}';
    }
}
