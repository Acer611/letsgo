package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2016/4/29.
 */
public class MsgBody {
    @ApiModelProperty(value = "消息元素类别")
    @JsonProperty(value = "MsgType")
    private String msgType;
    @ApiModelProperty(value = "消息元素的内容")
    @JsonProperty(value = "MsgContent")
    private MsgContent msgContent;

    public MsgBody() {
    }

    public MsgBody(String msgType, MsgContent msgContent) {
        this.msgType = msgType;
        this.msgContent = msgContent;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public MsgContent getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(MsgContent msgContent) {
        this.msgContent = msgContent;
    }

    @Override
    public String toString() {
        return "MsgBody{" +
                "msgType='" + msgType + '\'' +
                ", msgContent=" + msgContent +
                '}';
    }
}
