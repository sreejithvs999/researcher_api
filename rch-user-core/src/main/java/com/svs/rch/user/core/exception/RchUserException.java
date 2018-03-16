package com.svs.rch.user.core.exception;

/**
 * Custom application exception for Researcher user module related error
 * scenario.
 * 
 * @author Sreejith VS
 *
 */

public class RchUserException extends RuntimeException {

	private static final long serialVersionUID = 921760143806149571L;

	private Integer errorCode;
	private String errorText;

	public RchUserException() {
		super();
	}

	public RchUserException(String message) {
		super(message);
	}

	public RchUserException(Throwable throwable) {
		super(throwable);
	}

	public RchUserException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getErrorText() {
		return errorText;
	}

	public RchUserException detail(Integer errorCode, String errorText) {
		this.errorCode = errorCode;
		this.errorText = errorText;
		return this;
	}
}
