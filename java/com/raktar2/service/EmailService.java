package com.raktar2.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;
	
	public void sendMessage(String mailTo, String code) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(MESSAGE_FROM);
		message.setTo(mailTo);
		message.setSubject("Aqua Royal regisztráció");
		
		String szoveg="Üdvözöljük az Aqua Royal Kft. felhasználói között!\n\n Kérjük kattintson az alábbi linkre a regisztrációja véglegesítéséhez.\n\nhttp://localhost:8080/activation/"+code;
		message.setText(szoveg);
		javaMailSender.send(message);
	}
	
	
	
}
