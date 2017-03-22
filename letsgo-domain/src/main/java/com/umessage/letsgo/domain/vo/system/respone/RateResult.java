package com.umessage.letsgo.domain.vo.system.respone;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wendy on 2016/6/13.
 */
public class RateResult implements Serializable {
    @ApiModelProperty(value = "币种三字码")
    private String code;
    @ApiModelProperty(value = "币种名称")
    private String name;
    @ApiModelProperty(value = "国旗地址")
    private String nationflagurl;
    @ApiModelProperty(value = "汇率，以美元为准基数")
    private Double rate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationflagurl() {
        return nationflagurl;
    }

    public void setNationflagurl(String nationflagurl) {
        this.nationflagurl = nationflagurl;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
