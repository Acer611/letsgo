package com.umessage.letsgo.core.extensions.springsecurity.social;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WxAppVerificationAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String unionId;

    public WxAppVerificationAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public WxAppVerificationAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
