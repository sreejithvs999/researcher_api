package com.svs.rch.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresource.ClassLoaderTemplateResource;

@Configuration
@SpringBootApplication(scanBasePackages = "com.svs.rch.user")
public class ResearcherApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearcherApiApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Bean(name="emailTemplatesEng")
	public SpringTemplateEngine springTemplateEngine(@Autowired MessageSource messageSource) {
		SpringTemplateEngine templateEng = new SpringTemplateEngine();
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(1);
		templateResolver.setPrefix("/templates/mail/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		templateEng.setTemplateResolver(templateResolver);
		templateEng.setMessageSource(messageSource);
		return templateEng;
	}
}
