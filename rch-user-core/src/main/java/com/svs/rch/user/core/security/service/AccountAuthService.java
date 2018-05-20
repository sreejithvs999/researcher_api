package com.svs.rch.user.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.core.dao.UserBaseDao;
import com.svs.rch.user.core.dbo.UserBase;
import com.svs.rch.user.core.security.RchUserDetails;

@Service
public class AccountAuthService {

	@Autowired
	private UserBaseDao userBaseDao;

	public RchUserBean getUserAccountForLogin(String emailId) {

		List<UserBase> list = userBaseDao.getActiveUserBase(emailId);
		if (list.isEmpty()) {
			return null;
		}

		UserBase source = list.get(0);
		RchUserBean userBean = new RchUserBean();
		userBean.setEmailId(source.getEmailId());
		userBean.setPassword(source.getPassword());
		userBean.setFirstName(source.getFirstName());
		userBean.setLastName(source.getLastName());
		userBean.setMobileNo(source.getMobileNo());
		userBean.setStatus(source.getUserStatus().ordinal());
		userBean.setUserId(source.getUserId());
		userBean.setBirthDate(source.getBirthDate());
		return userBean;
	}
	
	public RchUserBean getLoggedInUser() {
		Authentication currAuthn = SecurityContextHolder.getContext().getAuthentication();
		OAuth2Authentication oauth2 = (OAuth2Authentication) currAuthn;

		RchUserDetails userDetails = (RchUserDetails) oauth2.getUserAuthentication().getPrincipal();
		return userDetails.getUserBean();
	}
}
