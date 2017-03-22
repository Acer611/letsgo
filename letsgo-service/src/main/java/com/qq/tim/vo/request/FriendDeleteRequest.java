package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ZhaoYidong on 2016/7/27.
 */
public class FriendDeleteRequest {
    @ApiModelProperty(value = "需要为该Identifier删除好友")
    @JsonProperty(value = "From_Account")
    private String fromAccount;

    @ApiModelProperty(value = "待删除的好友的Identifier")
    @JsonProperty(value = "To_Account")
    private String toAccount;

    @ApiModelProperty(value = "删除模式，Delete_Type_Single:单向删除好友，Delete_Type_Both：双向删除好友")
    @JsonProperty(value = "DeleteType")
    private String deleteType;



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

    public String getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }
}
