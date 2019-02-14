package cn.wenda.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 	拦截那些需要登录才能访问的网页，如果用户未登录，则跳转到登录页面
 * 
 * @author wuu 
 * 2018年12月14日
 */
@Component
public class RequireLoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	HostHolder hostHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(hostHolder.getUser()==null) {
			response.sendRedirect("/login?next="+request.getRequestURI());
		}
		return true;
	}

}
