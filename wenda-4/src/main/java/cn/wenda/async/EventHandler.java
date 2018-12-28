package cn.wenda.async;

import java.util.List;
/**
 * 事件处理接口，所有的事件处理类都必须实现这个接口
 * @author wuu
 * 2018年12月20日
 */
public interface EventHandler {
	/**
	 * 执行事件处理
	 * @param model
	 */
	void doHandle(EventModel model);
	/**
	 * 为事件处理类设置，支持的事件类型
	 * @return
	 */
	List<EventType> getSupportEventTypes();

}
