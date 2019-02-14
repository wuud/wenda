package cn.wenda.model;

import java.util.Date;

public class Feed {
	
	
	private int id;
	private int userId;
	private String data;
	private Date createdDate;
	private String url;
	
	public Feed(int userId, String data, Date createdDate, String url) {
		super();
		this.userId = userId;
		this.data = data;
		this.createdDate = createdDate;
		this.url = url;
	}
	
	public Feed() {
		super();
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getData() {
		return data;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	

}
