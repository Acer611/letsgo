package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class MemberList {
    @JsonProperty(value = "Member_Account")
    private String memberAccount;
    @JsonProperty(value = "Role")
    private String role;
    @JsonProperty(value = "Result")
    private Integer result;
    @JsonProperty(value = "AppMemberDefinedData")
    private List<AppMemberDefinedData> appMemberDefinedData;

    public MemberList() {}

    public MemberList(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public MemberList(String memberAccount, String role) {
        this.memberAccount = memberAccount;
        this.role = role;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<AppMemberDefinedData> getAppMemberDefinedData() {
        return appMemberDefinedData;
    }

    public void setAppMemberDefinedData(List<AppMemberDefinedData> appMemberDefinedData) {
        this.appMemberDefinedData = appMemberDefinedData;
    }

}
