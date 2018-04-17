package com.svs.rch.user.web.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.web.appservice.UserRegistrationService;
import com.svs.rch.user.web.beans.GenericResponse;
import com.svs.rch.user.web.beans.UserEmailActivateForm;
import com.svs.rch.user.web.beans.UserInfoResponse;
import com.svs.rch.user.web.beans.UserRegisterForm;
import com.svs.rch.user.web.beans.UserRegisterResponse;
import com.svs.rch.user.web.security.RchUserDetails;

/**
 * 
 * @author Sreejith VS
 *
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRegistrationService userRegService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<UserRegisterResponse> registerWithDetails(
			@Validated @RequestBody UserRegisterForm registerForm) {
		logger.info("register user controller.");

		return GenericResponse.ofPayload(userRegService.registerUser(registerForm));
	}

	@RequestMapping(value = "/activate/email", method = RequestMethod.POST)
	public GenericResponse<UserRegisterResponse> activateEmailByOTP(
			@Validated @RequestBody UserEmailActivateForm emailActivateForm) {

		logger.info("email activate by otp. ");

		return GenericResponse.ofPayload(userRegService.activateEmailByOTP(emailActivateForm));
	}

	@RequestMapping(value = "/info")
	public GenericResponse<UserInfoResponse> userInfo() {

		logger.info("request to user info from authentication.");
		return GenericResponse.ofPayload(userRegService.getLoggedInUserInfo());
	}

	@RequestMapping("/login")
	public Object userLogin(HttpServletRequest request, Authentication principal) {
		return request.getAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE);
	}
}
