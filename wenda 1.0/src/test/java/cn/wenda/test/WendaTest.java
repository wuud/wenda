package cn.wenda.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.wenda.WendaApplication;
import cn.wenda.dao.FeedDao;
import cn.wenda.dao.MessageDao;
import cn.wenda.dao.QuestionDao;
import cn.wenda.dao.UserDao;
import cn.wenda.model.Feed;

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
	FeedDao feedDao;
	@Test
	public void test() {
		
	}
}
