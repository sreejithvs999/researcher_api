package com.svs.rch.user.core.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RchUserProfile {

	private Long profileId;

	private String title;

	private String gender;

	private String headText;

	private String country;

	private String contactInfo;

	private String educationInfo;

	private String careerInfo;

	private RchUserBean userBean;

	@Override
	public String toString() {
		return super.toString() + " RchUserProfile(profileId=" + profileId + "userBean=" + userBean + ")";
	}
}
