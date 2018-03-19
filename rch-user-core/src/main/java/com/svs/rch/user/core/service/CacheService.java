package com.svs.rch.user.core.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class CacheService {

	Cache<Long, String> otpCache;

	@PostConstruct
	public void init() {
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

	}

	public void putInOtpCache(Long key, String otp) {
		otpCache.put(key, otp);
	}

	public String readFromOtpCache(Long key) {
		return otpCache.getIfPresent(key);
	}
}
