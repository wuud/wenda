package cn.wenda.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.wenda.WendaApplication;
import cn.wenda.dao.MessageDao;
import cn.wenda.dao.QuestionDao;
import cn.wenda.dao.UserDao;
import cn.wenda.model.User;
import cn.wenda.service.MailSenderService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { WendaApplication.class })
public class WendaTest {

	@Autowired
	UserDao userDao;
	@Autowired
	QuestionDao questionDao;
	@Autowired
	MessageDao messageDao;
	@Autowired
	MailSenderService mailSenderService;

	@Test
	public void test() {
		User user=userDao.getUserById(2);
		mailSenderService.sendEmail("541994159@qq.com", user, "欢迎注册问答社区！");
		
	}
}
