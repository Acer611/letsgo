package com.umessage.letsgo.domain.vo.journey.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ZhaoYidong on 2016/6/7.
 */
public class SafeInfoVo implements Serializable {

    /**
     * 安全与防范
     */
    @ApiModelProperty(value="安全与防范")
    private String security;
    /**
     * 特别注意
     */
    @ApiModelProperty(value="特别注意")
    private String specialAttention;


    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getSpecialAttention() {
        return specialAttention;
    }

    public void setSpecialAttention(String specialAttention) {
        this.specialAttention = specialAttention;
    }
}
