package com.svs.rch.user.core.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Equivalence;
import com.google.common.base.Predicates;
import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.core.beans.RchUserProfile;
import com.svs.rch.user.core.common.RchUserUtils;
import com.svs.rch.user.core.dao.UserBaseDao;
import com.svs.rch.user.core.dao.UserProfileDao;
import com.svs.rch.user.core.dbo.UserBase;
import com.svs.rch.user.core.dbo.UserProfile;

@Service
public class ProfileService {

	@Autowired
	private UserProfileDao profileDao;

	@Autowired
	private UserBaseDao userDao;

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public RchUserProfile updateUserProfile(RchUserProfile userProfile) {

		RchUserBean userBean = userProfile.getUserBean();
		Optional<UserProfile> anyProfile = profileDao.getUserProfileAndBase(userBean.getUserId());
		UserProfile profileDO;
		if (anyProfile.isPresent()) {
			profileDO = anyProfile.get();
			profileDO.setUpdatedDt(RchUserUtils.utcDateTime());
		} else {
			profileDO = new UserProfile();
			profileDO.setCreatedDt(RchUserUtils.utcDateTime());
			profileDO.setUserBase(userDao.getUserBaseById(userBean.getUserId()));
		}

		profileDO.setHeadText(userProfile.getHeadText());
		profileDO.setTitle(userProfile.getTitle());
		profileDO.setCountry(userProfile.getCountry());
		profileDO.setCareerInfo(userProfile.getCareerInfo());
		profileDO.setContactInfo(userProfile.getContactInfo());
		profileDO.setEducationInfo(userProfile.getEducationInfo());
		profileDO.setGender(userProfile.getGender());
		profileDao.saveProfile(profileDO);

		UserBase userDO = profileDO.getUserBase();
		if (anyChangeInUserBase(userDO, userBean)) {
			userDO.setFirstName(userBean.getFirstName());
			userDO.setLastName(userBean.getLastName());
			userDO.setMobileNo(userBean.getMobileNo());
			userDO.setBirthDate(userBean.getBirthDate());
			userDO.setUpdatedDt(profileDO.getUpdatedDt());
			userDao.saveUserBase(userDO);
		}

		userProfile.setProfileId(profileDO.getProfileId());
		return userProfile;
	}

	private boolean anyChangeInUserBase(UserBase userDO, RchUserBean userBean) {

		Object[] existing = new Object[] { userDO.getFirstName(), userDO.getLastName(), userDO.getMobileNo(),
				userDO.getBirthDate() };

		Object[] changed = new Object[] { userBean.getFirstName(), userBean.getLastName(), userBean.getMobileNo(),
				userBean.getBirthDate() };

		return !Arrays.equals(existing, changed);
	}

	public RchUserProfile getUserProfile(Long userId) {

		Optional<UserProfile> anyProfile = profileDao.getUserProfile(userId);
		RchUserProfile rchProfile = new RchUserProfile();
		if (anyProfile.isPresent()) {
			UserProfile profileDO = anyProfile.get();
			rchProfile.setTitle(profileDO.getTitle());
			rchProfile.setGender(profileDO.getGender());
			rchProfile.setCountry(profileDO.getCountry());
			rchProfile.setHeadText(profileDO.getHeadText());
			rchProfile.setProfileId(profileDO.getProfileId());
			rchProfile.setCareerInfo(profileDO.getCareerInfo());
			rchProfile.setContactInfo(profileDO.getContactInfo());
			rchProfile.setEducationInfo(profileDO.getEducationInfo());
		}
		return rchProfile;
	}

}
