package com.xiaomi.vo;

import java.util.Map;

/**
 * Created by wendy on 2016/7/4.
 */
public class MiPushRequest {
    private String title;   // 通知的标题
    private String content; // 通知的描述
    private String payload; // 消息内容payload
    private int passThrough;    // 是否通过透传的方式
    private Integer type;   // 通知类型
    private String packageName; // 安卓包名
    private Map<String, String> extra;  // 扩展字段
    private String url; // IOS铃声URL

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

    @Override
    public String toString() {
        return "MiPushRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", payload='" + payload + '\'' +
                ", passThrough=" + passThrough +
                ", type=" + type +
                ", packageName='" + packageName + '\'' +
                ", extra=" + extra +
                ", url='" + url + '\'' +
                '}';
    }
}
