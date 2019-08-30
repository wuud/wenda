package cn.wenda.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.wenda.model.EntityType;
import cn.wenda.model.Question;
import cn.wenda.service.FollowService;
import cn.wenda.service.QuestionService;
import cn.wenda.service.SearchService;
import cn.wenda.service.UserService;

@Controller
public class SearchController {
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	SearchService searchService;
	@Autowired
	FollowService followService;
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String search(Model model, @RequestParam("q") String keyword,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "count", defaultValue = "10") int count) {
		try {
			List<Question> questionList = searchService.searchQuestion(keyword, offset, count,"<em>", "</em>");
			List<Question> questions=new ArrayList<>();
            for (Question question : questionList) {
                Question q = questionService.getQuestionById(question.getId());
                if (question.getContent() != null) {
                    q.setContent(question.getContent());
                }
                if (question.getTitle() != null) {
                    q.setTitle(question.getTitle());
                }
                questions.add(q);
            }
            model.addAttribute("questions",questions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "result";
	}

}
