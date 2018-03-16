package com.svs.rch.user.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.svs.rch.user.web.appservice.UserRegistrationService;
import com.svs.rch.user.web.beans.GenericResponse;
import com.svs.rch.user.web.beans.UserRegisterForm;
import com.svs.rch.user.web.beans.UserRegisterResponse;

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
}
