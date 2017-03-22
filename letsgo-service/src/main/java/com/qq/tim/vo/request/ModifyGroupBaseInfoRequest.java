package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by wendy on 2016/6/8.
 */
public class ModifyGroupBaseInfoRequest {
    @JsonProperty(value = "GroupId")
    private String groupId; // 群组ID

    @JsonProperty(value = "Notification")
    private String notification;    // 群公告

    @JsonProperty(value = "AppDefinedData")
    private List<AppDefinedData> appDefinedData;    // 群组维度的自定义字段

    @JsonProperty(value = "FaceUrl")
    private String faceUrl;    // 群头像
    public ModifyGroupBaseInfoRequest() {}

    public ModifyGroupBaseInfoRequest(String groupId, String notification, List<AppDefinedData> appDefinedData) {
        this.groupId = groupId;
        this.notification = notification;
        this.appDefinedData = appDefinedData;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public List<AppDefinedData> getAppDefinedData() {
        return appDefinedData;
    }

    public void setAppDefinedData(List<AppDefinedData> appDefinedData) {
        this.appDefinedData = appDefinedData;
    }
}
