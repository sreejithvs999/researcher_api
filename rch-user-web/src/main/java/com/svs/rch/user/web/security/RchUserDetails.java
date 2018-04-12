package com.svs.rch.user.web.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.svs.rch.user.core.beans.RchUserBean;

public class RchUserDetails implements UserDetails {

	private static final long serialVersionUID = -5290085116869970857L;

	private RchUserBean userBean;

	public RchUserDetails(RchUserBean userBean) {
		this.userBean = userBean;
	}

	private Collection<SimpleGrantedAuthority> authorities = Collections
			.singleton(new SimpleGrantedAuthority("ROLE_USER"));

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getPassword() {

		return userBean.getPassword();
	}

	@Override
	public String getUsername() {

		return userBean.getEmailId();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

}
