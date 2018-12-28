package cn.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wenda.model.Comment;

public interface CommentDao {
	
	void addComment(Comment comment);
	
	List<Comment> getCommentsByEntity(@Param("entityId")int entityId,@Param("entityType")int entityType);
	
	Comment getCommentById(Integer id);
	
	void updateCommentStatus(@Param("id")int commentId,@Param("status") int status);

	int countComment(@Param("entityId")int entityId,@Param("entityType")int entityType);
	
	int getUserCommentCount(int userId);
}
