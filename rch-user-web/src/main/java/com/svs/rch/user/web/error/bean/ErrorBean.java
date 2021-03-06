package com.svs.rch.user.web.error.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorBean {

	private Long code;
	private String message;
}
