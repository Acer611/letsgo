package com.qq.tim.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wendy on 2016/6/14.
 */
public class ResultItem {
    @JsonProperty(value = "To_Account")
    private String toAccount;
    @JsonProperty(value = "ResultCode")
    private Integer resultCode;
    @JsonProperty(value = "ResultInfo")
    private String resultInfo;

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }
}
