package cn.wenda.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 * 为thymeleaf解析字符串注册模板引擎对象
 * @author wuu
 * 2018年12月19日
 */
//@Configuration
public class TemplateResolverConfiguration {
	
	@Bean
	public SpringTemplateEngine springTemplateEngine() {
		SpringTemplateEngine springTemplateEngine=new SpringTemplateEngine();
		IDialect springStandardDialect=new SpringStandardDialect();
		springTemplateEngine.setDialect(springStandardDialect);
		
		StringTemplateResolver stringTemplateResolver=new StringTemplateResolver();
		stringTemplateResolver.setTemplateMode(TemplateMode.HTML);
		springTemplateEngine.setTemplateResolver(stringTemplateResolver);
		
		return springTemplateEngine;
		
	}

}
