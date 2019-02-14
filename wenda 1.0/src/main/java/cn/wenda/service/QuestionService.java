package cn.wenda.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
	public List<Question> containsWord(String word) {
		String newWord="%"+word+"%";
		return questionDao.containsWord(newWord);
	}
	public void setPage(int page, int pageNum,Model model) {
		List<Integer> list = new ArrayList<>();
		int pageButtonNum = 7;
		if (page + pageButtonNum < pageNum) {
			for (int i = pageButtonNum; i >= 0; i--) {
				list.add(page + i);
			}
		} else if (pageNum < pageButtonNum) {
			for (int i = 0; i < pageNum; i++) {
				list.add(i);
			}
		} else {
			for (int i = pageNum - 1; i >= pageNum - pageButtonNum - 1; i--) {
				list.add(i);
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("pageList", list);
		model.addAttribute("pageNum", pageNum);
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
	public List<Question> getQuestionByPage(int page,int pageSize){
		int offsetNum=page*pageSize;
		return questionDao.getQuestionByPage(pageSize, offsetNum);
	}
	public List<Question> getUserQuestionsByPage(int userId,int page,int pageSize){
		int offsetNum=page*pageSize;
		return questionDao.getUserQuestionsByPage(userId,pageSize, offsetNum);
	}
	
	
	public List<Question> getUserAllQuestion(Integer user_id){
		return questionDao.getUserAllQuestion(user_id);
	}
	public Question getQuestionById(Integer id) {
		return questionDao.getQuestionById(id);
	}
	public Integer countAllQuestion() {
		return questionDao.countAllQuestion();
	}
	public Integer countQuestionByUser(int userId) {
		return questionDao.countQuestionByUser(userId);
	}

}
