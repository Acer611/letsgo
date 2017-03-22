package com.umessage.letsgo.service.common.helper;

import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.common.security.WxUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Service
public class UserLoginHelper {
	@Resource
	private IUserService userServiceImpl;

	public UserDetails getUserLoginFromContext() {
		UserDetails user = null;

		UsernamePasswordAuthenticationToken userToken = getUsernamePasswordAuthenticationToken();
		if (userToken != null){
			user = userToken2User(userToken);
		}

		return user;
	}

	protected UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken() {
		UsernamePasswordAuthenticationToken userToken = null;
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null || context.getAuthentication() == null) {
			return userToken;
		}

		if(context.getAuthentication() instanceof OAuth2Authentication){
			OAuth2Authentication authentication =  (OAuth2Authentication) context.getAuthentication();
			if (authentication.getUserAuthentication() != null){
				userToken = (UsernamePasswordAuthenticationToken) authentication.getUserAuthentication();
			}
		} else if (context.getAuthentication() instanceof UsernamePasswordAuthenticationToken){
			userToken = (UsernamePasswordAuthenticationToken) context.getAuthentication();
		}
		return userToken;
	}

	protected UserDetails userToken2User(UsernamePasswordAuthenticationToken userToken){
		if(userToken.getPrincipal() != null && userToken.getPrincipal() instanceof UserDetails){
			return (UserDetails) userToken.getPrincipal();
		}

		String username = userToken.getName();
		if (StringUtils.isEmpty(username)){
			return null;
		}
		String password = StringUtils.defaultString(userToken.getPrincipal().toString());
		return new User(username, password, userToken.getAuthorities());
	}

	/**
	 * 获取用户信息，调用该方法强需要调用hasLogin，否则有可能返回异常
	 * @return
	 */
	public UserResponse getLoginUser() {
		UserDetails user = getUserLoginFromContext();
		if (user == null){
			return UserResponse.createUserNotLoginResponse();
		}
		if (isWxUser(user.getAuthorities())){
			return UserResponse.createUserNotLoginResponse();
		}

		UserResponse userResponse = userServiceImpl.getCurrentUser(user.getUsername());
		return userResponse;
	}

	public WxUser getWxUser() {
		UserDetails user = getUserLoginFromContext();
		if (user == null){
			return null;
		}

		if (!isWxUser(user.getAuthorities())){
			return null;
		}
		if(!(user instanceof WxUser)){
			return null;
		}
		return (WxUser)user;
	}

	private boolean isWxUser(Collection<? extends GrantedAuthority> grantedAuthorities){

		for (GrantedAuthority authority :grantedAuthorities
			 ) {
			if ("ROLE_WX_USER".equals(authority.toString())){
				return true;
			}
		}
		return false;
	}
}
