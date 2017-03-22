package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class PortraitSetRequest {
    @ApiModelProperty(value = "需要设置该Identifier的资料")
    @JsonProperty(value = "From_Account")
    private String fromAccount;
    @ApiModelProperty(value = "待设置的用户的资料对象数组，数组中每一个对象都包含了Tag和Value")
    @JsonProperty(value = "ProfileItem")
    private List<ProfileItem> profileItem;

    public PortraitSetRequest(){}

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public List<ProfileItem> getProfileItem() {
        return profileItem;
    }

    public void setProfileItem(List<ProfileItem> profileItem) {
        this.profileItem = profileItem;
    }

    public PortraitSetRequest(String fromAccount, List<ProfileItem> profileItem) {
        this.fromAccount = fromAccount;
        this.profileItem = profileItem;
    }

    @Override
    public String toString() {
        return "PortraitSetRequest{" +
                "fromAccount='" + fromAccount + '\'' +
                ", profileItem=" + profileItem +
                '}';
    }
}
