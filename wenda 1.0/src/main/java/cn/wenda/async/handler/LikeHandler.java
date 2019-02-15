package cn.wenda.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.wenda.async.EventHandler;
import cn.wenda.async.EventModel;
import cn.wenda.async.EventType;
import cn.wenda.interceptor.HostHolder;
import cn.wenda.model.Message;
import cn.wenda.model.User;
import cn.wenda.service.MessageService;
import cn.wenda.service.UserService;
import cn.wenda.utils.Constants;
import cn.wenda.utils.WendaUtil;

/**
 * 点赞事件处理
 * @author wuu
 * 2018年12月20日
 */
@Component
public class LikeHandler implements EventHandler {
	@Autowired
	MessageService messageService;
	@Autowired
	UserService userService;
	@Autowired
	Constants constants;


	@Override
	public void doHandle(EventModel model) {
		Message message=new Message();
		message.setFromId(constants.systemId);
		message.setToId(model.getEntityOwnerId());
		message.setCreatedDate(new Date());
		message.setHasRead(0);
		message.setConversationId(message.getFromId()+"_"+message.getToId());
		User user=userService.getUserById(model.getActorId());
		message.setContent("用户"+user.getName()+"赞了你的评论，链接地址："+constants.hostName+"/question/"+model.getExts("questionId"));
		messageService.addMessage(message);
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.LIKE);
	}


	
	
	
	
	
	
	
}
