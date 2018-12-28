package cn.wenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.EntityType;
import cn.wenda.model.Feed;
import cn.wenda.service.FeedService;
import cn.wenda.service.FollowService;
import cn.wenda.service.JedisAdapterService;
import cn.wenda.utils.RedisKeysUtil;

@Controller
public class FeedController {

	@Autowired
	FeedService feedService;

	@Autowired
	FollowService followService;

	@Autowired
	HostHolder hostHolder;

	@Autowired
	JedisAdapterService jedisAdapter;

	@RequestMapping(path = { "/pushfeeds" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String getPush(Model model) {
		int localUserId = hostHolder.getUser() == null ? 0 : hostHolder.getUser().getId();
		List<String> feedIds = jedisAdapter.lrange(RedisKeysUtil.getTimelineKey(localUserId), 0, 10);
		List<Feed> feeds = new ArrayList<>();
		for (String s : feedIds) {
			Feed feed = feedService.getFeedById(Integer.parseInt(s));
			if (feed != null) {
				feeds.add(feed);
			}
		}
		model.addAttribute("feeds", feeds);
		return "feeds";
	}

	@RequestMapping(path = { "/pullfeeds" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String getPull(Model model) {
		int localUserId = hostHolder.getUser() == null ? 0 : hostHolder.getUser().getId();
		List<Integer> followees = new ArrayList<>();
		if (localUserId != 0) {
			// 关注的人
			followees = followService.getFollowees(localUserId, EntityType.ENTITY_USER, Integer.MAX_VALUE);
		}
		List<Feed> feeds = feedService.getUserFeeds(followees,Integer.MAX_VALUE,10);
		model.addAttribute("feeds", feeds);
		return "feeds";
	}

}
