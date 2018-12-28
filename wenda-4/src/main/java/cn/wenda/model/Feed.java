package cn.wenda.model;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Feed {
	
	private int id;
	private int userId;
	private int type;
	private String data;
	private Date createdDate;
	
	private JSONObject dataJson=null;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
		dataJson=JSON.parseObject(data);
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String get(String key) {
		return dataJson==null?null:dataJson.getString(key);
	}

}
