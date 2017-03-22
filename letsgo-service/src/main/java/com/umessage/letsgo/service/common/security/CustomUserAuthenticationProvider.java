package com.umessage.letsgo.service.common.security;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.extensions.springsecurity.sms.SMSVerificationAuthenticationToken;
import com.umessage.letsgo.core.extensions.springsecurity.social.WxAppVerificationAuthenticationToken;
import com.umessage.letsgo.core.extensions.springsecurity.social.WxVerificationAuthenticationToken;
import com.umessage.letsgo.service.common.security.verification.IVerification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.util.Assert;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CustomUserAuthenticationProvider extends DaoAuthenticationProvider implements ApplicationContextAware {

	private ApplicationContext appContext;

	private ClientDetailsService clientDetailsService;

	public ClientDetailsService getClientDetailsService() {
		return clientDetailsService;
	}

	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

	private Object getBean(String paramString)
	{
		return appContext.getBean(paramString);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		// 短信验证码方式登录
		if (SMSVerificationAuthenticationToken.class.isInstance(authentication) ){
			return customAuthenticate(authentication, (IVerification) getBean("smsVerification"));
		}else if (WxAppVerificationAuthenticationToken.class.isInstance(authentication)){
			//微信授权登陆方式
			return customAuthenticate(authentication, (IVerification) getBean("wxAppVerification"));
		}else if (WxVerificationAuthenticationToken.class.isInstance(authentication)){
			//微信授权登陆方式
			return customAuthenticate(authentication, (IVerification) getBean("wxVerification"));
		}else if (UsernamePasswordAuthenticationToken.class.isInstance(authentication)) {
			return super.authenticate(authentication);
		} else {
			return super.authenticate(authentication);
		}
	}

	private Authentication customAuthenticate(Authentication authentication, IVerification verification) throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
				messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports",
						"Only UsernamePasswordAuthenticationToken is supported"));

		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

		boolean cacheWasUsed = true;
		UserDetails user = this.getUserCache().getUserFromCache(username);

		UserAccountUserDetailsService userAccountUserDetailsService  = (UserAccountUserDetailsService)this.getUserDetailsService();

		if (user == null) {
			cacheWasUsed = false;

			try {
				verification.verificationAuthenticationChecks(username, (UsernamePasswordAuthenticationToken) authentication);
				user = verification.retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
			} catch (AuthenticationException exception) {
				logger.debug("User '" + username + "' not found");

				if (hideUserNotFoundExceptions) {
					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
				} else {
					throw exception;
				}
			}

			Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
		}

		try {
			getPreAuthenticationChecks().check(user);
			//additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
		} catch (AuthenticationException exception) {
			if (cacheWasUsed) {
				// There was a problem, so try again after checking
				// we're using latest data (i.e. not from the cache)
				cacheWasUsed = false;
				user = verification.retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
				getPreAuthenticationChecks().check(user);
				//additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
			} else {
				throw exception;
			}
		}

		getPostAuthenticationChecks().check(user);

		if (!cacheWasUsed) {
			this.getUserCache().putUserInCache(user);
		}

		Object principalToReturn = user;

		if (isForcePrincipalAsString()) {
			principalToReturn = user.getUsername();
		}

		// 如果原Authentication中用户名与获取到的UserDetails不一致,则返回UserDetails中用户名
		if (!username.equals(user.getUsername())){
			authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		}

		return createSuccessAuthentication(principalToReturn, authentication, user);
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
		Object details = authentication.getDetails();
		if (details instanceof Map){
			Map<String, String> parameters = (Map<String, String>) details;
			if (parameters != null){
				String clientId = parameters.get(OAuth2Utils.CLIENT_ID);
				if (StringUtils.isNotEmpty(clientId)){
					checkUserScopes(clientId, user);
				}
			}
		}
		return super.createSuccessAuthentication(principal, authentication, user);
	}

	private void checkUserScopes(String clientId, UserDetails user){
		ClientDetails client = clientDetailsService.loadClientByClientId(clientId);
		Set<String> scopes = client.getScope();
		logger.info("scopes" + scopes.toString());
		Set<String> authorities = AuthorityUtils.authorityListToSet(user.getAuthorities());
		logger.info("authorities" + authorities.toString());
		Set<String> result = new LinkedHashSet<String>();
		for (String scope : scopes) {
			if (authorities.contains(scope) || authorities.contains(scope.toUpperCase())
					|| authorities.contains("ROLE_" + scope.toUpperCase())) {
				result.add(scope);
			}
		}
		logger.info("result" + result.toString());

		if (result.isEmpty()){
			throw new BusinessException(ErrorConstant.INSUFFICIENT_PERMISSION, "用户没有的权限");
		}
	}

}
