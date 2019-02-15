package cn.wenda.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.wenda.interceptor.AlreadyLoginInterceptor;
import cn.wenda.interceptor.LoginInterceptor;
import cn.wenda.interceptor.RequireLoginInterceptor;

/**
   *       进行拦截器的注册
 * @author wuu
 * 2018年12月14日
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer{
	
	@Autowired
	LoginInterceptor loginInterceptor;
	@Autowired
	RequireLoginInterceptor requireLoginInterceptor;
	@Autowired
	AlreadyLoginInterceptor alreadyLoginInterceptor;
	
	/**
	 * 将自定义的拦截器注册到Spring容器内
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(requireLoginInterceptor).addPathPatterns("/user/**")
//		.addPathPatterns("/question/**").addPathPatterns("/msg/**")
//		.addPathPatterns("/pullfeeds");
		
		registry.addInterceptor(loginInterceptor);
		
		registry.addInterceptor(alreadyLoginInterceptor).addPathPatterns("/login");
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
