     package cn.wenda.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wenda.async.EventModel;
import cn.wenda.async.EventProducer;
import cn.wenda.async.EventType;
import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.EntityType;
import cn.wenda.model.Feed;
import cn.wenda.model.Question;
import cn.wenda.model.User;
import cn.wenda.service.CommentService;
import cn.wenda.service.FeedService;
import cn.wenda.service.FollowService;
import cn.wenda.service.QuestionService;
import cn.wenda.service.UserService;
import cn.wenda.utils.Constants;
import cn.wenda.utils.WendaUtil;

/**
 * 关注功能
 * 
 * @author wuu 2018年12月20日
 */
@Controller
public class FollowController {
	@Autowired
	FollowService followService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	UserService userService;
	@Autowired
	QuestionService questionService;
	@Autowired
	CommentService commentServcie;
	@Autowired
	FeedService feedService;
	@Autowired
	EventProducer eventProducer;

	/**
	 * 实现关注用户功能
	 * 
	 * @param userId
	 */
	@RequestMapping(value = "/followUser", method = RequestMethod.POST)
	@ResponseBody
	public String followUser(@RequestParam("userId") int userId) {
		if (hostHolder.getUser() == null) {
			return WendaUtil.getJSONString(999);
		}
		if (userService.getUserById(userId) == null) {
			return WendaUtil.getJSONString(1, "用户不存在");
		}
		boolean b = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
		
		
		User followedUser = userService.getUserById(userId);
		String message="关注了用户"+followedUser.getName();
		String url=Constants.HOST_NAME+"/user/"+userId;
		feedService.addFeed(new Feed(hostHolder.getUser().getId(),message,new Date(),url));
		
		//当用户关注了某个用户，告诉被关注的用户谁关注了它
		eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
				.setActorId(hostHolder.getUser().getId()).setEntityId(userId)
				.setEntityOwnerId(userId).setEntityType(EntityType.ENTITY_USER));

		// 返回关注的人数
		return WendaUtil.getJSONString(b ? 0 : 1,
				String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));
	}

	/**
	 * 取消关注用户
	 * 
	 * @param userId
	 */
	@RequestMapping(value = "/unfollowUser", method = RequestMethod.POST)
	@ResponseBody
	public String unfollowUser(@RequestParam("userId") int userId) {
		if (hostHolder.getUser() == null) {
			return WendaUtil.getJSONString(999);
		}
		if (userService.getUserById(userId) == null) {
			return WendaUtil.getJSONString(1, "用户不存在");
		}
		boolean b = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
		// 返回关注的人数
		return WendaUtil.getJSONString(b ? 0 : 1,
				String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));
	}

	/**
	 * 实现关注问题
	 * 
	 * @param questionId
	 */
	@RequestMapping(value = "/followQuestion", method = RequestMethod.POST)
	@ResponseBody
	public String followQuestion(@RequestParam("questionId") int questionId) {
		if (hostHolder.getUser() == null) {
			return WendaUtil.getJSONString(999);
		}
		if (questionService.getQuestionById(questionId) == null) {
			return WendaUtil.getJSONString(1, "问题不存在");
		}
		boolean b = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);
		//当用户发布的问题被关注，告诉问题发布者，他发布的问题被谁关注了。
		eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
				.setActorId(questionId).setEntityId(questionId)
				.setEntityOwnerId(questionService.getQuestionById(questionId).getUser_id().getId())
				.setEntityType(EntityType.ENTITY_QUESTION));
		
		Question question = questionService.getQuestionById(questionId);
		String message="关注了问题："+question.getTitle();
		String url=Constants.HOST_NAME+"/question/"+question.getId();
		feedService.addFeed(new Feed(hostHolder.getUser().getId(),message,new Date(),url));
		
		Map<String, Object> info = new HashMap<>();
		info.put("headUrl", hostHolder.getUser().getHead_url());
		info.put("name", hostHolder.getUser().getName());
		info.put("id", hostHolder.getUser().getId());
		info.put("count", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionId));
		return WendaUtil.getJSONString(b ? 0 : 1, info);

	}

	/**
	 * 实现取消关注问题
	 * 
	 * @param questionId
	 */
	@RequestMapping(value = "/unfollowQuestion", method = RequestMethod.POST)
	@ResponseBody
	public String unfollowQuestion(@RequestParam("questionId") int questionId) {
		if (hostHolder.getUser() == null) {
			return WendaUtil.getJSONString(999);
		}
		if (questionService.getQuestionById(questionId) == null) {
			return WendaUtil.getJSONString(1, "问题不存在");
		}
		boolean b = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);

		Map<String, Object> info = new HashMap<>();
		info.put("id", hostHolder.getUser().getId());
		info.put("count", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionId));
		return WendaUtil.getJSONString(b ? 0 : 1, info);
	}

	/**
	 * 跳转到用户所有的粉丝列表
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/{uid}/followers")
	public String followers(Model model, @PathVariable("uid") int userId) {
		if (hostHolder.getUser() == null) {
			return "redirect:/login";
		}
		if (userService.getUserById(userId) == null) {
			String message = "用户不存在！";
			return "redirect:/error?msg=" + message;
		}
		int count = (int) followService.getFollowerCount(EntityType.ENTITY_USER, userId);
		List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER,userId,count);
		// 将用户粉丝的用户信息传给前端
		model.addAttribute("followers", getUsersInfo(hostHolder.getUser().getId(), followers));
		model.addAttribute("followerCount", count);
		model.addAttribute("curUser", userService.getUserById(userId));
		return "followers";
	}

	/**
	 * 跳转到用户的关注列表
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/{uid}/followees")
	public String followees(Model model, @PathVariable("uid") int userId) {
		if (hostHolder.getUser() == null) {
			return "redirect:/login";
		}
		int count = (int) followService.getFolloweeCount(userId, EntityType.ENTITY_USER);
		List<Integer> followees = followService.getFollowees(userId, EntityType.ENTITY_USER, count);
		// 将用户关注的用户信息传给前端
		model.addAttribute("followees", getUsersInfo(hostHolder.getUser().getId(), followees));
		model.addAttribute("followeeCount",count);
		model.addAttribute("curUser", userService.getUserById(userId));
		return "followees";
	}

	/**
	 * 根据list内的用户id，获得所有用户详细信息
	 * 
	 * @param localUserId 当前登录用户的id
	 * @param userIds     用户id的集合
	 * @return
	 */
	private List<Map<String, Object>> getUsersInfo(int localUserId, List<Integer> userIds) {
		List<Map<String, Object>> userInfos = new ArrayList<>();
		for (Integer uid : userIds) {
			User user = userService.getUserById(uid);
			if (user == null) {
				continue;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("user", user);
			map.put("commentCount", commentServcie.getUserCommentCount(uid));
			map.put("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, uid));
			map.put("followeeCount", followService.getFolloweeCount(uid, EntityType.ENTITY_USER));
			if (localUserId != 0) {
				map.put("followed", followService.isFollow(localUserId, EntityType.ENTITY_USER, uid));
			} else {
				map.put("followed", false);
			}
			userInfos.add(map);
		}
		return userInfos;
	}

}
