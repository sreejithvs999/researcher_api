package com.svs.rch.user.web.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.core.service.AccountCreationService;
import com.svs.rch.user.web.beans.UserEmailActivateForm;
import com.svs.rch.user.web.beans.UserInfoResponse;
import com.svs.rch.user.web.beans.UserRegisterForm;
import com.svs.rch.user.web.beans.UserRegisterResponse;
import com.svs.rch.user.web.security.RchUserDetails;

@Service
public class UserRegistrationService {

	@Autowired
	private AccountCreationService accountCreationService;

	public UserRegisterResponse registerUser(UserRegisterForm registerForm) {

		RchUserBean userBean = new RchUserBean();
		userBean.setEmailId(registerForm.getEmailId());
		userBean.setFirstName(registerForm.getFirstName());
		userBean.setLastName(registerForm.getLastName());
		userBean.setMobileNo(registerForm.getMobileNo());
		userBean.setPassword(registerForm.getPassword());
		userBean.setBirthDate(registerForm.getBirthDate());

		userBean = accountCreationService.createUserAccount(userBean);

		accountCreationService.sendConfirmEmailNotification(userBean);

		return UserRegisterResponse.builder().userId(userBean.getUserId()).build();
	}

	public UserInfoResponse getLoggedInUserInfo() {
		UserInfoResponse response = new UserInfoResponse();
		Authentication currAuthn = SecurityContextHolder.getContext().getAuthentication();
		OAuth2Authentication oauth2 = (OAuth2Authentication) currAuthn;

		RchUserDetails userDetails = (RchUserDetails) oauth2.getUserAuthentication().getPrincipal();
		RchUserBean userBean = userDetails.getUserBean();
		response.setUserId(userBean.getUserId());
		response.setFirstName(userBean.getFirstName());
		response.setLastName(userBean.getLastName());
		response.setEmailId(userBean.getEmailId());
		response.setStatus(userBean.getStatus());
		return response;

	}

	public UserRegisterResponse activateEmailByOTP(UserEmailActivateForm form) {

		RchUserBean userBean = accountCreationService.activateEmailByOTP(form.getEmailId(), form.getOtp());
		accountCreationService.sendEmailActivatedNotification(userBean);

		return UserRegisterResponse.builder().userId(userBean.getUserId()).build();
	}
}
