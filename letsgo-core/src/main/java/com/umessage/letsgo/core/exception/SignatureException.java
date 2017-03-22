package com.umessage.letsgo.core.exception;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@SuppressWarnings("serial")
public class SignatureException extends OAuth2Exception {

	public SignatureException(String message) {
		super(message);
	}

	public SignatureException(String message, Throwable t) {
		super(message, t);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_sign";
	}
}
