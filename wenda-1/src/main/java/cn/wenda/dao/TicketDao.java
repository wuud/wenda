package cn.wenda.dao;

import org.apache.ibatis.annotations.Param;

import cn.wenda.model.Ticket;

public interface TicketDao {
	
	void insertTicket(Ticket ticket);
	Ticket getTicketByTicket(String ticket);
	void updateStatus(@Param("ticket")String ticket,@Param("status")int status);

}
