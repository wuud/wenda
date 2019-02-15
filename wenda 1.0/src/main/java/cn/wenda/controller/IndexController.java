package cn.wenda.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.EntityType;
import cn.wenda.model.Question;
import cn.wenda.model.User;
import cn.wenda.service.CommentService;
import cn.wenda.service.FollowService;
import cn.wenda.service.QuestionService;
import cn.wenda.service.UserService;
import cn.wenda.utils.Constants;

@Controller
public class IndexController {
	@Autowired
	HostHolder hostHolder;
	@Autowired
	QuestionService questionService;
	@Autowired
	UserService userService;
	@Autowired
	CommentService commentService;
	@Autowired
	FollowService followService;
	@Autowired
	Constants constants;

	@RequestMapping(value = { "/" })
	public String index(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		List<Question> questions = questionService.getQuestionByPage(page, constants.pageSize);
		int pageNum = (questionService.countAllQuestion() / 10) + 1;

		questionService.setPage(page, pageNum, model);
		model.addAttribute("questions", questions);
		
		return "index";
	}

	@RequestMapping("/user/{id}")
	public String userIndex(Model model, @PathVariable("id") Integer user_id,
			@RequestParam(value = "page", defaultValue = "0") int page) {
		
		if(hostHolder.getUser()==null) {
			return "redirect:/login?next=/user/"+user_id;
		}
		List<Question> questions = questionService.getUserQuestionsByPage(user_id, page, constants.pageSize);
		model.addAttribute("questions", questions);
		User user = userService.getUserById(user_id);
		if (user == null) {
			return "redirect:/error";
		}
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("commentCount", commentService.getUserCommentCount(user_id));
		map.put("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, user_id));
		map.put("followeeCount", followService.getFolloweeCount(user_id, EntityType.ENTITY_USER));
		if (hostHolder.getUser() != null) {
			//如果当前登录用户正在查看自己主页，则不显示  关注/取消关注   按钮
			if (hostHolder.getUser().getId() == user_id) {
				map.put("display", false);
			} else {
				map.put("display", true);
			}
			//是否关注了用户
			map.put("followed", followService.isFollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, user_id));
		} else {
			map.put("followed", false);
		}
		model.addAttribute("profileUser", map);
		
		int articleNum = questionService.countQuestionByUser(user_id);
		int pageNum = (int) Math.ceil((articleNum / 10.0));
		questionService.setPage(page, pageNum, model);
		
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("userId", user_id);
		return "profile";
	}



}
