package com.svs.rch.user.core.common;

import java.util.Random;

public class RchUserUtils {

	public static String generateRandomOTP() {

		Random random = new Random();
		return String.valueOf(1000 + random.nextInt(10000));
	}
}
