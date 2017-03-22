package com.qq.tim.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qq.tim.vo.request.MemberList;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class AddGroupMemberResponse extends CommonResponse {
    @JsonProperty(value = "MemberList")
    private List<MemberList> memberList;

    public List<MemberList> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberList> memberList) {
        this.memberList = memberList;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"memberList\":" + memberList
                + "}";
    }
}
