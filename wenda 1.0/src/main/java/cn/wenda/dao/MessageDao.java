package cn.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wenda.model.Message;

public interface MessageDao {

	void addMessage(Message message);

	void updateMessage(Message message);

	List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset,
			@Param("limit") int limit);

	int getConversationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

	/**
	 * 以conversationId为分组进行查询，得到每组内最新的一条消息记录
	 * @return
	 */
	List<Message> getConversationList(@Param("userId") int userId);

}
