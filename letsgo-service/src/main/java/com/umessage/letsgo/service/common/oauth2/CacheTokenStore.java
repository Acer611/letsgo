package com.umessage.letsgo.service.common.oauth2;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

public class CacheTokenStore extends JdbcTokenStore {

	public CacheTokenStore(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	@Cacheable(value="oauth2TokenCache", key = "#tokenValue")
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		return super.readAccessToken(tokenValue);
	}

	@Override
	@Cacheable(value="oauth2AuthCache", key = "#token")
	public OAuth2Authentication readAuthentication(String token) {
		return super.readAuthentication(token);
	}

	@Override
	@Cacheable(value="oauth2AuthRefreshCache", key = "#value")
	public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
		return super.readAuthenticationForRefreshToken(value);
	}

	@Override
	@Cacheable(value="oauth2RefreshTokenCache", key = "#token")
	public OAuth2RefreshToken readRefreshToken(String token) {
		return super.readRefreshToken(token);
	}

}
