package com.umessage.letsgo.domain.vo.system.respone;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wendy on 2016/6/29.
 */
public class Message implements Serializable {
    @ApiModelProperty(value = "消息内容")
    private String content;
    @ApiModelProperty(value = "消息发送人")
    private String sender;
    @ApiModelProperty(value = "消息发布日期")
    private Date pubdate;
    @ApiModelProperty(value = "消息标题")
    private String title;
    @ApiModelProperty(value="语音地址")
    private String videoUrl;
    @ApiModelProperty(value="语音时长")
    private String videoLen;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoLen() {
        return videoLen;
    }

    public void setVideoLen(String videoLen) {
        this.videoLen = videoLen;
    }
}
