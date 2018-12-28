package cn.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wenda.model.Feed;

public interface FeedDao {

	void addFeed(Feed feed);

	Feed getFeedById(Integer id);

	List<Feed> selectUserFeeds(@Param("maxId") int maxId,
			@Param("userIds") List<Integer> userIds,
			@Param("count") int count);

}
