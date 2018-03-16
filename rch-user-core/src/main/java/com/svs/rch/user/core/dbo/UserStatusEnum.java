package com.svs.rch.user.core.dbo;

public enum UserStatusEnum {

	DEFAULT, NEW, EMAIL_ACTIVE, MOBILE_ACTIVE, ACTIVE, LOCKED, DELETED;

	public boolean isNew() {
		return this.equals(NEW);
	}

	public boolean isEmailActive() {
		return this.equals(EMAIL_ACTIVE) || this.equals(ACTIVE);
	}

	public boolean isMobileActive() {
		return this.equals(MOBILE_ACTIVE) || this.equals(ACTIVE);
	}

	public boolean isActive() {
		return this.equals(ACTIVE);
	}

	public boolean isLocked() {
		return this.equals(LOCKED);
	}

	public boolean isDeleted() {
		return this.equals(DELETED);
	}
}
