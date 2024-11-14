package com.example.demo.utils;

@Transactional
@Service
public class MailService {
	
	private static final Logger log = LoggerFactory.getLogger(MailService.class);
	
	private JavaMailSender mailSender;

	public void sendMail() {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("발송 주소");
		message.setTo("수신 주소");
		message.setSubject();
		message.setText();

		mailSender.send(message);
	}
}
