package com.umessage.letsgo.core.extensions.springsecurity.sms;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class SMSVerificationAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public SMSVerificationAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public SMSVerificationAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
