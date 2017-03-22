package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/7/22.
 */
public class DeleteGroupMemberRequest {
    @ApiModelProperty(value = "要操作的群组id")
    @JsonProperty(value = "GroupId")
    private String groupId;

    @ApiModelProperty(value = "是否静默删除 1:是，0:否")
    @JsonProperty(value = "Silence")
    private Integer silence;

    @ApiModelProperty(value = "踢出用户原因")
    @JsonProperty(value = "Reason")
    private String reason;

    @ApiModelProperty(value = "踢出用户账号")
    @JsonProperty(value = "MemberToDel_Account")
    private List<String> memberToDelAccount;


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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getMemberToDelAccount() {
        return memberToDelAccount;
    }

    public void setMemberToDelAccount(List<String> memberToDelAccount) {
        this.memberToDelAccount = memberToDelAccount;
    }
}
