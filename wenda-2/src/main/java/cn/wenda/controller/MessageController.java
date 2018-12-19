package cn.wenda.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.Message;
import cn.wenda.model.User;
import cn.wenda.service.MessageService;
import cn.wenda.service.UserService;
import cn.wenda.utils.WendaUtil;

@Controller
public class MessageController {

	@Autowired
	MessageService messageService;
	@Autowired
	UserService userService;
	@Autowired
	HostHolder hostHolder;

	@RequestMapping(value = "/msg/addMessage", method = RequestMethod.POST)
	@ResponseBody
	public String addMessage(@RequestParam("content") String content, @RequestParam("toName") String toName) {
		if (hostHolder.getUser() == null) {
			return WendaUtil.getJSONString(999, "未登录");
		}
		User user = userService.getUserByName(toName);
		if (user == null) {
			return WendaUtil.getJSONString(1, "用户不存在");
		}
		Message message = new Message();
		int fromId = hostHolder.getUser().getId();
		int toId = user.getId();
		message.setContent(content);
		message.setContent(content);
		message.setFromId(fromId);
		message.setToId(toId);
		message.setCreatedDate(new Date());
		message.setConversationId(
				fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
		messageService.addMessage(message);
		return WendaUtil.getJSONString(0);
	}

	@RequestMapping(value = "/msg/detail")
	public String conversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
		List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
		// 利用一个Map对象将message和前台所需要的user的headUrl等属性绑在一块传过去
		List<Map<String,Object>> messages = new ArrayList<>();
		for (Message m : conversationList) {
			Map<String,Object> map=new HashMap<>();
			//设置消息已读
			m.setHasRead(1);
			messageService.updateMessage(m);
			map.put("message", m);
			User user = userService.getUserById(m.getFromId());
			map.put("headUrl", user.getHead_url());
			map.put("userId", user.getId());
			messages.add(map);
		}
		model.addAttribute("messages", messages);
		return "letterDetail";
	}
	@RequestMapping(value="/msg/list")
	public String messageList(Model model) {
		int localUserId = hostHolder.getUser().getId();
		
		List<Map<String,Object>> conversations = new ArrayList<Map<String,Object>>();
		List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
		for (Message msg : conversationList) {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("conversation", msg);
			//聊天对方的id
			int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
			User user = userService.getUserById(targetId);
			map.put("user", user);
			map.put("unread", messageService.getConvesationUnreadCount(localUserId, msg.getConversationId()));getClass();
			conversations.add(map);
		}
		model.addAttribute("conversations", conversations);
		
		return "letter";

	}

}
