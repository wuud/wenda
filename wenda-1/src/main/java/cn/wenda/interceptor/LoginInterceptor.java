package cn.wenda.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.wenda.dao.TicketDao;
import cn.wenda.dao.UserDao;
import cn.wenda.model.Ticket;
import cn.wenda.model.User;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	TicketDao ticketDao;
	@Autowired
	UserDao userDao;
	@Autowired
	HostHolder hostHolder;
	/**
	 * 预处理，通过ticket获得user
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.err.println("拦截器=====");
		String ticket = null;
		Cookie[] cookies = request.getCookies();
		//首先拿到所有的cookie，然后找到ticket
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ticket")) {
					ticket = cookie.getValue();
					break;
				}
			}
		}
		//对找到的ticket做检查判断，查看当前ticket是否过期和状态是否正常，再通过ticket获得对应的user对象
		if (ticket != null) {
			Ticket t = ticketDao.getTicketByTicket(ticket);
			if (t == null || t.getExpired().before(new Date()) || t.getStatus() != 0) {
				return true;
			}
			User user = userDao.getUserById(t.getUser_id());
			System.out.println(t);
			System.out.println("user对象:"+user);
			hostHolder.setUser(user);
		}
		return true;
	}
	/**
	 * 将user对象注入到ModelAndView内
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null && hostHolder.getUser() != null) {
			System.out.println(hostHolder.getUser());
			modelAndView.addObject("user", hostHolder.getUser());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		hostHolder.clear();
	}

}
