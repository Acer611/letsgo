package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class CreateGroupRequest {
    @ApiModelProperty(value = "群主id，自动添加到群成员中。如果不填，群没有群主")
    @JsonProperty(value = "Owner_Account")
    private String ownerAccount;
    @ApiModelProperty(value = "群类型, 包括Public(公开群), Private(私密群), ChatRoom(聊天室)")
    @JsonProperty(value = "Type")
    private String type;
    @ApiModelProperty(value = "群名称")
    @JsonProperty(value = "Name")
    private String name;
    @ApiModelProperty(value = "群头像URL")
    @JsonProperty(value = "FaceUrl")
    private String faceUrl;
    @ApiModelProperty(value = "群组维度的自定义字段")
    @JsonProperty(value = "AppDefinedData")
    private List<AppDefinedData> appDefinedData;
    @ApiModelProperty(value = "初始群成员列表，最多500个")
    @JsonProperty(value = "memberList")
    private List<MemberList> memberList;

    public CreateGroupRequest() {}

    public CreateGroupRequest(String ownerAccount, String type, String name, String faceUrl) {
        this.ownerAccount = ownerAccount;
        this.type = type;
        this.name = name;
        this.faceUrl = faceUrl;
    }

    public CreateGroupRequest(String ownerAccount, String type, String name, String faceUrl, List<AppDefinedData> appDefinedData, List<MemberList> memberList) {
        this.ownerAccount = ownerAccount;
        this.type = type;
        this.name = name;
        this.faceUrl = faceUrl;
        this.appDefinedData = appDefinedData;
        this.memberList = memberList;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public List<AppDefinedData> getAppDefinedData() {
        return appDefinedData;
    }

    public void setAppDefinedData(List<AppDefinedData> appDefinedData) {
        this.appDefinedData = appDefinedData;
    }

    public List<MemberList> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberList> memberList) {
        this.memberList = memberList;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"ownerAccount\":\"" + ownerAccount + "\""
                + ",                         \"type\":\"" + type + "\""
                + ",                         \"name\":\"" + name + "\""
                + ",                         \"faceUrl\":\"" + faceUrl + "\""
                + ",                         \"appDefinedData\":" + appDefinedData
                + ",                         \"memberList\":" + memberList
                + "}";
    }
}
