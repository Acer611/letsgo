package com.umessage.letsgo.domain.vo.system.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wendy on 2016/8/24.
 */
public class RegisterRequest {
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "邀请码，如果没有，可不传")
    private String invitationCode;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "性别")
    private Integer sex;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
