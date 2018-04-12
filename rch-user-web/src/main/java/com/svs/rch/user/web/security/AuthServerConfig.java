package com.svs.rch.user.web.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		//Authorization: Basic cmNoLXVzZXItY2xpZW50OnVzZXIqc2VjcmV0
		clients.inMemory().withClient("rch-user-client").secret("$2a$10$SCt4wPc0iBjjpycx97Vwo.KV7vsilLHDvqWK0ffTv9O2IqteoJqwW")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
				.scopes("read", "write", "trust").accessTokenValiditySeconds(60 * 60 * 3)
				.refreshTokenValiditySeconds(60 * 60 * 24);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
		.passwordEncoder(new BCryptPasswordEncoder());

	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		endpoints.authenticationManager(authenticationManager);
	}

}
