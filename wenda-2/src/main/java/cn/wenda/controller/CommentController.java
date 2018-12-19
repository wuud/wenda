package cn.wenda.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.Comment;
import cn.wenda.model.EntityType;
import cn.wenda.service.CommentService;

@Controller
public class CommentController {
	
	@Autowired
	CommentService commentService;
	@Autowired
	HostHolder hostHolder;

	@RequestMapping(value="/addComment",method=RequestMethod.POST)
	public String addComment(@RequestParam("content") String content,
							@RequestParam("questionId") int questionId) {
		Comment comment =new Comment();
		comment.setContent(content);
		comment.setCreatedDate(new Date());
		comment.setEntityId(questionId);
		comment.setEntityType(EntityType.ENTITY_QUESTION);
		if(hostHolder.getUser()==null) {
			return "redirect:/login";
		}
		comment.setUserId(hostHolder.getUser());
		commentService.addComment(comment);
		return "redirect:/question/"+questionId;
		
	}
}
