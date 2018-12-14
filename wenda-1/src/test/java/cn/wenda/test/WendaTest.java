package cn.wenda.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.wenda.WendaApplication;
import cn.wenda.dao.QuestionDao;
import cn.wenda.dao.UserDao;
import cn.wenda.model.Question;
import cn.wenda.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {WendaApplication.class})
public class WendaTest {
	
	@Autowired
	UserDao userDao;
	@Autowired
	QuestionDao questionDao;

	@Test
	public void test() {
		List<Question> list = questionDao.getAllQuestion();
		System.out.println(list);
	}
}






