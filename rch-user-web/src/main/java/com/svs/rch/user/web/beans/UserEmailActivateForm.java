package com.svs.rch.user.web.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.svs.rch.user.core.common.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEmailActivateForm {

	@Pattern(message = "email.pattern.error", regexp = Constants.EMAIL_PATTERN)
	@Size(message = "email.length.error", max = Constants.EMAIL_MAX_LENGTH)
	@NotNull(message = "email.pattern.error")
	private String emailId;

	@Pattern(message = "email.otp.pattern.error", regexp = Constants.EMAIL_OTP_PATTERN)
	@NotNull(message = "email.otp.pattern.error")
	private String otp;
}
