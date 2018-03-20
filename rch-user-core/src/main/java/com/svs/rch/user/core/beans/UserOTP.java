package com.svs.rch.user.core.beans;

import java.io.Serializable;

public class UserOTP implements Serializable {

	private static final long serialVersionUID = -9065837679516536384L;

	private static final int OTP_MAX_ACCESS = 3;

	private String otp;
	private Integer accessCount;

	public UserOTP(String otp) {
		this.otp = otp;
		this.accessCount = 0;
	}

	public boolean incrAccessCount() {
		if (accessCount == OTP_MAX_ACCESS) {
			return false;
		}
		accessCount++;
		return true;
	}

	public boolean check(String otp) {
		return this.otp.equals(otp);
	}

	@Override
	public String toString() {

		return "UserOTP[" + otp + "/" + accessCount + "]";
	}
}
