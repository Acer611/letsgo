package com.umessage.letsgo.domain.vo.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomUserAuthenticationToken extends UsernamePasswordAuthenticationToken {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3415346928206743672L;

	private Long userId; // 用户ID
	
	private String userName; // 用户名
	
	private String phoneNumber;// 绑定手机
	
	private String weixinToken; // 微信Token
	
	public CustomUserAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public CustomUserAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWeixinToken() {
		return weixinToken;
	}

	public void setWeixinToken(String weixinToken) {
		this.weixinToken = weixinToken;
	}

	@Override
	public String toString() {
		return "CustomUserAuthenticationToken {phoneNumber=" + phoneNumber + ", userId=" + userId + ", userName="
				+ userName + ", weixinToken=" + weixinToken + "}";
	}


}
