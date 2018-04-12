package com.svs.rch.user.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.rch.user.core.beans.RchUserBean;
import com.svs.rch.user.core.dao.UserBaseDao;
import com.svs.rch.user.core.dbo.UserBase;

@Service
public class AccountAuthService {

	@Autowired
	private UserBaseDao userBaseDao;

	public RchUserBean getUserAccountForLogin(String emailId) {

		List<UserBase> list = userBaseDao.getActiveUserBase(emailId);
		if (list.isEmpty()) {
			return null;
		}

		UserBase source = list.get(0);
		RchUserBean userBean = new RchUserBean();
		userBean.setEmailId(source.getEmailId());
		userBean.setPassword(source.getPassword());
		userBean.setFirstName(source.getFirstName());
		userBean.setLastName(source.getLastName());
		userBean.setMobileNo(source.getMobileNo());
		userBean.setStatus(source.getUserStatus().ordinal());
		userBean.setUserId(source.getUserId());
		userBean.setBirthDate(source.getBirthDate());
		return userBean;

	}
}
