package com.svs.rch.user.web.beans;

import java.time.LocalDate;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.svs.rch.user.core.common.Constants;
import com.svs.rch.user.core.common.data.LocalDateDeserializer;
import com.svs.rch.user.core.common.data.LocalDateSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Sreejith VS
 *
 */
@Setter
@Getter
public class UserRegisterForm {

	@Pattern(message = "email.pattern.error", regexp = Constants.EMAIL_PATTERN)
	@Size(message = "email.length.error", max = Constants.EMAIL_MAX_LENGTH)
	@NotNull(message = "email.pattern.error")
	private String emailId;

	@Pattern(message = "password.pattern.error", regexp = Constants.PASSWORD_PATTERN)
	@Size(message = "password.length.error", max = Constants.PASSWORD_MAX_LENGTH)
	@NotNull(message = "password.pattern.error")
	private String password;

	@Size(message = "firstName.length.error", max = Constants.NAME_MAX_LENGTH)
	@NotNull(message = "firstName.null.error")
	private String firstName;

	@Size(message = "lastName.length.error", max = Constants.NAME_MAX_LENGTH)
	@NotNull(message = "lastName.null.error")
	private String lastName;

	@Pattern(message = "mobileNo.pattern.error", regexp = Constants.MOBILENO_PATTERN)
	private String mobileNo;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="birthDate.value.error")
	private LocalDate birthDate;

	@AssertFalse(message = "birthDate.range.error")
	public boolean isValidBirthDate() {
		return (birthDate == null || birthDate.isAfter(LocalDate.now().minusYears(13))
				|| birthDate.isBefore(LocalDate.now().minusYears(100)));
	}


}
