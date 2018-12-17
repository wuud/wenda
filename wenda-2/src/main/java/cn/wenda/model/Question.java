package cn.wenda.model;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {
	
	private int id;
	private String title;
	private String content;
	private Date created_date;
	private int comment_count;
	private User user_id;

	public Question(String title, String content, User user_id) {
		super();
		this.title = title;
		this.content = content;
		this.user_id = user_id;
	}
	public Question(String title, String content, Date created_date, User user_id) {
		super();
		this.title = title;
		this.content = content;
		this.created_date = created_date;
		this.user_id = user_id;
	}

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", content=" + content + ", created_date=" + created_date
				+ ", comment_count=" + comment_count + ", user_id=" + user_id + "]";
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public User getUser_id() {
		return user_id;
	}
	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}



}
