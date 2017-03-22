package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by wendy on 2016/6/8.
 */
public class SendMsgRequest {
    @JsonProperty(value = "From_Account")
    private String fromAccount; // 消息发送方账号

    @JsonProperty(value = "To_Account")
    private String toAccount;   // 消息接收方账号

    @JsonProperty(value = "MsgRandom")
    private Integer msgRandom;  // 消息随机数

    @JsonProperty(value = "MsgTimeStamp")
    private Integer msgTimeStamp;   // 消息时间戳

    @JsonProperty(value = "MsgBody")
    private List<MsgBody> msgBody;  // 消息内容

    public SendMsgRequest() {}

    public SendMsgRequest(String fromAccount, String toAccount, Integer msgRandom, Integer msgTimeStamp, List<MsgBody> msgBody) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.msgRandom = msgRandom;
        this.msgTimeStamp = msgTimeStamp;
        this.msgBody = msgBody;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public Integer getMsgRandom() {
        return msgRandom;
    }

    public void setMsgRandom(Integer msgRandom) {
        this.msgRandom = msgRandom;
    }

    public Integer getMsgTimeStamp() {
        return msgTimeStamp;
    }

    public void setMsgTimeStamp(Integer msgTimeStamp) {
        this.msgTimeStamp = msgTimeStamp;
    }

    public List<MsgBody> getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(List<MsgBody> msgBody) {
        this.msgBody = msgBody;
    }

    @Override
    public String toString() {
        return "SendMsgRequest{" +
                "fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", msgRandom=" + msgRandom +
                ", msgTimeStamp=" + msgTimeStamp +
                ", msgBody=" + msgBody +
                '}';
    }
}
