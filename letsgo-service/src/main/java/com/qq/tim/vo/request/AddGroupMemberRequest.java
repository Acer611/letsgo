package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class AddGroupMemberRequest {
    @ApiModelProperty(value = "要操作的群组id")
    @JsonProperty(value = "GroupId")
    private String groupId;
    @ApiModelProperty(value = "要加入的用户id")
    @JsonProperty(value = "Silence")
    private Integer silence;
    @ApiModelProperty(value = "是否静默加入, 0为否， 1为是")
    @JsonProperty(value = "MemberList")
    private List<MemberList> memberList;

    public AddGroupMemberRequest() {}

    public AddGroupMemberRequest(String groupId, List<MemberList> memberList) {
        this.groupId = groupId;
        this.memberList = memberList;
    }

    public AddGroupMemberRequest(String groupId, Integer silence, List<MemberList> memberList) {
        this.groupId = groupId;
        this.silence = silence;
        this.memberList = memberList;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getSilence() {
        return silence;
    }

    public void setSilence(Integer silence) {
        this.silence = silence;
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
                + "                        \"groupId\":\"" + groupId + "\""
                + ",                         \"silence\":\"" + silence + "\""
                + ",                         \"memberList\":" + memberList
                + "}";
    }
}
