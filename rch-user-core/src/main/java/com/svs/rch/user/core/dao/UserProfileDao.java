package com.svs.rch.user.core.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.svs.rch.user.core.dbo.UserProfile;

@Repository
public class UserProfileDao {

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@PersistenceContext
	EntityManager entityManager;

	public Optional<UserProfile> getUserProfileAndBase(Long userId) {
		return entityManager.createNamedQuery("getProfileAllByUserId", UserProfile.class).setParameter("userId", userId)
				.getResultList().stream().findFirst();
	}

	
	public Optional<UserProfile> getUserProfile(Long userId) {
		return entityManager.createNamedQuery("getProfileByUserId", UserProfile.class).setParameter("userId", userId)
				.getResultList().stream().findFirst();
	}
	
	public UserProfile saveProfile(UserProfile userProfile) {
		entityManager.persist(userProfile);
		entityManager.flush();
		return userProfile;
	}
}
