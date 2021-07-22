package com.manageuniversity.service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.manageuniversity.exception.BadRequestException;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Async
	public void sendMail(SimpleMailMessage email) {
		mailSender.send(email);
	}

	private final TemplateEngine templateEngine;

	private final JavaMailSender javaMailSender;

	public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
		this.templateEngine = templateEngine;
		this.javaMailSender = javaMailSender;
	}

	@Async
	public ResponseEntity<String> sendMail(String email, String token, HttpServletRequest request)
			throws MessagingException {
		try {
			String url = request.getRequestURI() +"/api/v1/users/reset-password?token=" + token ;

			Context context = new Context();

			context.setVariable("url", url);

			String process = templateEngine.process("email/email", context);
			javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSubject("Password Reset Request");
			helper.setText(process, true);
			helper.setTo(email);
			javaMailSender.send(mimeMessage);
			return ResponseEntity.ok().body("Email sent successful");
			
		} catch (Exception e) {
			throw new BadRequestException("Send mail error");
		}
		
	}

}
