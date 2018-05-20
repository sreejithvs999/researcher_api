package com.svs.rch.user.core.infra.service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.svs.rch.user.core.beans.RchUserBean;

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

	@Autowired
	private SpringTemplateEngine emailTemplatesEng;

	private void sendSimpleText(String to, String subject, String text) {

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

	@Override
	public void afterPropertiesSet() throws Exception {
		emailFromId = env.getProperty("mail.username");
	}

	private void sendWithTemplate(String to, String subject, String templateName, Context ctx) {
		taskExecutor.execute(() -> {
			String text = emailTemplatesEng.process(templateName, ctx);
			sendSimpleText(to, subject, text);
		});
	}

	public void sendConfirmAccountEmail(RchUserBean userBean) {

		Context ctx = new Context();
		ctx.setVariable("firstName", userBean.getFirstName());
		ctx.setVariable("lastName", userBean.getLastName());
		sendWithTemplate(userBean.getEmailId(), "Researcher App - Account activated", "email-confirm", ctx);
	}

	public void sendAccountActivatedEmail(RchUserBean userBean, String otp) {
		
		Context ctx = new Context();
		ctx.setVariable("firstName", userBean.getFirstName());
		ctx.setVariable("lastName", userBean.getLastName());
		ctx.setVariable("otpCode", otp);
		sendWithTemplate(userBean.getEmailId(), "Researcher App - Confirm Email Id", "registration-otp", ctx);
	}
}
