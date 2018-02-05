package com.svs.rch.user.web.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.svs.rch.user.web.beans.UserRegisterForm;

/**
 * 
 * @author Sreejith VS
 *
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public UserRegisterForm registerWithDetails(@Validated @RequestBody UserRegisterForm registerForm) {

		return registerForm;
	}
}
