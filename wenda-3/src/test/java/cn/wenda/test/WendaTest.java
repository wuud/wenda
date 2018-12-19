package cn.wenda.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.wenda.WendaApplication;
import cn.wenda.async.EventModel;
import cn.wenda.dao.QuestionDao;
import cn.wenda.dao.UserDao;
import cn.wenda.service.JedisAdapterService;
import cn.wenda.utils.RedisKeysUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { WendaApplication.class })
public class WendaTest {

	@Autowired
	UserDao userDao;
	@Autowired
	QuestionDao questionDao;
	@Autowired
	JedisAdapterService jedisAdapterService;

	@Test
	public void test() {
		String key = RedisKeysUtil.getEventQueueKey();
		List<String> events = jedisAdapterService.brpop(0, key);
		for (String s : events) {
			EventModel eventModel = JSON.parseObject(s, EventModel.class);
			System.out.println("eventModel" + eventModel);
		}
	}
}
