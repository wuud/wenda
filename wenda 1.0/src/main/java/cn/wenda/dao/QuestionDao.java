package cn.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wenda.model.Question;

public interface QuestionDao {
	
	void insertQuestion(Question q);
	Question getQuestionById(Integer id);
	Question getQuestionByTitle(String title);
	
	void updateQuestion(Question q);
	void addQuestion(Question question);
	void updateCommentCount(@Param("commentCount")int commentCount,@Param("id")int questionId);
	
	Integer countAllQuestion();
	Integer countQuestionByUser(int userId);
	
	List<Question> getAllQuestion();
	List<Question> getQuestionByPage(@Param("pageSize")int pageSize,@Param("offsetNum")int offsetNum);
	List<Question> getUserAllQuestion(@Param("user_id")Integer user_id);
	List<Question> getUserQuestionsByPage(@Param("userId")int userId,@Param("pageSize")int pageSize,@Param("offsetNum")int offsetNum);

	List<Question> containsWord(String word);
}
