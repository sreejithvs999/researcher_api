package com.svs.rch.user.web.error.handler;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.svs.rch.user.core.exception.RchUserException;
import com.svs.rch.user.web.beans.GenericResponse;
import com.svs.rch.user.web.error.bean.ErrorBean;

@ControllerAdvice(basePackages = "com.svs.rch.user.web.controller")
public class GlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private static final Long INPUT_VALIDATION_ERROR = 1001L;
	private static final Long INPUT_UNKNOWN_ERROR = 1002L;
	private static final Long INPUT_MESSAGE_ERROR = 1003L;
	private static final Long SERVER_INTERNAL_ERROR = 9001L;

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public GenericResponse<ErrorBean> handleMethodArgumentNotValidException(MethodArgumentNotValidException mae,
			Locale locale) {

		FieldError fieldError = mae.getBindingResult().getFieldError();
		if (fieldError != null) {
			return GenericResponse.ofError(ErrorBean.builder().code(INPUT_VALIDATION_ERROR)
					.message(messageSource.getMessage(fieldError.getDefaultMessage(), null, locale)).build());
		} else {
			return GenericResponse
					.ofError(ErrorBean.builder().code(INPUT_UNKNOWN_ERROR).message(mae.getMessage()).build());

		}

	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<GenericResponse<ErrorBean>> handleException(Exception e, Locale locale) {

		logger.error("Caught exception", e);

		ResponseEntity<GenericResponse<ErrorBean>> re = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(GenericResponse.ofError(ErrorBean.builder().code(SERVER_INTERNAL_ERROR)
						.message(messageSource.getMessage("server.internal.error", null, locale)).build()));

		return re;
	}

	@ExceptionHandler(value = RchUserException.class)
	public ResponseEntity<GenericResponse<ErrorBean>> handleRchUserException(RchUserException e, Locale locale) {

		logger.error("Caught RchUserException", e.getMessage());

		ResponseEntity<GenericResponse<ErrorBean>> re = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(GenericResponse.ofError(
						ErrorBean.builder().code(e.getErrorCode().longValue()).message(e.getErrorText()).build()));

		return re;
	}

	@ExceptionHandler(value = HttpMessageConversionException.class)
	public ResponseEntity<GenericResponse<ErrorBean>> handleException(HttpMessageConversionException e, Locale locale) {

		logger.error("Caught HttpMessageConversionException", e);

		ResponseEntity<GenericResponse<ErrorBean>> re = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(GenericResponse.ofError(ErrorBean.builder().code(INPUT_MESSAGE_ERROR)
						.message(messageSource.getMessage("client.message.error", null, locale)).build()));

		return re;
	}
}
