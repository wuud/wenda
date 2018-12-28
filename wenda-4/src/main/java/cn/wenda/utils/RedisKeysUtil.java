package cn.wenda.utils;

/**
 * 为要存入redis数据库的数据生成唯一的key，防止key重复造成数据的泄露
 * 
 * @author wuu 2018年12月18日
 */
public class RedisKeysUtil {

	private final static String SPLIT = ":";
	private final static String BIZ_LIKE = "LIKE";
	private final static String BIZ_DISLIKE = "DISLIKE";
	private final static String BIZ_EVENTQUEUE = "EVENT_QUEUE";
	private final static String BIZ_FOLLOWER = "FOLLOWER";
	private final static String BIZ_FOLLOWEE = "FOLLOWEE";
	private final static String BIZ_TIMELINE = "TIMELINE";

	public static String getLikeKey(int entityType, int entityId) {
		return BIZ_LIKE + SPLIT + entityType + entityId;
	}

	public static String getDislikeKey(int entityType, int entityId) {
		return BIZ_DISLIKE + SPLIT + entityType + entityId;
	}

	public static String getEventQueueKey() {
		return BIZ_EVENTQUEUE;
	}

	public static String getFollowerKey(int entityType, int entityId) {
		return BIZ_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
	}

	public static String getFolloweeKey(int userId, int entityType) {
		return BIZ_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
	}

	public static String getTimelineKey(int userId) {
		return BIZ_TIMELINE + SPLIT + String.valueOf(userId);
	}
}
