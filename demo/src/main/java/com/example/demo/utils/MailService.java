package com.example.demo.utils;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MailService {
	
	private static final Logger log = LoggerFactory.getLogger(MailService.class);
	
	private JavaMailSender mailSender;
	
	public void sendScheduledMail() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("발송 주소");
		message.setTo("수신 주소");
		message.setSubject("");
		message.setText("");
		mailSender.send(message);
	}
	// 단일 대상에 발송
	public void sendMail(Map<String, Object> params) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(Util.mapToString(params, "from")); // 발신
		message.setTo(Util.mapToString(params, "to")); // 수신
		message.setSubject(Util.mapToString(params, "subject")); // 제목
		message.setText(Util.mapToString(params, "text")); // 내용
		mailSender.send(message);
	}
	// 복수 대상에 발송
	public void sendMail(List<Map<String, Object>> paramsList) {
		for (Map<String, Object> params : paramsList) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(Util.mapToString(params, "from")); // 발신
			message.setTo(Util.mapToString(params, "to")); // 수신
			message.setSubject(Util.mapToString(params, "subject")); // 제목
			message.setText(Util.mapToString(params, "text")); // 내용
			mailSender.send(message);
		}
	}
}
