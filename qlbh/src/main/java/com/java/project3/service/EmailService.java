package com.java.project3.service;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	public void sendTest() {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = 
			new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
			
			Context context = new Context();
			context.setVariable("name", "noi dung truyen vao");
			String body = templateEngine.process("email/email.html", context);
			
			// send email
			helper.setTo("someone@gmail.com"); // email gửi tới
			helper.setText(body, true); // nội dung mail
			helper.setSubject("Test email from springboot"); // tiêu đề
			helper.setFrom("yourgmail@gmail.com");
			
			javaMailSender.send(message);
		} catch(MessagingException e) {
			log.error("Email sent with error: ", e);
		}
	}
	
	@Async // new thread
	public void sendBirthday(String to, String name) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = 
			new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
			
			Context context = new Context();
			context.setVariable("name", name);
			String body = templateEngine.process("email/birthday.html", context);
			
			// send email
			helper.setTo("someone@gmail.com"); // email gửi tới
			helper.setText(body, true); // nội dung mail
			helper.setSubject("Test email from springboot"); // tiêu đề
			helper.setFrom("yourgmail@gmail.com");
			
			javaMailSender.send(message);
		} catch(MessagingException e) {
			log.error("Email sent with error: ", e);
		}
	}
}
