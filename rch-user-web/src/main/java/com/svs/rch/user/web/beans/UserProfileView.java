package com.svs.rch.user.web.beans;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.svs.rch.user.core.common.data.util.LocalDateSerializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class UserProfileView {

	private Long userId;

	private String emailId;

	private String mobileNo;

	private String firstName;

	private String lastName;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthDate;

	private String title;

	private String gender;

	private String headText;

	private String country;

	private String contactInfo;

	private String educationInfo;

	private String careerInfo;
}
