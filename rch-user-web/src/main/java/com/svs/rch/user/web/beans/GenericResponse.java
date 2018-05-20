package com.svs.rch.user.web.beans;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericResponse<T> {

	private Long timestamp;
	private String status = "SUCCESS";

	private T payload;

	public static <T> GenericResponse<T> ofPayload(T payload) {
		return new GenericResponse<>(Instant.now().toEpochMilli(), "SUCCESS", payload);
	}

	public static <T> GenericResponse<T> ofError(T errorBean) {
		return new GenericResponse<>(Instant.now().toEpochMilli(), "ERROR", errorBean);
	}
}
