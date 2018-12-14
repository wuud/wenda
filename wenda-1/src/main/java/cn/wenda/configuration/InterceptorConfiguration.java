package cn.wenda.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.wenda.interceptor.LoginInterceptor;
@Component
public class InterceptorConfiguration implements WebMvcConfigurer{
	
	@Autowired
	LoginInterceptor loginInterceptor;
	
	/**
	 * 将自定义的拦截器注册到Spring容器内
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor);
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
