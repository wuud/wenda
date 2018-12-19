package cn.wenda.interceptor;

import org.springframework.stereotype.Component;

import cn.wenda.model.User;
/**
 * 对{@link ThreadLocal}对象的一个简单封装
 * @author wuu
 * 2018年12月14日
 */
@Component
public class HostHolder {
	/**
	 * @ThreadLocal 官方文档解释
	 * 该类提供了线程局部 (thread-local) 变量。
	 * 这些变量不同于它们的普通对应物，因为访问某个变量（通过其 get 或 set 方法）的每个线程都有自己的局部变量，
	 * 它独立于变量的初始化副本。ThreadLocal 实例通常是类中的 private static 字段，
	 * 它们希望将状态与某一个线程（例如，用户 ID 或事务 ID）相关联。
	 * @see 简单来说，就是为每个线程复制一个User副本，让各个线程可以独立的去访问User变量
	 */
	private static ThreadLocal<User> users = new ThreadLocal<>();

	/**
	 * 将此线程局部变量的当前线程副本中的值设置为指定值。
	 * @param user
	 */
	public void setUser(User user) {
		users.set(user);
	}
	/**
	 * 返回此线程局部变量的当前线程副本中的值。
	 * @return user
	 */
	public User getUser() {
		return users.get();
	}
	/**
	 * 移除此线程局部变量当前线程的值。
	 */
	public void clear() {
		users.remove();
	}

}
