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
/**
 * 事件消费者，即对事件进行处理
 * @author wuu
 * 2018年12月22日
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
	private static final Logger logger=LoggerFactory.getLogger(EventConsumer.class);
	@Autowired
	JedisAdapterService jedisAdapterService;

	private Map<EventType, List<EventHandler>> config = new HashMap<>();
	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		//得到所有实现了EventHandler接口的类
		Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
		if (beans != null) {
			//对上面得到的map对象进行遍历
			for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
				//得到每个Handler对象支持的事件类型
				List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
				//一个handler可能支持处理多个事件类型，对他所能处理的事件类型进行遍历
				for (EventType type : eventTypes) {
					//将hanlder对象，按照它能处理的事件类型EventType进行分类
					if (!config.containsKey(type)) {
						config.put(type, new ArrayList<EventHandler>());
					}
					//将Handler按EventType进行分类，每一个type对应所有能处理他的handler类
					config.get(type).add(entry.getValue());
				}
			}
		}
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				String key = RedisKeysUtil.getEventQueueKey();
				//等待EventProducer向队列中添加事件
				//阻塞式pop，有元素就pop，没有就一直阻塞
				List<String> events = jedisAdapterService.brpop(0, key);
				for(String s:events) {
					if(s.equals(key)) {
						continue;
					}
					EventModel eventModel=JSON.parseObject(s, EventModel.class);
					if(!config.containsKey(eventModel.getType())) {
						logger.error("不能识别的事件");
						continue;
					}
					//对所有能处理该Type类型的Handler进行遍历，逐个执行
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
