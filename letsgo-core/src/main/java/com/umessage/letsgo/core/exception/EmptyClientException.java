package com.umessage.letsgo.core.exception;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@SuppressWarnings("serial")
public class EmptyClientException extends OAuth2Exception{

	public EmptyClientException(String message) {
		super(message);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "empty_client";
	}
}
