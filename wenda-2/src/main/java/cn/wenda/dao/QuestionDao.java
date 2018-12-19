package cn.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wenda.model.Question;

public interface QuestionDao {
	
	void insertQuestion(Question q);
	Question getQuestionById(Integer id);
	void updateQuestion(Question q);
	void addQuestion(Question question);
	void updateCommentCount(@Param("commentCount")int commentCount,@Param("id")int questionId);
	List<Question> getAllQuestion();
	List<Question> getUserAllQuestion(@Param("user_id")Integer user_id);
	

}
