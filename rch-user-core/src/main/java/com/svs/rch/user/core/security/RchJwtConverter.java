package com.svs.rch.user.core.security;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.core.security.service.RchUserDetailService;

/**
 * Jwt converter for used in both authorization and resource server.
 * 
 * @author Sreejith VS
 *
 */
@Component
public class RchJwtConverter extends JwtAccessTokenConverter implements InitializingBean {

	@Autowired
	RchUserDetailService userDetailsService;

	public RchJwtConverter() {
		setSigningKey("a30d");
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		super.afterPropertiesSet();

		DefaultAccessTokenConverter conv = (DefaultAccessTokenConverter) getAccessTokenConverter();
		RchUserTokenConverter userConv = new RchUserTokenConverter();
		userConv.setUserDetailsService(userDetailsService);
		conv.setUserTokenConverter(userConv);
	}

	/**
	 * Changes for user details conversion between JSON and Token.
	 * 
	 * @author Sreejith VS
	 *
	 */
	static class RchUserTokenConverter extends DefaultUserAuthenticationConverter {

		@Override
		public Map<String, ?> convertUserAuthentication(Authentication authentication) {

			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) super.convertUserAuthentication(authentication);

			RchUserDetails userDetails = (RchUserDetails) authentication.getPrincipal();
			RchUserBean userBean = userDetails.getUserBean();

			map.put("userId", userBean.getUserId());
			return map;
		}
	}
}
