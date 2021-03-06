package com.svs.rch.user.web.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.svs.rch.user.core.security.RchJwtConverter;

/**
 * Configurations for authenticating users/ client apps and generating tokens.
 * Using in-memory token store for created token.
 * 
 * @author Sreejith VS
 *
 */
@Configuration
@EnableAuthorizationServer
public class RchAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	DataSource dataSource;

	@Autowired
	RchJwtConverter jwtConverter;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		/**
		 * Configure single client: rch-user-client, which is the application itself.
		 */
		clients.inMemory().withClient("rch-user-client")
				.secret("$2a$10$SCt4wPc0iBjjpycx97Vwo.KV7vsilLHDvqWK0ffTv9O2IqteoJqwW")
				.authorizedGrantTypes("password", "refresh_token", "implicit").scopes("all")
				.accessTokenValiditySeconds(60 * 60 * 24).refreshTokenValiditySeconds(60 * 60 * 24 * 3);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		/**
		 * The authentication manager delegate work flow for emailId+password login
		 * validation, by adding ResourceOwnerPasswordTokenGranter to the list of
		 * granter's.
		 */
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.passwordEncoder(new BCryptPasswordEncoder());

	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints.authenticationManager(authenticationManager).tokenStore(new JwtTokenStore(jwtConverter))
				.tokenEnhancer(jwtConverter);
	}

}
