package com.svs.rch.user.core.beans;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RchUserBean {

	private Long userId;
	
	private String emailId;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String mobileNo;
	
	private LocalDate birthDate;
	
	private Integer status;
}
