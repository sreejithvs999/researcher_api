package com.svs.rch.user.web.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

//@EnableResourceServer
public class ResServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.anonymous().disable().authorizeRequests().antMatchers("/user/**").authenticated()
				.antMatchers("/user/register").permitAll().and().exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler());

	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

		resources.resourceId("resource_id");
	}
}
