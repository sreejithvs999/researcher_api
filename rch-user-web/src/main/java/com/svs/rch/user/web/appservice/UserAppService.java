package com.svs.rch.user.web.appservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.core.beans.RchUserProfile;
import com.svs.rch.user.core.security.service.AccountAuthService;
import com.svs.rch.user.core.service.AccountCreationService;
import com.svs.rch.user.core.service.ProfileService;
import com.svs.rch.user.web.beans.ProfileEditForm;
import com.svs.rch.user.web.beans.UserEmailActivateForm;
import com.svs.rch.user.web.beans.UserInfoResponse;
import com.svs.rch.user.web.beans.UserProfileView;
import com.svs.rch.user.web.beans.UserRegisterForm;
import com.svs.rch.user.web.beans.UserRegisterResponse;

@Service
public class UserAppService {

	private static final Logger log = LoggerFactory.getLogger(UserAppService.class);

	@Autowired
	private AccountCreationService accountCreationService;

	@Autowired
	private AccountAuthService authService;

	@Autowired
	private ProfileService profileService;

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

		RchUserBean userBean = authService.getLoggedInUser();
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

	public ProfileEditForm updateProfile(ProfileEditForm profileForm) {

		RchUserProfile rchProfile = new RchUserProfile();
		rchProfile.setTitle(profileForm.getTitle());
		rchProfile.setGender(profileForm.getGender());
		rchProfile.setCountry(profileForm.getCountry());
		rchProfile.setHeadText(profileForm.getHeadText());
		rchProfile.setCareerInfo(profileForm.getCareerInfo());
		rchProfile.setContactInfo(profileForm.getContactInfo());
		rchProfile.setEducationInfo(profileForm.getEducationInfo());
		RchUserBean rchUser = new RchUserBean();

		rchUser.setFirstName(profileForm.getFirstName());
		rchUser.setLastName(profileForm.getLastName());
		rchUser.setBirthDate(profileForm.getBirthDate());
		rchUser.setMobileNo(profileForm.getMobileNo());
		rchUser.setUserId(authService.getLoggedInUser().getUserId());
		rchProfile.setUserBean(rchUser);

		rchProfile = profileService.updateUserProfile(rchProfile);

		return profileForm;
	}

	public UserProfileView getUserProfile() {

		UserProfileView view = new UserProfileView();
		RchUserBean userBean = authService.getLoggedInUser();
		log.info("Authenticated user, userId={}", userBean.getUserId());

		RchUserProfile rchProfile = profileService.getUserProfile(userBean.getUserId());
		log.info("Profile read for user, rchProfile={}", rchProfile);

		view.setUserId(userBean.getUserId());
		view.setEmailId(userBean.getEmailId());
		view.setMobileNo(userBean.getMobileNo());
		view.setLastName(userBean.getLastName());
		view.setFirstName(userBean.getFirstName());
		view.setBirthDate(userBean.getBirthDate());
		if (rchProfile != null) {
			view.setTitle(rchProfile.getTitle());
			view.setGender(rchProfile.getGender());
			view.setCountry(rchProfile.getCountry());
			view.setHeadText(rchProfile.getHeadText());
			view.setCareerInfo(rchProfile.getCareerInfo());
			view.setContactInfo(rchProfile.getContactInfo());
			view.setEducationInfo(rchProfile.getEducationInfo());
		}

		return view;
	}
}
