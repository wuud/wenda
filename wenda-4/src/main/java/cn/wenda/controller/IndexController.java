package cn.wenda.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.EntityType;
import cn.wenda.model.Question;
import cn.wenda.model.User;
import cn.wenda.service.CommentService;
import cn.wenda.service.FollowService;
import cn.wenda.service.QuestionService;
import cn.wenda.service.UserService;

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
	
	@RequestMapping(value= {"/"})
	public String index(Model model) {
		System.out.println("index:"+hostHolder.getUser());
		List<Question> questions = questionService.getAllQuestion();
		model.addAttribute("questions",questions);
		return "index";
	}
	@RequestMapping("/user/{id}")
	public String userIndex(Model model,@PathVariable("id") Integer user_id) {
		List<Question> questions=questionService.getUserAllQuestion(user_id);
		model.addAttribute("questions",questions);
		User user=userService.getUserById(user_id);
		if(user==null) {
			return "redirect:/error";
		}
		Map<String,Object> map=new HashMap<>();
		map.put("user",user);
		map.put("commentCount", commentService.getUserCommentCount(user_id));
        map.put("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, user_id));
        map.put("followeeCount", followService.getFolloweeCount(user_id, EntityType.ENTITY_USER));
        if (hostHolder.getUser() != null) {
            map.put("followed", followService.isFollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, user_id));
        } else {
            map.put("followed", false);
        }
        model.addAttribute("profileUser", map);
		return "profile";
	}

}
