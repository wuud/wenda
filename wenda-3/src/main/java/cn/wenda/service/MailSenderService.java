package cn.wenda.service;

import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import cn.wenda.async.EventModel;

@Service
public class MailSenderService implements InitializingBean {

	private JavaMailSenderImpl mailSender;
	
	@Autowired
	UserService userService;

	public boolean sendWithHTMLTemplate(String to, String subject, EventModel model
			) throws Exception {
		String nick=MimeUtility.encodeText("来自问答社区");
		InternetAddress from=new InternetAddress(nick+"<wuu_dd@qq.com>");
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		String username=userService.getUserById(model.getActorId()).getName();
		String templateStr="<p th:text=\"您好+"+username+",欢迎注册问答社区 \"></p>";
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(templateStr, true);
        mailSender.send(mimeMessage);
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		mailSender = new JavaMailSenderImpl();
		mailSender.setUsername("wuu_dd@qq.com");
		mailSender.setPassword("+=wudi.com");
		mailSender.setDefaultEncoding("utf8");
		Properties p = new Properties();
		p.put("mail.smtp.ssl.enable", true);

		mailSender.setJavaMailProperties(p);

	}

}
