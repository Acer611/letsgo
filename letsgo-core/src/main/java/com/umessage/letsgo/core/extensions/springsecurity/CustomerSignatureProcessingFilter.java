package com.umessage.letsgo.core.extensions.springsecurity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import com.umessage.letsgo.core.config.constant.ConfigConstant;
import com.umessage.letsgo.core.exception.EmptyClientException;
import com.umessage.letsgo.core.exception.EmptySignException;
import com.umessage.letsgo.core.exception.SignatureException;
import com.umessage.letsgo.core.extensions.springsecurity.constant.SecurityConstant;
import com.umessage.letsgo.core.extensions.springsecurity.utils.SignUtil;

public class CustomerSignatureProcessingFilter implements Filter, InitializingBean {

	private final static Log logger = LogFactory.getLog(CustomerSignatureProcessingFilter.class);
	
	private AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();

	private ClientDetailsService clientDetailsService;

	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state(clientDetailsService != null, "clientDetailsService is required");
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		final boolean debug = logger.isDebugEnabled();
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;		

		String clientId = request.getParameter(SecurityConstant.APP_KEY_NAME);
		try {
			if (StringUtils.isEmpty(clientId)){
				throw new EmptyClientException(SecurityConstant.APP_KEY_NAME + "参数不能为空");
			}
			String clientSecret = loadClientSecretById(clientId);
			
			if (!signVerification(request, clientSecret)) {
				throw new SignatureException("签名验证失败");
			}
		}
		catch (OAuth2Exception failed) {
			SecurityContextHolder.clearContext();

			if (debug) {
				logger.debug("Authentication request failed: " + failed);
			}
			authenticationEntryPoint.commence(request, response,
					new InsufficientAuthenticationException(failed.getMessage(), failed));

			return;
		}

		chain.doFilter(request, response);		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	public String loadClientSecretById(String clientId) {
		ClientDetails clientDetails;
		try {
			clientDetails = clientDetailsService.loadClientByClientId(clientId);
		} catch (NoSuchClientException e) {
			throw new InvalidClientException(e.getMessage());
		}
		String clientSecret = clientDetails.getClientSecret();
		if (clientSecret== null || clientSecret.trim().length()==0) {
			clientSecret = "";
		}
		return clientSecret;
	}	
	
	public static boolean signVerification(HttpServletRequest request, String clientSecret) {
		String sign = request.getParameter(SecurityConstant.APP_SIGN_NAME);
		if (StringUtils.isEmpty(sign)){
			throw new EmptySignException(SecurityConstant.APP_SIGN_NAME + "参数不能为空");
		}
		
		Map<String, String[]> parameterMap = request.getParameterMap();

		Map<String, String> paramValues = getParamValues(parameterMap);

		List<String> ignoreParamNames = new ArrayList<String>();
		ignoreParamNames.add(SecurityConstant.APP_SIGN_NAME);
		HashMap<String, String> signMap = SignUtil.sign(paramValues, ignoreParamNames,
				clientSecret);
		String appSign = signMap.get("appSign");
		if (!sign.equals(appSign)) {
			if ("true".equals(ConfigConstant.RETURN_APP_SIGN)) {
				String appParam = signMap.get("appParam");
				throw new SignatureException("签名验证失败，sign: " + sign
						+ ", appSign:" + appSign + ", appParam:" + appParam);
			} else {
				throw new SignatureException("签名验证失败");
			}
			// return false;
		}

		return true;
	}

	private static Map<String, String> getParamValues(Map<String, String[]> parameterMap) {
		Map<String, String> paramValues = new HashMap<String, String>();
		Iterator iter = parameterMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String[] val = (String[]) entry.getValue();
			String value = "";
			if (val != null) {
				for (int i = 0; i < val.length; i++) {
					value = val[i];
					paramValues.put(key, value);
				}
			} else {
				paramValues.put(key, value);
			}
		}
		return paramValues;
	}

}
