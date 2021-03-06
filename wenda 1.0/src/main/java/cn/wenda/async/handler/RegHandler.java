package cn.wenda.async.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.wenda.async.EventHandler;
import cn.wenda.async.EventModel;
import cn.wenda.async.EventType;
import cn.wenda.interceptor.HostHolder;
import cn.wenda.service.MailSenderService;
/**
 *	 处理用户注册事件
 * @author wuu
 * 2018年12月19日
 */
//@Component
public class RegHandler implements EventHandler {
	@Autowired
	MailSenderService mailSender;
	@Autowired
	HostHolder hostHolder;

	String userEmail="541994159@qq.com";
	@Override
	public void doHandle(EventModel model) {
		System.out.println("RegHandler doHandle");
		try {
			mailSender.sendEmail(userEmail,hostHolder.getUser(), "欢迎注册问答社区！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.LOGIN);
	}
	
}
