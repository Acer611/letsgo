package com.umessage.letsgo.domain.vo.security.request;

import java.io.Serializable;

public class CheckCodeRequest implements Serializable {
    private String phoneNumber;

    private String verifyCode;
    
    private String targetUrl;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	@Override
	public String toString() {
	    return "CheckCodeRequest {phoneNumber=" + phoneNumber + ", targetUrl=" + targetUrl
		    + ", verifyCode=" + verifyCode + "}";
	}
    
}
