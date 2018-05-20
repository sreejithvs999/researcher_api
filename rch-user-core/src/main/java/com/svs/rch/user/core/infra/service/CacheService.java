package com.svs.rch.user.core.infra.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.svs.rch.user.core.beans.UserOTP;

@Service
public class CacheService {

	private static Logger log = LoggerFactory.getLogger(CacheService.class);

	private Cache<Long, UserOTP> otpCache;

	@PostConstruct
	public void init() {
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

	}

	public void putInOtpCache(Long key, UserOTP otp) {

		log.info("Saving in cache : key={}, otp={}", key, otp);
		otpCache.put(key, otp);
	}

	public UserOTP readFromOtpCache(Long key) {

		log.info("Reading from cache : key={}", key);
		return otpCache.getIfPresent(key);
	}
}
