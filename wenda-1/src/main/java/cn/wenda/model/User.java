package cn.wenda.model;

import java.io.Serializable;

public class User implements Serializable {

	private int id;
	private String name;
	private String password;
	private String salt;
	private String head_url;

	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public User(String name, String password, String salt, String head_url) {
		super();
		this.name = name;
		this.password = password;
		this.salt = salt;
		this.head_url = head_url;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getHead_url() {
		return head_url;
	}

	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", salt=" + salt + ", head_url="
				+ head_url + "]";
	}

}
