package com.umessage.letsgo.core.errorhandler;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.exception.SignatureException;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;

public class OAuth2ExceptionTranslator {
    Logger logger = Logger.getLogger(OAuth2ExceptionTranslator.class);

    public BusinessException translate(Object exception) throws Exception {
        if (exception instanceof BusinessException) {
            return (BusinessException) exception;
        }

        Integer errorCode = ErrorConstant.INTERNAL_SERVER_ERROR;
        String errorMessage = "系统内部异常";
        if (exception instanceof OAuth2Exception) {
            // 显示签名错误提示
            if (exception instanceof SignatureException) {
                errorCode = ErrorConstant.INVALID_SIGN;
                errorMessage = ((SignatureException) exception).getMessage();
            } else {
                String oAuth2ErrorCode = ((OAuth2Exception) exception).getOAuth2ErrorCode();
                return getBusinessException(oAuth2ErrorCode);
            }
        }

        if (exception instanceof BadCredentialsException) {
            errorCode = ErrorConstant.BAD_CREDENTIALS;
            errorMessage = ((BadCredentialsException) exception).getMessage();
        } else if (exception instanceof CredentialsExpiredException) {
            errorCode = ErrorConstant.CREDENTIALS_EXPIRED;
            errorMessage = ((CredentialsExpiredException) exception).getMessage();
        } else if (exception instanceof UnapprovedClientAuthenticationException) {
        }

        BusinessException ex = new BusinessException(errorCode, errorMessage, (Throwable) exception);
        logger.debug("系统捕捉到OAuth2异常！\r\n", ex);
        return ex;
    }

    private BusinessException getBusinessException(String oAuth2ErrorCode) {
        Integer errorCode = ErrorConstant.INTERNAL_SERVER_ERROR;
        String errorMessage = "系统内部异常";

        switch (oAuth2ErrorCode) {
            case "invalid_client":
                errorCode = ErrorConstant.INVALID_CLIENT;
                errorMessage = "invalid_client：client_id或client_secret不正确";
                break;
            case "invalid_grant":
                errorCode = ErrorConstant.INVALID_GRANT;
                errorMessage = "invalid_grant：无效的授权";
                break;
            case "invalid_request":
                errorCode = ErrorConstant.INVALID_REQUEST;
                errorMessage = "invalid_request：请求地址不正确";
                break;
            case "invalid_scope":
                errorCode = ErrorConstant.INVALID_SCOPE;
                errorMessage = "invalid_scope：访问的 scope 不合法";
                break;
            case "invalid_token":
                errorCode = ErrorConstant.INVALID_TOKEN;
                errorMessage = "invalid_token：access_token 不存在或已被用户删除";
                break;
            case "unauthorized_user":
                errorCode = ErrorConstant.UNAUTHORIZED_USER;
                errorMessage = "unauthorized_user：用户未经授权";
                break;
            case "unsupported_grant_type":
                errorCode = ErrorConstant.UNSUPPORTED_GRANT_TYPE;
                errorMessage = "unsupported_grant_type：不支持的 grant_type";
                break;
            case "unsupported_response_type":
                errorCode = ErrorConstant.UNSUPPORTED_RESPONSE_TYPE;
                errorMessage = "unsupported_response_type：不支持的 response_type";
                break;
            case "access_denied":
                errorCode = ErrorConstant.ACCESS_DENIED;
                errorMessage = "access_denied：访问被拒绝";
                break;
            case "server_error":
                errorCode = ErrorConstant.INTERNAL_SERVER_ERROR;
                errorMessage = "系统服务异常";
                break;
            case "unauthorized":
                errorCode = ErrorConstant.UNAUTHORIZED;
                errorMessage = "访问未经授权";
                break;
            case "method_not_allowed":
                errorCode = ErrorConstant.METHOD_NOT_ALLOWED;
                errorMessage = "不允许该操作";
                break;
            case "unauthorized_client":
                errorCode = ErrorConstant.UNAUTHORIZED_CLIENT;
                errorMessage = "不允许该操作";
                break;
            case "invalid_sign":
                errorCode = ErrorConstant.INVALID_SIGN;
                errorMessage = "签名参数不合法";
                break;
            case "empty_client":
                errorCode = ErrorConstant.EMPTY_CLIENT;
                errorMessage = "client_id不能为空";
                break;
            case "empty_sign":
                errorCode = ErrorConstant.EMPTY_SIGN;
                errorMessage = "签名参数为空";
                break;
            default:
                errorCode = ErrorConstant.INTERNAL_SERVER_ERROR;
                errorMessage = "系统服务异常";
        }

        return new BusinessException(errorCode, errorMessage);
    }
}
