package cn.wenda.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.wenda.service.JedisAdapterService;
import cn.wenda.utils.RedisKeysUtil;
/**
 * 事件生产者，每触发一个事件，就放入redis的队列中，交由EventConsumer去处理
 * @author wuu
 * 2018年12月22日
 */
@Service
public class EventProducer {
	
	@Autowired
	JedisAdapterService jedisAdapterService;
	
	public boolean fireEvent(EventModel eventModel) {
		try {
			System.out.println("fireEvent");
			String json=JSONObject.toJSONString(eventModel);
			String key=RedisKeysUtil.getEventQueueKey();
			jedisAdapterService.lpush(key,json);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
