package com.umessage.letsgo.domain.vo.system.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wendy on 2016/9/26.
 */
public class CheckRegisterRequest extends RegisterRequest{
    @ApiModelProperty(value = "校验码")
    private String checkcode;
    @ApiModelProperty(value = "验证码")
    private String captcha;
    @ApiModelProperty(value = "验证码作用范围")
    private Integer captchaType;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Integer getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(Integer captchaType) {
        this.captchaType = captchaType;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
}
