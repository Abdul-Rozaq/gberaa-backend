package com.arktech.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.arktech.entity.NotificationEmail;
import com.arktech.exception.AppException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

	private JavaMailSender mailSender;
	private MailContentBuilder mailBuilder;
	
	@Async
	void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			
			messageHelper.setFrom("gberaa@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailBuilder.build(notificationEmail.getBody()));
		};
		
		try {
			mailSender.send(messagePreparator);
			log.info("Activation email sent");
		} catch (MailException e) {
			throw new AppException("Error occurred sending mail to " + notificationEmail.getRecipient());
		}
	}
}
