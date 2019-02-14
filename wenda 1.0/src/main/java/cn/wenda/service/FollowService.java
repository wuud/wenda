package cn.wenda.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wenda.utils.RedisKeysUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 关注功能的业务实现，关注用户以及关注问题
 * 
 * @author wuu 2018年12月20日
 */
@Service
public class FollowService {
	@Autowired
	JedisAdapterService jedisAdapterService;

	/**
	 * 实现用户对实体的关注，使用redis的zset数据结构，以关注时的时间为score
	 * 
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return 是否成功
	 */
	public boolean follow(int userId, int entityType, int entityId) {
		String followerKey = RedisKeysUtil.getFollowerKey(entityType, entityId);
		String followeeKey = RedisKeysUtil.getFolloweeKey(userId, entityType);
		Date date = new Date();
		Jedis jedis = jedisAdapterService.getJedis();
		// 开启事务
		Transaction tx = jedisAdapterService.multi(jedis);
		// 为当前实体添加粉丝
		tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
		// 向用户的关注列表中添加实体
		tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
		List<Object> ret = jedisAdapterService.exec(tx, jedis);

		return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
	}

	/**
	 * 取消关注
	 * 
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return 是否成功
	 */
	public boolean unfollow(int userId, int entityType, int entityId) {
		String followerKey = RedisKeysUtil.getFollowerKey(entityType, entityId);
		String followeeKey = RedisKeysUtil.getFolloweeKey(userId, entityType);
		Jedis jedis = jedisAdapterService.getJedis();
		Transaction tx = jedisAdapterService.multi(jedis);
		// 取消关注，即在对应zset中删除value
		tx.zrem(followerKey, String.valueOf(userId));
		tx.zrem(followeeKey, String.valueOf(entityId));
		List<Object> ret = jedisAdapterService.exec(tx, jedis);

		return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;

	}

	/**
	 * 得到当前实体的所有关注者的id
	 * 
	 * @param entityType
	 * @param entityId
	 * @param count		实体关注者的总数量
	 * @return
	 */
	public List<Integer> getFollowers(int entityType, int entityId, int count) {
		String followerKey = RedisKeysUtil.getFollowerKey(entityType, entityId);
		return getIdsFromSet(jedisAdapterService.zrevrange(followerKey, 0, count));
	}

	/**
	 * 得到当前用户的所有关注对象的id
	 * 
	 * @param userId
	 * @param entityType
	 * @param count		当前用户关注的对象的数量
	 * @return
	 */
	public List<Integer> getFollowees(int userId, int entityType, int count) {
		String followeeKey = RedisKeysUtil.getFolloweeKey(userId, entityType);
		return getIdsFromSet(jedisAdapterService.zrevrange(followeeKey, 0, count));

	}
	/**
	 * 得到当前实体的关注数量
	 * @param entityType
	 * @param entityId
	 */
	public long getFollowerCount(int entityType, int entityId) {
		String followerKey = RedisKeysUtil.getFollowerKey(entityType, entityId);
		return jedisAdapterService.zcard(followerKey);
	}
	/**
	 * 得到当前用户的总关注数量
	 * @param userId
	 * @param entityType
	 */
	public long getFolloweeCount(int userId, int entityType) {
		String followeeKey = RedisKeysUtil.getFolloweeKey(userId, entityType);
		return jedisAdapterService.zcard(followeeKey);
	}
	/**
	 * 判断用户是否关注了某个实体
	 * @param userId
	 * @param entityType
	 * @param entityId
	 */
	public boolean isFollow(int userId,int entityType,int entityId) {
		String followKey=RedisKeysUtil.getFollowerKey(entityType, entityId);
		return jedisAdapterService.zscore(followKey, String.valueOf(userId)) != null;

	}
	/**
	 * 从zset内取出的数据是java中Set<String>类型，将其转为List<Integer>数据结构
	 * 
	 * @param idset
	 * @return
	 */
	private List<Integer> getIdsFromSet(Set<String> idset) {
		List<Integer> ids = new ArrayList<>();
		for (String str : idset) {
			ids.add(Integer.parseInt(str));
		}
		return ids;
	}

}
