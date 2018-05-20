package com.svs.rch.user.core.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

public class RchUserUtils {

	private static char[] OTP_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

	public static String generateRandomOTP() {

		Random random = new Random();

		char[] chars = new char[4];

		chars[0] = OTP_CHARS[random.nextInt(36)];
		chars[1] = OTP_CHARS[random.nextInt(36)];
		chars[2] = OTP_CHARS[random.nextInt(36)];
		chars[3] = OTP_CHARS[random.nextInt(36)];

		return new String(chars);
	}

	public static LocalDateTime utcDateTime() {
		return LocalDateTime.now(ZoneId.of("UTC"));
	}

}
