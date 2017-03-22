package com.huawei.vo;

import java.util.Map;

/**
 * Created by pw on 2016/11/1.
 */
public class HuaweiPushRequest {
    private String title;   // 通知的标题
    private String content; // 通知的描述
    private String payload; // 消息内容payload
    private int passThrough;    // 是否通过透传的方式
    private Integer type;   // 通知类型
    private String packageName; // 安卓包名
    private Map<String, String> extra;  // 扩展字段
    private String url; // IOS铃声URL
    private String appId; // 华为appId
    private String appSecret; // 华为appSecret
    private String token; // 用户token
    //1：指定用户，必须指定tokens字段//2：所有人，无需指定tokens，tags，exclude_tags//3：一群人，必须指定tags或者exclude_tags字段
    private Integer pushType; //推送范围，必选
    //标签，可选
    //当push_type的取值为3时，该字段生效
    private String tags;
    //排除的标签，可选
    //当push_type的取值为3时，该字段生效
    private String excludeTags;

    public String getExcludeTags() {
        return excludeTags;
    }

    public void setExcludeTags(String excludeTags) {
        this.excludeTags = excludeTags;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public int getPassThrough() {
        return passThrough;
    }

    public void setPassThrough(int passThrough) {
        this.passThrough = passThrough;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
