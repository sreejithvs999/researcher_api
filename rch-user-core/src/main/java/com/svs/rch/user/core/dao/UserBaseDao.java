package com.svs.rch.user.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.svs.rch.user.core.dbo.UserBase;
import com.svs.rch.user.core.dbo.UserStatusEnum;

@Repository
public class UserBaseDao {

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@PersistenceContext
	EntityManager entityManager;

	public List<UserBase> getUserBaseListByEmailId(String emailId) {

		return entityManager.createNamedQuery("getUserBaseListByEmailId", UserBase.class)
				.setParameter("emailId", emailId).getResultList();
	}

	public List<UserBase> getUserBaseListByMobileNo(String mobileNo) {

		return entityManager.createNamedQuery("getUserBaseListByMobileNo", UserBase.class)
				.setParameter("mobileNo", mobileNo).getResultList();
	}

	public void deleteNewAccountsFor(String emailId, String mobileNo) {
		entityManager.createNamedQuery("deleteUserBasePreCreate").setParameter("emailId", emailId)
				.setParameter("mobileNo", mobileNo).setParameter("userStatus", UserStatusEnum.NEW).executeUpdate();
	}

	public UserBase saveUserBase(UserBase userBase) {

		entityManager.persist(userBase);
		entityManager.flush();
		return userBase;
	}

}
