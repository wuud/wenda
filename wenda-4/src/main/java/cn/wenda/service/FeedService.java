package cn.wenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wenda.dao.FeedDao;
import cn.wenda.model.Feed;

@Service
public class FeedService {
	@Autowired
	FeedDao feedDao;
	
	public void addFeed(Feed feed) {
		feedDao.addFeed(feed);
	}
	public Feed getFeedById(Integer id) {
		return feedDao.getFeedById(id);
	}
	public List<Feed> getUserFeeds(List<Integer> userIds,int maxId,int count){
		return feedDao.selectUserFeeds(maxId, userIds, count);
	}

}
