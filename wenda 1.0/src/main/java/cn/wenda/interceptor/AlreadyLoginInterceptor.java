package cn.wenda.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.wenda.utils.Constants;
/**
 *  拦截登录页面，如果当前用户已经登录，又去访问登录界面，直接跳转到首页
 * @author wuu
 * 2018年12月14日
 */
@Component
public class AlreadyLoginInterceptor implements HandlerInterceptor {
	@Autowired
	HostHolder hostHolder;
	@Autowired
	Constants constant;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(hostHolder.getUser()!=null) {
			response.sendRedirect(constant.hostName);
		}
		return true;
	}

}
