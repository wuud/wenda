package cn.wenda.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wenda.dao.TicketDao;
import cn.wenda.dao.UserDao;
import cn.wenda.model.Ticket;
import cn.wenda.model.User;
import cn.wenda.utils.WendaUtil;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	@Autowired
	TicketDao ticketDao;
	
	/**
	 * 实现注册并登录
	 * @param name
	 * @param password
	 * @return Map<String,String>
	 * map内携带了ticket或error信息，可以传给前端界面
	 */
	public Map<String, String> register(String name, String password) {
		Map<String, String> map = new HashMap<>();
		//数据校验
		if (StringUtils.isBlank(name)) {
			map.put("error", "用户名为空");
			return map;
		} else if (StringUtils.isBlank(password)) {
			map.put("error", "密码为空");
			return map;
		} else if (userDao.getUserByName(name) != null) {
			map.put("error", "用户已被注册");
			return map;
		}
		//向数据库添加一条user数据
		User user = new User(name, password);
		user.setHead_url(String.format("http://images.nowcoder.com/head/%dt.png", (int) (Math.random() * 1000)));
		user.setSalt(UUID.randomUUID().toString().substring(0, 5));
		user.setPassword(WendaUtil.MD5(password + user.getSalt()));
		userDao.insertUser(user);
		//利用ticket完成用户的登录功能
		String ticket=addTicket(user.getId());
		map.put("ticket", ticket);
		return map;
	}
	/**
	 * 实现登录功能
	 * @param name
	 * @param password
	 * @return Map<String,String>
	 * map内携带了ticket或error信息，可以传给前端界面
	 */
	public Map<String, String> login(String name, String password) {
		Map<String, String> map = new HashMap<>();
		User user = userDao.getUserByName(name);
		if (user==null) {
			map.put("error", "用户名不存在");
			return map;
		} else if (StringUtils.isBlank(password)) {
			map.put("error", "密码为空");
			return map;
		} else if (!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
			map.put("error", "用户名与密码不一致");
			return map;
		}
		String ticket=addTicket(user.getId());
		map.put("ticket", ticket);
		return map;
	}
	/**
	 * 实现用户的注销
	 * 当用户登出的时候删除当前ticket或将ticket的状态设置为1，
	 * ticket失效后，用户的登录状态也就不再存在
	 * @param ticket
	 */
	public void logout(String ticket) {
		ticketDao.updateStatus(ticket, 1);
	}
	/**
	 * 每当用户登录的时候，就为当前用户生成一条ticket来作为用户的唯一标识
	 * ticket会被放到浏览器cookie里面
	 * 并向数据库中插入一条ticket数据
	 * @param user_id
	 * @return ticket
	 */
	public String addTicket(int user_id) {
		Ticket t=new Ticket();
		t.setUser_id(user_id);
		Date d=new Date();
		//3600*24*30*1000计算出的结果超出Integer.MAX_VALUE，所以如果不强转成long的话得到的结果是一个负数
		d.setTime(d.getTime()+(long)3600*24*30*1000);
		t.setExpired(d);
		t.setStatus(0);
		t.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		ticketDao.insertTicket(t);
		return t.getTicket();
	}
	
	
	
	
	
	
	
	
	
	

}
