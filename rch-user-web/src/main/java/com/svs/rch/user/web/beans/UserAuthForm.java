package com.svs.rch.user.web.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(content = Include.NON_NULL)
public class UserAuthForm {

	private String emailId;

	private String password;

	private String accessToken;

	private Long expiresIn;
}
