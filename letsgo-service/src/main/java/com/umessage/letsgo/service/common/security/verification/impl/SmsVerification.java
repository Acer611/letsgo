package com.umessage.letsgo.service.common.security.verification.impl;

import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.service.common.security.UserAccountUserDetailsService;
import com.umessage.letsgo.service.common.security.verification.IVerification;
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

public class SmsVerification implements IVerification {
    private final static Logger logger = LoggerFactory.getLogger(SmsVerification.class);
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private UserAccountUserDetailsService userAccountUserDetailsService;

    public UserAccountUserDetailsService getUserAccountUserDetailsService() {
        return userAccountUserDetailsService;
    }

    public void setUserAccountUserDetailsService(UserAccountUserDetailsService userAccountUserDetailsService) {
        this.userAccountUserDetailsService = userAccountUserDetailsService;
    }

    public void verificationAuthenticationChecks(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), username);
        }
        String code = authentication.getCredentials().toString();

        // 对用户输入的验证码进行有效性验证
        if (!userAccountUserDetailsService.isSmsCodeValid(username, code)) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), username);
        }
    }

    public final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;
        try {
            loadedUser = userAccountUserDetailsService.loadUserAndCreateByUsername(username);
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