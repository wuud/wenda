package cn.wenda.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.wenda.async.EventModel;
import cn.wenda.async.EventProducer;
import cn.wenda.async.EventType;
import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.Comment;
import cn.wenda.model.EntityType;
import cn.wenda.model.Feed;
import cn.wenda.model.Question;
import cn.wenda.service.CommentService;
import cn.wenda.service.FeedService;
import cn.wenda.service.QuestionService;
import cn.wenda.utils.Constants;

/**
 * 评论功能，即用户对问题的回答，和对回答的评论
 * 
 * @author wuu 2018年12月20日
 */
@Controller
public class CommentController {

	@Autowired
	CommentService commentService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	EventProducer eventProducer;
	@Autowired
	FeedService feedService;
	@Autowired
	QuestionService questionService;
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
		
		Question question = questionService.getQuestionById(questionId);
		String message="发布了新评论在："+ question.getTitle();
		String url=Constants.HOST_NAME+"/question"+questionId;
		feedService.addFeed(new Feed(hostHolder.getUser().getId(),message,new Date(),url));
		
		eventProducer.fireEvent(new EventModel(EventType.COMMENT)
				.setActorId(comment.getUserId().getId()).setEntityId(questionId));
		
		return "redirect:/question/"+questionId;
		
	}
}
