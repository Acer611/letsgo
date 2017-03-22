package com.umessage.letsgo.domain.vo.system.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wendy on 2016/8/26.
 */
public class CheckRequest {
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "邀请码，如果没有邀请码，则不需要传")
    private String invitationCode;
    @ApiModelProperty(value = "验证码")
    private String captcha;
    @ApiModelProperty(value = "验证码作用范围【1：登录；2：设置密码；3：注册】")
    private Integer captchaType;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

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
}
