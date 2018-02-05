package com.svs.rch.user.web.error;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = "com.svs.rch.user.web.controller")
public class GlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public Object handleRequestValidationException(MethodArgumentNotValidException mae, Locale locale) {

		Map<String, String> errorMap = new HashMap<>();

		FieldError fieldError = mae.getBindingResult().getFieldError();
		if (fieldError != null) {
			errorMap.put("errorMessage", messageSource.getMessage(fieldError.getDefaultMessage(), null, locale));
		} else {
			errorMap.put("errorMessage", mae.getMessage());
		}
		return errorMap;
	}

	@ExceptionHandler(value = Exception.class)
	public Object handleRequestValidationException(Exception e) {

		logger.error("Caught exception", e);
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("errorMessage", e.getMessage());
		return errorMap;
	}
}
