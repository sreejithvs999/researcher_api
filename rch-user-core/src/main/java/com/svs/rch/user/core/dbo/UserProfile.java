package com.svs.rch.user.core.dbo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@NamedQueries({
		@NamedQuery(name = "getProfileAllByUserId", query = "select up from UserProfile up join up.userBase ub where ub.userId =:userId"),
		@NamedQuery(name = "getProfileByUserId", query = "select up from UserProfile up where up.userBase.userId =:userId")})

@Table(name = "rch_user_profile")
public class UserProfile implements Serializable {

	private static final long serialVersionUID = -3335503902521411140L;

	@Id
	@SequenceGenerator(name = "rch_user_seq", allocationSize = 1, sequenceName = "rch_user_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rch_user_seq")
	@Column(name = "profile_id")
	private Long profileId;

	@JoinColumn(name = "user_id", updatable = false)
	@OneToOne()
	private UserBase userBase;

	@Column(name = "title")
	private String title;

	@Column(name = "gender")
	private String gender;

	@Column(name = "head_text")
	private String headText;

	@Column(name = "country")
	private String country;

	@Column(name = "contact_info")
	private String contactInfo;

	@Column(name = "education_info")
	private String educationInfo;

	@Column(name = "career_info")
	private String careerInfo;

	@Column(name = "created_dt")
	private LocalDateTime createdDt;

	@Column(name = "updated_dt")
	private LocalDateTime updatedDt;

	@Override
	public boolean equals(Object obj) {

		if (obj == null || !(obj instanceof UserProfile)) {
			return false;
		}

		UserProfile other = (UserProfile) obj;
		if (other.getProfileId() == null || !other.getProfileId().equals(getProfileId())) {
			return false;
		}

		if (getUserBase() != null && !getUserBase().equals(other.getUserBase())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return getProfileId() != null ? this.getProfileId().hashCode() : super.hashCode();
	}

	@Override
	public String toString() {

		return "UserProfile (profileId=" + getProfileId() + "; user=" + getUserBase() + "; )";
	}
}
