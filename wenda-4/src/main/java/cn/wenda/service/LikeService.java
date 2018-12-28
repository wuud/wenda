package cn.wenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wenda.utils.RedisKeysUtil;
/**
 * 点赞的业务实现
 * @author wuu
 * 2018年12月18日
 */
@Service
public class LikeService {
	
	@Autowired
	JedisAdapterService jedisAdapterService;
	/**
	 * 如果用户点赞，则向redis中添加一条数据，并删除用户之前的踩，最后返回多少用户点了赞
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
	 */
	public long like(int userId,int entityType,int entityId) {
		String likeKey=RedisKeysUtil.getLikeKey(entityType, entityId);
		jedisAdapterService.sadd(likeKey,String.valueOf(userId));
		
		String dislike=RedisKeysUtil.getDislikeKey(entityType, entityId);
		jedisAdapterService.srem(dislike, String.valueOf(userId));
		return jedisAdapterService.scard(likeKey);
	}
	/**
	 * 和like类似，只是逻辑相反
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
	 */
	public long dislike(int userId,int entityType,int entityId) {
		String dislikeKey=RedisKeysUtil.getDislikeKey(entityType, entityId);
		jedisAdapterService.sadd(dislikeKey, String.valueOf(userId));
		
		String likeKey=RedisKeysUtil.getLikeKey(entityType, entityId);
		jedisAdapterService.srem(likeKey,String.valueOf(userId));
		
		return jedisAdapterService.scard(likeKey);
	}
	/**
	 * 得到用户的状态，1表示赞，0表示无操作，-1表示踩
	 * @return
	 */
	public int getLikeStatus(int userId,int entityType,int entityId) {
		String likeKey=RedisKeysUtil.getLikeKey(entityType, entityId);
		String dislikeKey=RedisKeysUtil.getDislikeKey(entityType, entityId);
		if(jedisAdapterService.sismember(likeKey, String.valueOf(userId))) {
			return 1;
		}else if(jedisAdapterService.sismember(dislikeKey, String.valueOf(userId))) {
			return -1;
		}
		return 0;
	}
	/**
	 * 获取评论的点赞总数
	 * @param entityType
	 * @param entityId
	 * @return
	 */
	public long getLikeCount(int entityType,int entityId) {
		String likeKey=RedisKeysUtil.getLikeKey(entityType, entityId);
		return jedisAdapterService.scard(likeKey);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
