package com.svs.rch.user.core.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.core.beans.UserOTP;
import com.svs.rch.user.core.common.RchUserUtils;
import com.svs.rch.user.core.dao.UserBaseDao;
import com.svs.rch.user.core.dbo.UserBase;
import com.svs.rch.user.core.dbo.UserStatusEnum;
import com.svs.rch.user.core.exception.RchUserException;
import com.svs.rch.user.core.exception.RchUserExceptionConstants;
import com.svs.rch.user.core.infra.service.CacheService;
import com.svs.rch.user.core.infra.service.EmailService;

@Service
public class AccountCreationService {

	@Autowired
	private UserBaseDao userBaseDao;

	@Autowired
	private CacheService cacheService;

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

		/*
		 * Any user base already saved for given email id and not yet active.
		 */
		Optional<UserBase> matchingUserOpt = usersByEmail.stream().filter(ue -> ue.getUserStatus().isNew()).findFirst();

		UserBase userBase;

		if (matchingUserOpt.isPresent()) {
			userBase = matchingUserOpt.get();
		} else {
			userBase = new UserBase();
			userBase.setEmailId(userBean.getEmailId());
			userBase.setUserStatus(UserStatusEnum.NEW);
		}

		userBase.setMobileNo(userBean.getMobileNo());
		userBase.setFirstName(userBean.getFirstName());
		userBase.setLastName(userBean.getLastName());
		userBase.setPassword(userBean.getPassword());
		userBase.setBirthDate(userBean.getBirthDate());
		userBase.setCreatedDt(RchUserUtils.utcDateTime());

		log.info("saving user base data. userBase={}", userBase);
		userBase = userBaseDao.saveUserBase(userBase);

		log.info("saved user base data userBase.userId={}", userBase.getUserId());
		userBean.setUserId(userBase.getUserId());

		return userBean;
	}

	public void sendConfirmEmailNotification(RchUserBean userBean) {

		String otp = RchUserUtils.generateRandomOTP();
		cacheService.putInOtpCache(userBean.getUserId(), new UserOTP(otp));

		emailService.sendAccountActivatedEmail(userBean, otp);

	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public RchUserBean activateEmailByOTP(String emailId, String otp) {

		List<UserBase> usersByEmail = userBaseDao.getNewUserBaseByEmailId(emailId);
		if (usersByEmail.isEmpty()) {
			log.info("No user found for activation. emailId={}", emailId);
			throw new RchUserException().detail(RchUserExceptionConstants.EMAIL_ACTIVATE_NOT_FOUND,
					"Email Activation with OTP : No email Id found for activation.");
		}

		UserBase userBase = usersByEmail.get(0);
		UserOTP otpChd = cacheService.readFromOtpCache(userBase.getUserId());
		if (otpChd == null) {
			throw new RchUserException().detail(RchUserExceptionConstants.EMAIL_ACTIVATE_OTP_EXPIRED,
					"Email Activation with OTP : OTP does not exists.");
		}
		if (otpChd.incrAccessCount()) {
			cacheService.putInOtpCache(userBase.getUserId(), otpChd);

		} else {
			throw new RchUserException().detail(RchUserExceptionConstants.EMAIL_ACTIVATE_OTP_EXCEEDS,
					"Email Activation with OTP : OTP validation attempts exceeds limit.");
		}

		if (otpChd.check(otp)) {
			userBase.setUserStatus(UserStatusEnum.ACTIVE);
			userBase.setUpdatedDt(RchUserUtils.utcDateTime());
		} else {
			throw new RchUserException().detail(RchUserExceptionConstants.EMAIL_ACTIVATE_OTP_MISMATCH,
					"Email Activation with OTP : OTP does not match.");
		}

		userBaseDao.saveUserBase(userBase);

		RchUserBean userBean = new RchUserBean();
		userBean.setUserId(userBase.getUserId());
		userBean.setEmailId(userBase.getEmailId());
		userBean.setFirstName(userBase.getFirstName());
		userBean.setLastName(userBase.getLastName());
		userBean.setStatus(userBase.getUserStatus().ordinal());

		return userBean;

	}

	public void sendEmailActivatedNotification(RchUserBean userBean) {

		emailService.sendConfirmAccountEmail(userBean);

	}
}
