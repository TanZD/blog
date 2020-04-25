package com.zd.Service.impl;

import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zd.Service.MailSenderService;

@Service("MailSenderImpl")
public class MailSenderServiceImpl implements MailSenderService {
	public static final Logger logger = LogManager.getLogger(MailSenderServiceImpl.class);

	@Autowired
	JavaMailSender sender;

	@Value("${mail.fromAddress}")
	private String from;

	MailMessage mailMessage;

	@Override
	@Async
	public void resetPassMail(String to, int user_id, String to_pass) {
		logger.info("SendTo: " + to);
		logger.info("ForgetPass: " + to_pass);
		logger.info("user_id: " + user_id);
		MimeMessage mail = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail);
		try {
			helper.setFrom("850222009@qq.com");
			helper.setTo(to);
			helper.setSubject("密码重置的验证码");
			helper.setText("<p>验证码为</p><p>" + to_pass + "</p><p>请不要泄露给别人哦</p>", true);
			sender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
