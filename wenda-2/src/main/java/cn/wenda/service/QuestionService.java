package cn.wenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import cn.wenda.dao.QuestionDao;
import cn.wenda.model.Question;

@Service
public class QuestionService {
	
	@Autowired
	QuestionDao questionDao;
	@Autowired
	SensitiveWordService sensitiveWordService;
	
	
	public void addQuestion(Question question) {
		//做html标签的过滤，即将用于问题中的html标签进行转义
		question.setContent(HtmlUtils.htmlEscape(question.getContent()));
		question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
		// 敏感词过滤
        question.setTitle(sensitiveWordService.filter(question.getTitle()));
        question.setContent(sensitiveWordService.filter(question.getContent()));
        questionDao.addQuestion(question);
	}
	public void updateQuestion(Question question) {
		questionDao.updateQuestion(question);
	}
	public void updateCommentCount(int commentCount,int questionId) {
		questionDao.updateCommentCount(commentCount, questionId);
	}
	
	public List<Question> getAllQuestion(){
		return questionDao.getAllQuestion();
	}
	public List<Question> getUserAllQuestion(Integer user_id){
		return questionDao.getUserAllQuestion(user_id);
	}
	public Question getQuestionById(Integer id) {
		return questionDao.getQuestionById(id);
	}
	
	

}
