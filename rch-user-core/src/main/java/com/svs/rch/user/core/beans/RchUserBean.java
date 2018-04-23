package com.svs.rch.user.core.beans;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RchUserBean implements Serializable {

	private static final long serialVersionUID = 8228981654156347902L;

	private Long userId;

	private String emailId;

	private String password;

	private String firstName;

	private String lastName;

	private String mobileNo;

	private LocalDate birthDate;

	private Integer status;

	@Override
	public String toString() {
		return super.toString() + " RchUserBean(userId=" + userId + "; emailId=" + emailId + "status=" + status + ")";
	}
}
