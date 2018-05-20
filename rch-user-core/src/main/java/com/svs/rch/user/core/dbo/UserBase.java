package com.svs.rch.user.core.dbo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rch_user_base")
@NamedQueries({
		@NamedQuery(name = "getUserBaseListByEmailId", query = "select ub from UserBase ub where ub.emailId =:emailId"),
		@NamedQuery(name = "getUserBaseByEmailId$UserStatus", query = "select ub from UserBase ub where ub.emailId =:emailId and ub.userStatus=:userStatus") })

@Getter
@Setter
public class UserBase implements Serializable {

	private static final long serialVersionUID = -8267864708734571667L;

	@Id
	@SequenceGenerator(name = "rch_user_seq", allocationSize = 1, sequenceName = "rch_user_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rch_user_seq")
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "email_id", length = 250)
	private String emailId;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "birth_date")
	private LocalDate birthDate;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "user_status")
	private UserStatusEnum userStatus = UserStatusEnum.DEFAULT;

	@Column(name = "created_dt")
	private LocalDateTime createdDt;

	@Column(name = "updated_dt")
	private LocalDateTime updatedDt;

	@Override
	public boolean equals(Object obj) {

		if (obj == null || !(obj instanceof UserBase)) {
			return false;
		}
		UserBase casted = (UserBase) obj;
		if (casted.getUserId() == null || !casted.getUserId().equals(this.getUserId())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return userId != null ? userId.hashCode() : super.hashCode();
	}

	@Override
	public String toString() {
		return "UserBase (userId=" + userId + ";emailId=" + emailId + ";firstName=" + firstName + ";lastName" + lastName
				+ ")";
	}

}
