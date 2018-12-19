package cn.wenda.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.wenda.model.Message;
import cn.wenda.service.JedisAdapterService;
import cn.wenda.utils.RedisKeysUtil;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
	private static final Logger logger=LoggerFactory.getLogger(EventConsumer.class);
	@Autowired
	JedisAdapterService jedisAdapterService;

	private Map<EventType, List<EventHandler>> config = new HashMap<>();
	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
		if (beans != null) {
			for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
				List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
				for (EventType type : eventTypes) {
					if (!config.containsKey(type)) {
						config.put(type, new ArrayList<EventHandler>());
					}
					config.get(type).add(entry.getValue());
				}
			}
		}
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				String key = RedisKeysUtil.getEventQueueKey();
				List<String> events = jedisAdapterService.brpop(0, key);
				for(String s:events) {
					if(s.equals(key)) {
						continue;
					}
					JSONObject jo=JSON.parseObject(s);
					EventModel eventModel=JSON.parseObject(s, EventModel.class);
					if(!config.containsKey(eventModel.getType())) {
						logger.error("不能识别的事件");
						continue;
					}
					for(EventHandler handler:config.get(eventModel.getType())) {
						handler.doHandle(eventModel);
					}
				}

			}
		});
		thread.start();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

}
