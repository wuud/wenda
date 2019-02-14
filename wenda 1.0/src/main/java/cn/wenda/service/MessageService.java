package cn.wenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wenda.dao.MessageDao;
import cn.wenda.model.Message;

@Service
public class MessageService {
	@Autowired
	MessageDao messageDao;
	@Autowired
	SensitiveWordService sensitiveWordService;

	public void addMessage(Message message) {
		message.setContent(sensitiveWordService.filter(message.getContent()));
		messageDao.addMessage(message);
	}
	public void updateMessage(Message message) {
		messageDao.updateMessage(message);
	}

	public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
		return messageDao.getConversationDetail(conversationId, offset, limit);
	}

	public List<Message> getConversationList(int userId, int offset, int limit) {
		List<Message> list = messageDao.getConversationList(userId);
		return list;
	}

	public int getConvesationUnreadCount(int userId, String conversationId) {
		return messageDao.getConversationUnreadCount(userId, conversationId);
	}

}
