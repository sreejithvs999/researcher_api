package com.svs.rch.user.web.beans;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {

	private Long userId;

	private String emailId;

	private String firstName;

	private String lastName;

	private Integer status;
}
