package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qq.tim.vo.response.CommonResponse;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class SendGroupMsgRequest extends CommonResponse {
    @JsonProperty(value = "GroupId")
    private String groupId; // 群组ID

    @JsonProperty(value = "Random")
    private Integer random; //32位随机数，五分钟数字相同认为是重复消息

    @JsonProperty(value = "MsgBody")
    private List<MsgBody> msgBody;  // 消息体

    @JsonProperty(value = "From_Account")
    private String fromAccount; // 消息来源帐号，选填。如果不填写该字段，则默认消息的发送者为调用该接口时使用的APP管理员帐号。

    public SendGroupMsgRequest() {}

    public SendGroupMsgRequest(String groupId, Integer random, List<MsgBody> msgBody) {
        this.groupId = groupId;
        this.random = random;
        this.msgBody = msgBody;
    }

    public SendGroupMsgRequest(String groupId, Integer random, List<MsgBody> msgBody, String fromAccount) {
        this.groupId = groupId;
        this.random = random;
        this.msgBody = msgBody;
        this.fromAccount = fromAccount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getRandom() {
        return random;
    }

    public void setRandom(Integer random) {
        this.random = random;
    }

    public List<MsgBody> getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(List<MsgBody> msgBody) {
        this.msgBody = msgBody;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"groupId\":\"" + groupId + "\""
                + ",                         \"random\":\"" + random + "\""
                + ",                         \"msgBody\":" + msgBody
                + ",                         \"fromAccount\":\"" + fromAccount + "\""
                + "}";
    }
}
