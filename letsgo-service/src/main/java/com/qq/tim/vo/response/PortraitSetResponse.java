package com.qq.tim.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2016/9/30.
 */
public class PortraitSetResponse extends CommonResponse {
    //详细的客户端展示信息。
    @JsonProperty(value = "ErrorDisplay")
    private String errorDisplay;

    public String getErrorDisplay() {
        return errorDisplay;
    }

    public void setErrorDisplay(String errorDisplay) {
        this.errorDisplay = errorDisplay;
    }

    @Override
    public String toString() {
        return "PortraitSetResponse{" +
                "errorDisplay='" + errorDisplay + '\'' +
                '}';
    }
}
