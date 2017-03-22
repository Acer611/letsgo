package com.qq.tim.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2016/4/28.
 */
public class CommonResponse {
    @JsonProperty(value = "ActionStatus")
    private String actionStatus;
    @JsonProperty(value = "ErrorInfo")
    private String errorInfo;
    @JsonProperty(value = "ErrorCode")
    private Integer errorCode;

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"actionStatus\":\"" + actionStatus + "\""
                + ",                         \"errorInfo\":\"" + errorInfo + "\""
                + ",                         \"errorCode\":\"" + errorCode + "\""
                + "}";
    }
}
