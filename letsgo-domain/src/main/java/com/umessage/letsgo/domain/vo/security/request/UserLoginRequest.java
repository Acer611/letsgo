package com.umessage.letsgo.domain.vo.security.request;

import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

public class UserLoginRequest extends CommonRequest{
	/**
	 * 登录账户（可以是手机号，可以是邮箱，也可以是用户名）
	 */
	@ApiModelProperty(value="登录账户（可以是手机号，可以是邮箱，也可以是用户名）")
	private String loginAccount;
	/**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String password;
	/**
	 * 登录方式，1：密码；2：动态验证码；3，微信token
	 */
	@ApiModelProperty(value="登录方式，1：密码；2：动态验证码；3，微信token")
	private String loginType;

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Override
	public String toString() {
		return "UserLoginRequest{" +
				"loginAccount='" + loginAccount + '\'' +
				", password='" + password + '\'' +
				", loginType='" + loginType + '\'' +
				'}';
	}
}
