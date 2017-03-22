package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by wendy on 2016/6/8.
 */
public class ModifyGroupMemberInfoRequest {
    @JsonProperty(value = "GroupId")
    private String groupId; // 操作的群ID

    @JsonProperty(value = "Member_Account")
    private String memberAccount;   // 要操作的群成员

    @JsonProperty(value = "AppMemberDefinedData")
    private List<AppMemberDefinedData> appMemberDefinedData;    // 群成员维度的自定义字段

    @JsonProperty(value = "NameCard")
    private String nameCard; // //群名片

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public ModifyGroupMemberInfoRequest() {}

    public ModifyGroupMemberInfoRequest(String groupId, String memberAccount, List<AppMemberDefinedData> appMemberDefinedData) {
        this.groupId = groupId;
        this.memberAccount = memberAccount;
        this.appMemberDefinedData = appMemberDefinedData;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public List<AppMemberDefinedData> getAppMemberDefinedData() {
        return appMemberDefinedData;
    }

    public void setAppMemberDefinedData(List<AppMemberDefinedData> appMemberDefinedData) {
        this.appMemberDefinedData = appMemberDefinedData;
    }

    @Override
    public String toString() {
        return "ModifyGroupMemberInfoRequest{" +
                "groupId='" + groupId + '\'' +
                ", memberAccount='" + memberAccount + '\'' +
                ", appMemberDefinedData=" + appMemberDefinedData +
                '}';
    }
}
