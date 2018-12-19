package cn.wenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wenda.dao.QuestionDao;
import cn.wenda.model.Question;
import cn.wenda.service.QuestionService;

@Controller
public class IndexController {
	
	@Autowired
	QuestionService questionService;	
	
	@RequestMapping(value= {"/"})
	public String index(Model model) {
		List<Question> questions = questionService.getAllQuestion();
		model.addAttribute("questions",questions);
		return "index";
	}
	@RequestMapping("/user/{id}")
	public String userIndex(Model model,@PathVariable("id") Integer user_id) {
		List<Question> questions=questionService.getUserAllQuestion(user_id);
		model.addAttribute("questions",questions);
		return "index";
	}

}
