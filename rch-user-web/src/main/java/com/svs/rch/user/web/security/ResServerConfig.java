package com.svs.rch.user.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/user/register").permitAll().antMatchers("/user/**").authenticated();

	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

		resources.resourceId("resource_id");
	}
}