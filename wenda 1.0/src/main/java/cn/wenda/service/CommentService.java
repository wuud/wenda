package cn.wenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import cn.wenda.dao.CommentDao;
import cn.wenda.model.Comment;

@Service
public class CommentService {

	@Autowired
	CommentDao commentDao;
	@Autowired
	SensitiveWordService sensitiveWordService;

	public List<Comment> getCommentsByEntity(int entityId, int entityType) {
		return commentDao.getCommentsByEntity(entityId, entityType);
	}

	public Comment getCommentById(Integer id) {
		return commentDao.getCommentById(id);
	}

	public void addComment(Comment comment) {
		comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
		comment.setContent(sensitiveWordService.filter(comment.getContent()));
		commentDao.addComment(comment);

	}

	public int countComment(int entityId, int entityType) {
		return commentDao.countComment(entityId, entityType);
	}

	public int getUserCommentCount(int userId) {
		return commentDao.getUserCommentCount(userId);
	}

}
