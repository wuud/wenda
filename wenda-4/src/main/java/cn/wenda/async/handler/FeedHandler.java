package cn.wenda.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.wenda.async.EventHandler;
import cn.wenda.async.EventModel;
import cn.wenda.async.EventType;
import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.EntityType;
import cn.wenda.model.Feed;
import cn.wenda.model.Question;
import cn.wenda.model.User;
import cn.wenda.service.FeedService;
import cn.wenda.service.FollowService;
import cn.wenda.service.JedisAdapterService;
import cn.wenda.service.QuestionService;
import cn.wenda.service.UserService;
import cn.wenda.utils.RedisKeysUtil;

/**
 * 好友动态，用户关注的人有什么最新动态都要发一个事件
 * 
 * @author wuu 2018年12月21日
 */
@Component
public class FeedHandler implements EventHandler {

	@Autowired
	UserService userService;
	@Autowired
	QuestionService questionService;
	@Autowired
	FeedService feedService;
	@Autowired
	FollowService followService;
	@Autowired
	JedisAdapterService jedisAdapter;
	@Autowired
	HostHolder hostHolder;

	@Override
	public void doHandle(EventModel model) {
		System.out.println("feedHandler start");
		Feed feed = new Feed();
		feed.setCreatedDate(new Date());
		feed.setType(model.getType().getValue());
		System.out.println("hostHolder.getUser():" + hostHolder.getUser());
		feed.setUserId(hostHolder.getUser().getId());
		feed.setData(buildData(model));
		feedService.addFeed(feed);

		List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER, hostHolder.getUser().getId(),
				Integer.MAX_VALUE);
		for (Integer follower : followers) {
			String timelineKey = RedisKeysUtil.getTimelineKey(follower);
			jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
		}
	}

	private String buildData(EventModel model) {
		Map<String, String> map = new HashMap<>();
		User actor = userService.getUserById(model.getActorId());
		if (actor == null)
			return null;
		map.put("userName", actor.getName());
		map.put("userHead", actor.getHead_url());
		map.put("userId", String.valueOf(actor.getId()));
		if (model.getType() == EventType.COMMENT && model.getEntityType() == EntityType.ENTITY_QUESTION) {
			Question question = questionService.getQuestionById(model.getEntityId());
			if (question == null)
				return null;
			map.put("questionId", String.valueOf(question.getId()));
			map.put("questionTitle", question.getTitle());
			return JSON.toJSONString(map);

		}
		if (model.getType() == EventType.FOLLOW && model.getEntityType() == EntityType.ENTITY_USER) {
			User followee = userService.getUserById(model.getEntityId());
			if (followee == null)
				return null;
			map.put("followeeId", String.valueOf(followee.getId()));
			map.put("followeeName", followee.getName());
		}
		return null;
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(new EventType[] { EventType.FOLLOW, EventType.COMMENT, EventType.LIKE });
	}

}
