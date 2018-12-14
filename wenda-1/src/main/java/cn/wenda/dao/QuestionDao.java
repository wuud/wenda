package cn.wenda.dao;

import java.util.List;

import cn.wenda.model.Question;

public interface QuestionDao {
	
	public void insertQuestion(Question q);
	public Question getQuestionById(Integer id);
	public void updateQuestion(Question q);
	public List<Question> getAllQuestion();
	public List<Question> getUserAllQuestion(Integer user_id);
	

}
