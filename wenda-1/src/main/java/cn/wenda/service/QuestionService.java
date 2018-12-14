package cn.wenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wenda.dao.QuestionDao;
import cn.wenda.model.Question;

@Service
public class QuestionService {
	
	@Autowired
	QuestionDao questionDao;
	
	public List<Question> getAllQuestion(){
		return questionDao.getAllQuestion();
	}
	
	

}
