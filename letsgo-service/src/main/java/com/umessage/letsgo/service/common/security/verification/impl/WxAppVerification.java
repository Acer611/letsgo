package com.umessage.letsgo.service.common.security.verification.impl;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.extensions.springsecurity.social.WxAppVerificationAuthenticationToken;
import com.umessage.letsgo.domain.vo.security.respone.WeixinUserInfoResponse;
import com.umessage.letsgo.service.common.security.UserAccountUserDetailsService;
import com.umessage.letsgo.service.common.security.verification.IVerification;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class WxAppVerification implements IVerification {
    private final static Logger logger = LoggerFactory.getLogger(WxAppVerification.class);
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private UserAccountUserDetailsService userAccountUserDetailsService;

    public UserAccountUserDetailsService getUserAccountUserDetailsService() {
        return userAccountUserDetailsService;
    }

    public void setUserAccountUserDetailsService(UserAccountUserDetailsService userAccountUserDetailsService) {
        this.userAccountUserDetailsService = userAccountUserDetailsService;
    }

    public void verificationAuthenticationChecks(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (username == null) {
            logger.debug("Authentication failed: no username provided");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad username"), username);
        }
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "no token"), username);
        }
        //用户的token
        String accessToken = authentication.getCredentials().toString();

        // 根据openId和用户accessToken获取微信用户信息
        WeixinUserInfoResponse response = userAccountUserDetailsService.getWxAppUserInfo(accessToken, username);
        if (response == null || StringUtils.isEmpty(response.getUnionid())) {
            throw new BusinessException(ErrorConstant.THIRD_ACCOUNT_NOT_FOUND, "未找到该微信用户的信息");
        }
        ((WxAppVerificationAuthenticationToken)authentication).setUnionId(response.getUnionid());
    }

    public final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "no token"), username);
        }
        // 微信的unionID
        String unionId = ((WxAppVerificationAuthenticationToken)authentication).getUnionId();
        UserDetails loadedUser;
        try {
            loadedUser = userAccountUserDetailsService.loadUserByWxUnionId(unionId);
        } catch (BusinessException e) {
            logger.debug(e.getMessage());
            throw e;
        } catch (Exception repositoryProblem) {
            logger.debug(repositoryProblem.getMessage());

            throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }
}