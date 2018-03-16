package com.svs.rch.user.web.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.core.service.AccountCreationService;
import com.svs.rch.user.web.beans.UserRegisterForm;
import com.svs.rch.user.web.beans.UserRegisterResponse;

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

}
