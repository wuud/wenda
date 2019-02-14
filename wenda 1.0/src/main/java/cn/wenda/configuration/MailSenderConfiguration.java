package cn.wenda.configuration;

import java.util.Properties;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class MailSenderConfiguration {
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
		mailSender.setHost("smtp.qq.com");
		mailSender.setPort(465);
		mailSender.setUsername("wuu_dd@qq.com");
		//授权码
		mailSender.setPassword("vljutbwwrgbzdjec");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.ssl.enable", true);
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.timeout", 25000);
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}
	/**
	 *  从类路径加载Thymeleaf模板
	 * @return ClassLoaderTemplateResolver
	 */
	@Bean
	public ClassLoaderTemplateResolver emailTemplateResolver() {
		ClassLoaderTemplateResolver resolver=new ClassLoaderTemplateResolver();
		resolver.setPrefix("templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setOrder(2);
		return resolver;
	}
	

}
