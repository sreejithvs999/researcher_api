package com.svs.rch.user.core.service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements InitializingBean {

	private static Logger log = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	private String emailFromId;

	@Autowired
	private TaskExecutor taskExecutor;

	public void sendSimpleText(String to, String subject, String text) {

		taskExecutor.execute(new Runnable() {

			@Override
			public void run() {

				try {
					MimeMessage msg = mailSender.createMimeMessage();

					msg.setRecipient(RecipientType.TO, new InternetAddress(to));
					msg.setFrom(new InternetAddress(emailFromId));					
					msg.setSubject(subject);
					msg.setContent(text, "text/html");
					mailSender.send(msg);
					log.info("Sent simple text mail");
				} catch (Exception e) {
					log.error("Sent simple text mail", e);
				}

			}
		});

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		emailFromId = env.getProperty("mail.username");
	}

}
