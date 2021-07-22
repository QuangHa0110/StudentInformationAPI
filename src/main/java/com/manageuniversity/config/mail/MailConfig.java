package com.manageuniversity.config.mail;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import ch.qos.logback.classic.pattern.EnsureExceptionHandling;

//@Configuration
public class MailConfig {
	
	
	
//	@Bean
//	public JavaMailSender getJavaMailSender() {
//	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//	    
//	    mailSender.setHost("smtp.gmail.com");
//	    mailSender.setPort(587);
//	      
//	    mailSender.setUsername("quanghadang08@gmail.com");
//	    mailSender.setPassword("mtyfxibyrtovefxm");
//	      
//	    Properties props = mailSender.getJavaMailProperties();
//	    props.put("mail.transport.protocol", "smtp");
//	    props.put("mail.smtp.auth", "true");
//	    props.put("mail.smtp.starttls.enable", "true");
//	    props.put("mail.debug", "true");
//	      
//	    return mailSender;
//	}

}
