package cn.wenda.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wenda.async.EventModel;
import cn.wenda.async.EventProducer;
import cn.wenda.async.EventType;
import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.Comment;
import cn.wenda.model.EntityType;
import cn.wenda.model.Feed;
import cn.wenda.service.CommentService;
import cn.wenda.service.FeedService;
import cn.wenda.service.LikeService;
import cn.wenda.utils.WendaUtil;

/**
 * 实现赞、踩功能
 * 
 * @author wuu 2018年12月18日
 */
@Controller
public class LikeController {

	@Autowired
	LikeService likeService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	CommentService commentService;
	@Autowired
	EventProducer eventProducer;
	
	@RequestMapping(value="/like",method=RequestMethod.POST)
	@ResponseBody
	public String like(@RequestParam("commentId") int commentId) {
		if (hostHolder.getUser() == null) {
			return WendaUtil.getJSONString(999);
		}
		Comment comment =commentService.getCommentById(commentId);
		eventProducer.fireEvent(new EventModel(EventType.LIKE)
				.setActorId(hostHolder.getUser().getId()).setEntityOwnerId(comment.getUserId().getId())
				.setEntityId(commentId).setExts("questionId", String.valueOf(comment.getEntityId())));
		
		long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
				
		return WendaUtil.getJSONString(0, String.valueOf(likeCount));
	}
	
	@RequestMapping(value="/dislike",method=RequestMethod.POST)
	@ResponseBody
	public String dislike(@RequestParam("commentId") int commentId) {
		if (hostHolder.getUser() == null) {
			return WendaUtil.getJSONString(999);
		}
		long likeCount = likeService.dislike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
				
		return WendaUtil.getJSONString(0, String.valueOf(likeCount));
	}

}
