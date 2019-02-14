package cn.wenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.wenda.model.Question;
import cn.wenda.service.QuestionService;

@Controller
public class SearchController {
	
	@Autowired
	QuestionService questionService;
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String search(@RequestParam("word")String word,Model model) {
		System.out.println(word);
		List<Question> questions = questionService.containsWord(word);
		model.addAttribute("questions",questions);
		return "explore";
	}

}
