package cn.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wenda.model.Message;

public interface MessageDao {

	void addMessage(Message message);
	
	void updateMessage(Message message);

	List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset,
			@Param("limit") int limit);

	int getConversationUnreadCount(@Param("userId") int userId,
			@Param("conversationId") String conversationId);
	
	 List<Message> getConversationList(@Param("userId") int userId,
             @Param("offset") int offset, @Param("limit") int limit);
	 

}
