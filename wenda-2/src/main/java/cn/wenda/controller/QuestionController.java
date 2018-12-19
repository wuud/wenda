package cn.wenda.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.Comment;
import cn.wenda.model.EntityType;
import cn.wenda.model.Question;
import cn.wenda.service.CommentService;
import cn.wenda.service.QuestionService;
import cn.wenda.utils.WendaUtil;

@Controller
public class QuestionController {
	@Autowired
	QuestionService questionService;
	@Autowired
	CommentService commentService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	IndexController indexController;
	
	@RequestMapping(value="/question/{id}")
	public String getDetails(Model model,@PathVariable("id") int id) {
		Question question = questionService.getQuestionById(id);
		model.addAttribute("question",question);
		List<Comment> comments = commentService.getCommentsByEntity(id, EntityType.ENTITY_QUESTION);
		model.addAttribute("comments",comments);
		int count=commentService.countComment(question.getId(), EntityType.ENTITY_QUESTION);
		questionService.updateCommentCount(count,question.getId());
		return "details";
		
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
			return WendaUtil.getJSONString(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return WendaUtil.getJSONString("添加问题失败", 1);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
