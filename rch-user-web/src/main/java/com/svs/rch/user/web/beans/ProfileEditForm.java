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
import com.svs.rch.user.core.common.data.util.LocalDateDeserializer;
import com.svs.rch.user.core.common.data.util.LocalDateSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * Profile information of user.
 * 
 * @author Sreejith VS
 *
 */
@Getter
@Setter
public class ProfileEditForm {

	@Size(message = "profile.title.size.error", max = 50)
	private String title;

	@Pattern(message = "profile.gender.error", regexp = "^[MFO]$")
	private String gender;

	@Size(message = "profile.headtext.size.error", max = 250)
	private String headText;

	@Size(message = "profile.country.size.error", max = 100)
	private String country;

	@Size(message = "profile.contactinfo.size.error", max = 2000)
	private String contactInfo;

	@Size(message = "profile.educationinfo.size.error", max = 2000)
	private String educationInfo;

	@Size(message = "profile.careerinfo.size.error", max = 2000)
	private String careerInfo;

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
	@NotNull(message = "birthDate.value.error")
	private LocalDate birthDate;

	@AssertFalse(message = "birthDate.range.error")
	public boolean isValidBirthDate() {
		return (birthDate == null || birthDate.isAfter(LocalDate.now().minusYears(13))
				|| birthDate.isBefore(LocalDate.now().minusYears(100)));
	}
}
