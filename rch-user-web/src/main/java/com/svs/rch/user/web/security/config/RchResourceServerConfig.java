package com.svs.rch.user.web.security.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.Assert;

/**
 * Configuration for requiring authentication for API end points except
 * /register, /activate/email and /login. The token store used is same as the
 * one configured by {@link RchAuthorizationServerConfig} <br/>
 * The oauthClientProcFilter is configured to handle user authentication process
 * and obtain access token for UI.
 * 
 * @author Sreejith VS
 * 
 */
@Configuration
@EnableResourceServer
public class RchResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	Environment env;

	@Override
	public void configure(HttpSecurity http) throws Exception {

		Assert.notNull(oauthClientProcFilter, "oauthClientProcFilter must not be null");

		http.authorizeRequests().antMatchers("/user/register", "/user/activate/email", "/user/login").permitAll()
				.antMatchers("/user/**").authenticated().and()
				.addFilterBefore(oauthClientProcFilter, BasicAuthenticationFilter.class);

	}

	private OAuth2ClientAuthenticationProcessingFilter oauthClientProcFilter;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

		resources.resourceId("resource_all");

		oauthClientProcFilter = new OAuth2ClientAuthenticationProcessingFilter("/user/login");
		DefaultTokenServices tokenService = new DefaultTokenServices();
		tokenService.setTokenStore(resources.getTokenStore());
		oauthClientProcFilter.setContinueChainBeforeSuccessfulAuthentication(true);
		oauthClientProcFilter.setTokenServices(tokenService);
		oauthClientProcFilter.setRestTemplate(restTemplate);

	}

	@Autowired
	OAuth2RestOperations restTemplate;

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public OAuth2RestOperations restTemplate(@Value("#{request}") HttpServletRequest httpReq) {

		DefaultAccessTokenRequest request = new DefaultAccessTokenRequest(httpReq.getParameterMap());

		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails(httpReq),
				new DefaultOAuth2ClientContext(request));
		return restTemplate;
	}

	private ResourceOwnerPasswordResourceDetails resourceDetails(HttpServletRequest httpReq) {

		ResourceOwnerPasswordResourceDetails pswdDetail = new ResourceOwnerPasswordResourceDetails();
		pswdDetail.setClientId(env.getProperty("app.rchuser.client.id"));
		pswdDetail.setClientSecret(env.getProperty("app.rchuser.client.secret"));
		pswdDetail.setAccessTokenUri(env.getProperty("app.rchuser.auth.token.url"));
		pswdDetail.setUsername(httpReq.getParameter("username"));
		pswdDetail.setPassword(httpReq.getParameter("password"));
		return pswdDetail;

	}
}
