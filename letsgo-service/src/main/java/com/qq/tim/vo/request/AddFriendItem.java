package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wendy on 2016/6/14.
 */
public class AddFriendItem {
    @JsonProperty(value = "To_Account")
    private String toAccount;
    @JsonProperty(value = "AddSource")
    private String addSource;

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getAddSource() {
        return addSource;
    }

    public void setAddSource(String addSource) {
        this.addSource = addSource;
    }
}
