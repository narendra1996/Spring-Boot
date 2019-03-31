package com.spring.boot.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	
	@Autowired
    private JavaMailSender  mailSender;
	
	public void sendRecoveryEmail(String email, String tempPassword) throws MessagingException {
		logger.info("sendRecoveryEmail start");
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setSubject("Recovery Email");
			helper.setText("Your Recovery Passsword is: "+tempPassword);
//			helper.setText("TEST PLEASE IGNORE");
			helper.setTo(email);
//			helper.setCc("narendrareddy1996@gmail.com");
			helper.setFrom("system.user.notify@gmail.com");

			// helper.addAttachment("attachment-document-name.jpg", new
			// ClassPathResource("memorynotfound-logo.jpg"));

			mailSender.send(message);
			logger.info("sendRecoveryEmail end");
		} catch (Exception e) {
			logger.info("error while sending email: " + e.getMessage());
			throw (e);
		}

	}
	
}
