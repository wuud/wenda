package cn.wenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wenda.model.Question;
import cn.wenda.service.QuestionService;

@Controller
public class ExploreController {
	
	@Autowired
	QuestionService questionService;
	
	@RequestMapping(value="/explore")
	public String randomPush(Model model) {
		int questionNum=questionService.countAllQuestion();
		List<Question> questionList=new ArrayList<>();
		for(int i=0;i<10;i++) {
			int randomId=(int)(Math.random()*(questionNum))+1;
			questionList.add(questionService.getQuestionById(randomId));
		}
		model.addAttribute("questions",questionList);
		return "explore";
	}

}
