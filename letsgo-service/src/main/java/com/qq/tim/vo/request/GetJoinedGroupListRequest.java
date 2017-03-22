package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2016/4/29.
 */
public class GetJoinedGroupListRequest {
    @JsonProperty(value = "Member_Account")
    private String memberAccount;

    public GetJoinedGroupListRequest() {
    }

    public GetJoinedGroupListRequest(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"memberAccount\":\"" + memberAccount + "\""
                + "}";
    }
}
