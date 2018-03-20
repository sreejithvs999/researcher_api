package com.svs.rch.user.core.common;

public class Constants {

	public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
	public static final int EMAIL_MAX_LENGTH = 250;

	public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*])(?=.{8,}).*";
	public static final int PASSWORD_MAX_LENGTH = 20;

	public static final int NAME_MAX_LENGTH = 100;

	public static final String MOBILENO_PATTERN = "^(\\+(?:[0-9] ?){6,14}[0-9])?$";

	public static final String EMAIL_OTP_PATTERN = "^[A-Z0-9]{4,4}$";

}
