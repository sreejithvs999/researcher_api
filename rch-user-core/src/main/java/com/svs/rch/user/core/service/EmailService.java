package com.svs.rch.user.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendSimpleText(String to, String replyTo, String from, String subject, String text) {
		SimpleMailMessage smm = new SimpleMailMessage();

		smm.setTo(to);
		smm.setReplyTo(replyTo);
		smm.setFrom(from);
		smm.setSubject(subject);
		smm.setText(text);
		mailSender.send(smm);
	}
}
