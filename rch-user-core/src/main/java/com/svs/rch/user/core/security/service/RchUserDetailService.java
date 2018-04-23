package com.svs.rch.user.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.core.security.RchUserDetails;

@Component
public class RchUserDetailService implements UserDetailsService {

	@Autowired
	private AccountAuthService accountAuthService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		RchUserBean userBean = accountAuthService.getUserAccountForLogin(username);

		if (userBean == null) {
			throw new UsernameNotFoundException("Rch User bean data not found for email: " + username);
		}
		RchUserDetails userDetails = new RchUserDetails(userBean);
		return userDetails;
	}
}
