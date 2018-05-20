package com.svs.rch.user.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.svs.rch.user.web.appservice.UserAppService;
import com.svs.rch.user.web.beans.GenericResponse;
import com.svs.rch.user.web.beans.ProfileEditForm;
import com.svs.rch.user.web.beans.UserProfileView;

@RestController
@RequestMapping(value = "/user/profile")
public class ProfileController {

	@Autowired
	UserAppService userAppService;

	@PostMapping(value = "/edit")
	public GenericResponse<ProfileEditForm> updateProfile(@Validated @RequestBody ProfileEditForm profileForm) {

		return GenericResponse.ofPayload(userAppService.updateProfile(profileForm));
	}

	@GetMapping
	public GenericResponse<UserProfileView> getProfile() {

		return GenericResponse.ofPayload(userAppService.getUserProfile());
	}
}
