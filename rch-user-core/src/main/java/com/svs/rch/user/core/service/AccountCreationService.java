package com.svs.rch.user.core.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.core.dao.UserBaseDao;
import com.svs.rch.user.core.dbo.UserBase;
import com.svs.rch.user.core.dbo.UserStatusEnum;
import com.svs.rch.user.core.exception.RchUserException;
import com.svs.rch.user.core.exception.RchUserExceptionConstants;

@Service
public class AccountCreationService {

	@Autowired
	private UserBaseDao userBaseDao;

	@Autowired
	private EmailService emailService;

	private static Logger log = LoggerFactory.getLogger(AccountCreationService.class);

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public RchUserBean createUserAccount(RchUserBean userBean) {

		List<UserBase> usersByEmail = userBaseDao.getUserBaseListByEmailId(userBean.getEmailId());
		if (usersByEmail.stream().anyMatch(x -> x.getUserStatus().isEmailActive() || x.getUserStatus().isLocked())) {
			log.info("Email id for account creation already exists. email={}", userBean.getEmailId());
			throw new RchUserException().detail(RchUserExceptionConstants.ACCOUNT_CREATION_EMAIL_EXISTS,
					"Account creation failed due to email id found to be already present against an account.");
		}

		List<UserBase> usersByMobileNo = userBaseDao.getUserBaseListByMobileNo(userBean.getMobileNo());
		if (usersByMobileNo.stream()
				.anyMatch(x -> x.getUserStatus().isMobileActive() || x.getUserStatus().isLocked())) {

			log.info("Mobile No for account creation already exists. mobileNo={}", userBean.getMobileNo());
			throw new RchUserException().detail(RchUserExceptionConstants.ACCOUNT_CREATION_MOBILE_EXISTS,
					"Account creation failed due to mobile no found to be already present against an account.");
		}

		/*
		 * Any user base already saved for given email id and mobile no and not yet
		 * active.
		 */
		Optional<UserBase> matchingUserOpt = usersByEmail.stream()
				.filter(ue -> ue.getMobileNo().equals(userBean.getMobileNo()) && ue.getUserStatus().isNew())
				.findFirst();

		UserBase userBase;

		if (matchingUserOpt.isPresent()) {
			userBase = matchingUserOpt.get();
		} else {
			userBase = new UserBase();
			userBase.setEmailId(userBean.getEmailId());
			userBase.setMobileNo(userBean.getMobileNo());
			userBase.setUserStatus(UserStatusEnum.NEW);
		}

		userBase.setFirstName(userBean.getFirstName());
		userBase.setLastName(userBean.getLastName());
		userBase.setPassword(userBean.getPassword());
		userBase.setBirthDate(userBean.getBirthDate());
		userBase.setCreatedDt(LocalDateTime.now(ZoneId.of("UTC")));

		log.info("saving user base data. userBase={}", userBase);
		userBase = userBaseDao.saveUserBase(userBase);

		log.info("saved user base data userBase.userId={}", userBase.getUserId());
		userBean.setUserId(userBase.getUserId());

		return userBean;
	}

	public void sendConfirmEmailNotification(RchUserBean userBean) {

		String text = "Hi " + userBean.getFirstName() + " " + userBean.getLastName() + ", \r\n<br/>"
				+ "An account has been created for Researcher User with this mail id"
				+ " Please confirm your email Id with the below code. \r\n<br/>" + "1234\r\n<br/>" + "Thanks, \r\n<br/>"
				+ "Researcher Team";
		emailService.sendSimpleText(userBean.getEmailId(), "jean.dieux2020@gmail.com", "jean.dieux2020@gmail.com",
				"Researcher user account confirmation", text);

	}
}
