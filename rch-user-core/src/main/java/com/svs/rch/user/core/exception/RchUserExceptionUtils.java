package com.svs.rch.user.core.exception;

public class RchUserExceptionUtils {

	public RchUserException instance(Throwable t, Integer errorCode, String errorText) {
		RchUserException exp = new RchUserException(t);
		return exp.detail(errorCode, errorText);
	}
}
