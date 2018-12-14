package cn.wenda.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.wenda.model.User;

@Repository
public interface UserDao {
	
	User getUserById(Integer id) ;
	
	User getUserByName(String name);
	
	List<User> getAllUser();
	
	void insertUser(User user);
	
	void updateUser(User user);

}
