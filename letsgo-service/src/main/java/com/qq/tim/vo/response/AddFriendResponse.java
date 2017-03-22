package com.qq.tim.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by wendy on 2016/6/14.
 */
public class AddFriendResponse extends CommonResponse {
    @JsonProperty(value = "ResultItem")
    private List<ResultItem> resultItem;
    @JsonProperty(value = "Fail_Account")
    private List<String> failAccount;
    @JsonProperty(value = "Invalid_Account")
    private List<String> invalidAccount;
    @JsonProperty(value = "ErrorDisplay")
    private String errorDisplay;

    public List<ResultItem> getResultItem() {
        return resultItem;
    }

    public void setResultItem(List<ResultItem> resultItem) {
        this.resultItem = resultItem;
    }

    public List<String> getFailAccount() {
        return failAccount;
    }

    public void setFailAccount(List<String> failAccount) {
        this.failAccount = failAccount;
    }

    public List<String> getInvalidAccount() {
        return invalidAccount;
    }

    public String getErrorDisplay() {
        return errorDisplay;
    }

    public void setErrorDisplay(String errorDisplay) {
        this.errorDisplay = errorDisplay;
    }

    public void setInvalidAccount(List<String> invalidAccount) {
        this.invalidAccount = invalidAccount;

    }
}
