package com.umessage.letsgo.h5.common;

import com.google.common.base.Predicate;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
//@EnableWebMvc
@EnableSwagger2
//@ComponentScan(basePackages = "com.umessage.letsgo.openapi")
public class CustomSwaggerConfig {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                // .groupName("defualt")
                .select()
                //.paths(PathSelectors.any())
                .paths(paths())
                .build()
                .apiInfo(apiInfo())
                .pathMapping("/") //2
                .directModelSubstitute(LocalDate.class, //3
                        String.class)
                .genericModelSubstitutes(ResponseEntity.class) //4
                .useDefaultResponseMessages(false) //6
                .securitySchemes(securitySchemes()) //8
                .securityContexts(securityContext()) //9
                ;
    }

    private Predicate<String> paths() {
        return or(
                regex("/api.*"));
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("", "", "");
        ApiInfo apiInfo = new ApiInfo(
                "跟上API",
                "<p>为了保护用户数据的安全和隐私，使用OAuth2.0协议来进行用户身份验证和获取用户授权，详细请参考：<a href='oauth2.html' target='_blank'>用户授权接口</a></p><p>API 调用有几个公共参数，详细请参考：<a href='api.html' target='_blank'>API调用说明</a></p>",
                "1.0", "My Apps API terms of service", contact, "", "");
        return apiInfo;
    }

    private List<SecurityScheme> securitySchemes() {
        ArrayList<SecurityScheme> authorizationTypes = new ArrayList<SecurityScheme>();

        /* not sure why scopes are used for both authorization and authorization
         * type
         * API is not using the scopes for now
         */

        List<AuthorizationScope> authorizationScopeList = new ArrayList<AuthorizationScope>();
        authorizationScopeList.add(new AuthorizationScope("captain", "captain resource"));
        authorizationScopeList.add(new AuthorizationScope("guide", "guide resource"));
        authorizationScopeList.add(new AuthorizationScope("tourist", "tourist resource"));
        List<GrantType> grantTypes = new ArrayList<GrantType>();

        /*
        TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint("/login/oauth/token", "client_Id ", "client_secret" );
        TokenEndpoint tokenEndpoint = new TokenEndpoint("/login/oauth/token", "theToken");

        PasswordTokenRequestEndpoint passwordTokenRequestEndpoint = new PasswordTokenRequestEndpoint(
                "/oauth/token", "client_Id ", "client_secret", "user", "password");
        grantTypes.add(new OAuth2PasswordCredentialsGrantType(
                passwordTokenRequestEndpoint));

        grantTypes.add(new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint));
        */

        //grantTypes.add(new ClientCredentialsGrant("/login/oauth/token"));
        //grantTypes.add(new ResourceOwnerPasswordCredentialsGrant("/login/oauth/token"));

        LoginEndpoint loginEndpoint = new LoginEndpoint("/login/oauth/authorize");
        grantTypes.add(new ImplicitGrant(loginEndpoint, "access_token"));

        /* OAuth authorization type with client credentials and password grant
         * types.
         */
        //authorizationTypes.add(new ApiKey("apiKey", "client_id", "header"));
        authorizationTypes.add(new OAuth("letsgo_auth", authorizationScopeList,
                grantTypes));


        return authorizationTypes;
    }

    private List<SecurityContext> securityContext() {
        return Arrays.asList(SecurityContext.builder()
                .securityReferences(SecurityReferences())
                .build());
    }

    private List<SecurityReference> SecurityReferences() {

        List<SecurityReference> authorizations = new ArrayList<SecurityReference>();

        /* use same scopes as above */
        AuthorizationScope[] authorizationScopes = {
                new AuthorizationScope("captain", "captain resource"),
                new AuthorizationScope("guide", "guide resource"),
                new AuthorizationScope("tourist", "tourist resource")};

        /* Currently we have 2 roles - user and client */
        authorizations.add(new SecurityReference("letsgo_auth", authorizationScopes));
        return authorizations;

    }

    private class PasswordTokenRequestEndpoint extends TokenRequestEndpoint {

        private final String _username;
        private final String _password;

        public PasswordTokenRequestEndpoint(String url, String clientIdName, String clientSecretName, String username,
                                            String password) {

            super(url, clientIdName, clientSecretName);
            this._username = username;
            this._password = password;
        }

        @SuppressWarnings("unused")
        public String getUsername() {
            return _username;
        }

        @SuppressWarnings("unused")
        public String getPassword() {
            return _password;
        }
    }

    /**
     * Class used for Oauth2 password grant type
     */
    private class OAuth2PasswordCredentialsGrantType extends GrantType {

        @SuppressWarnings("unused")
        private final PasswordTokenRequestEndpoint _tokenRequestEndpoint;

        public OAuth2PasswordCredentialsGrantType(PasswordTokenRequestEndpoint tokenRequestEndpoint) {
            super("password");
            this._tokenRequestEndpoint = tokenRequestEndpoint;
        }
    }
    
    /*
    @Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(
				"test-app-client-id", 
				"test-app-realm", 
				"test-app",
				"test-app",
				"test-app",
				ApiKeyVehicle.QUERY_PARAM,
				"test-app",
				"apiKey");
	}
    
	@Bean
    SecurityScheme apiKey() {
        return new ApiKey("api_key", "api_key", "header");
    }
    */

    //Here is an example where we select any api that matches one of these
	/*
	paths private Predicate<String> paths() { 
	  return or(regex("/business.*"), regex("/some.*"), regex("/contacts.*"),
			  regex("/pet.*"), regex("/springsRestController.*"), regex("/test.*")
			  ); 
	}
	
	private List<AuthorizationType> getAuthorizationType() {
		List<AuthorizationScope> scopes = new ArrayList<AuthorizationScope>();
		AuthorizationScope s1 = new AuthorizationScope("read", "");
		AuthorizationScope s2 = new AuthorizationScope("write", "");
		scopes.add(s1);
		scopes.add(s2);
		List<GrantType> gTypes = new ArrayList<GrantType>();
		GrantType g1 = new GrantType("password");
		GrantType g2 = new GrantType("client_credentials");
		gTypes.add(g1);
		gTypes.add(g2);
		List<AuthorizationType> authList = new ArrayList<AuthorizationType>();
		AuthorizationType authType = new OAuth(scopes, gTypes);
		authList.add(authType);
		return authList;
	}
	*/
}