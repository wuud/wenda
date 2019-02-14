package cn.wenda.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import cn.wenda.model.User;

@Service
public class MailSenderService  {

	@Autowired
	JavaMailSender mailSender;
	@Autowired
	ClassLoaderTemplateResolver resolver;
	
	public void sendEmail(String to,User user,String content) {
		Context ctx=new Context();
		ctx.setVariable("name", user.getName());
		ctx.setVariable("headUrl", user.getHead_url());
		ctx.setVariable("content", content);
		SpringTemplateEngine thymeleaf=new SpringTemplateEngine();
		thymeleaf.setTemplateResolver(resolver);
		String emailText = thymeleaf.process("email/welcomeReg.html", ctx);
		
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message,"UTF-8");
		try {
			helper.setFrom("wuu_dd@qq.com");
			helper.setTo(to);
			helper.setSubject("来自问答社区：");
			helper.setText(emailText,true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
