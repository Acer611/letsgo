package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by wendy on 2016/6/14.
 */
public class AddFriendRequest {
    @JsonProperty(value = "From_Account")
    private String fromAccount;
    @JsonProperty(value = "AddFriendItem")
    private List<AddFriendItem> addFriendItem;

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public List<AddFriendItem> getAddFriendItem() {
        return addFriendItem;
    }

    public void setAddFriendItem(List<AddFriendItem> addFriendItem) {
        this.addFriendItem = addFriendItem;
    }
}
