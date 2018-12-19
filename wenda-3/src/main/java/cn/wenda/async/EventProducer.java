package cn.wenda.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.wenda.service.JedisAdapterService;
import cn.wenda.utils.RedisKeysUtil;

@Service
public class EventProducer {
	
	@Autowired
	JedisAdapterService jedisAdapterService;
	
	public boolean fireEvent(EventModel eventModel) {
		try {
			String json=JSONObject.toJSONString(eventModel);
			String key=RedisKeysUtil.getEventQueueKey();
			jedisAdapterService.lpush(key,json);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
