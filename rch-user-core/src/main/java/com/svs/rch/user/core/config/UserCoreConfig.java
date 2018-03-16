package com.svs.rch.user.core.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class UserCoreConfig {

	@Bean
	@ConfigurationProperties("spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory entityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		HibernateJpaVendorAdapter hjva = new HibernateJpaVendorAdapter();
		hjva.setDatabasePlatform("org.hibernate.dialect.PostgreSQL94Dialect");
		emf.setJpaVendorAdapter(hjva);
		emf.setPackagesToScan("com.svs.rch.user.core.dbo");
		emf.setPersistenceUnitName("default");
		emf.getJpaPropertyMap().put("hibernate.jdbc.lob.non_contextual_creation","true");

		emf.afterPropertiesSet();
		return emf.getObject();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public JpaTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager jpaTransMgr = new JpaTransactionManager(emf);
		return jpaTransMgr;
	}
	
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(465);
	     
	    mailSender.setUsername("*****0@gmail.com");
	    mailSender.setPassword("*****");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.starttls.required", "true");
	    props.put("mail.smtp.ssl.enable", "true");
	    props.put("mail.smtp.ssl.enable", "true");
	    props.put("mail.test-connection", "true");
	    return mailSender;
	}
}
