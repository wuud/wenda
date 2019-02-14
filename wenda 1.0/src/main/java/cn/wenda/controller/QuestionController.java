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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.Comment;
import cn.wenda.model.EntityType;
import cn.wenda.model.Feed;
import cn.wenda.model.Question;
import cn.wenda.model.User;
import cn.wenda.service.CommentService;
import cn.wenda.service.FeedService;
import cn.wenda.service.FollowService;
import cn.wenda.service.LikeService;
import cn.wenda.service.QuestionService;
import cn.wenda.service.UserService;
import cn.wenda.utils.Constants;
import cn.wenda.utils.WendaUtil;

@Controller
public class QuestionController {
	@Autowired
	QuestionService questionService;
	@Autowired
	CommentService commentService;
	@Autowired
	LikeService likeService;
	@Autowired
	FollowService followService;
	@Autowired
	UserService userService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	FeedService feedService;

	@RequestMapping(value = "/question/{id}")
	public String getDetails(Model model, @PathVariable("id") int id) {
		Question question = questionService.getQuestionById(id);
		model.addAttribute("question", question);
		List<Comment> commentList = commentService.getCommentsByEntity(id, EntityType.ENTITY_QUESTION);
		int commentCount = commentService.countComment(question.getId(), EntityType.ENTITY_QUESTION);
		questionService.updateCommentCount(commentCount, question.getId());
		List<Map<String, Object>> comments = new ArrayList<>();
		for (Comment c : commentList) {
			Map<String, Object> map = new HashMap<>();
			map.put("comment", c);
			long likeCount = likeService.getLikeCount(EntityType.ENTITY_COMMENT, c.getId());
			map.put("likeCount", likeCount);
			if (hostHolder.getUser() == null) {
				map.put("likeStatus", 0);
			} else {
				int likeStatus = likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,
						c.getId());
				map.put("likeStatus", likeStatus);
			}
			comments.add(map);
		}
		model.addAttribute("comments", comments);
		long followersCount=followService.getFollowerCount(EntityType.ENTITY_QUESTION, id);
		boolean followed=followService.isFollow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, id);
		model.addAttribute("followersCount",followersCount);
		model.addAttribute("followed",followed);
		//得到最后10个关注该问题的用户
		List<Integer> users=followService.getFollowers(EntityType.ENTITY_QUESTION, id, 10);
		List<Map<String,Object>> followers=new ArrayList<>();
		for(Integer i:users) {
			Map<String,Object> map=new HashMap<>();
			User user = userService.getUserById(i);
			if(user==null) {
				continue;
			}
			map.put("headUrl", user.getHead_url());
			map.put("name", user.getName());
			map.put("id",i);
			followers.add(map);
		}
		model.addAttribute("followers",followers);
		return "detail";

	}

	@RequestMapping(value = "/question/add")
	@ResponseBody
	public String addQuestion(Model model, @RequestParam("title") String title,
			@RequestParam("content") String content) {
		try {
			Question question = new Question();
			question.setComment_count(0);
			question.setContent(content);
			question.setTitle(title);
			question.setCreated_date(new Date());
			if (hostHolder.getUser() == null) {
				return WendaUtil.getJSONString(999);
			} else {
				question.setUser_id(hostHolder.getUser());
			}
			questionService.addQuestion(question);
			
			String message="提出了新问题："+question.getTitle();
			String url=Constants.HOST_NAME+"/question/"+question.getId();
			feedService.addFeed(new Feed(hostHolder.getUser().getId(), message, new Date(),url));
			return WendaUtil.getJSONString(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return WendaUtil.getJSONString(1, "添加问题失败");
	}

}
