package cn.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wenda.model.Feed;

public interface FeedDao {

	void addFeed(Feed feed);

	Feed getFeedById(Integer id);

	Feed getFeedByUser(@Param("userId") int userId,
			@Param("count") int count);

}
