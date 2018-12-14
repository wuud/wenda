package cn.wenda.model;

import java.util.Date;

public class Ticket {
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", user_id=" + user_id + ", ticket=" + ticket + ", expired=" + expired + ", status="
				+ status + "]";
	}

	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int id;
	private int user_id;
	private String ticket;
	private Date expired;
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Ticket(int id, int user_id, String ticket, Date expired, int status) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.ticket = ticket;
		this.expired = expired;
		this.status = status;
	}

}
