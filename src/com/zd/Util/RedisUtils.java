package com.zd.Util;

public class RedisUtils {
	public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";

	public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";

	public static final String MAP_KEY_ARTICLE_VIEW_COUNT = "MAP_KEY_ARTICLE_VIEW_COUNT";

	public static String getLikedKey(int user_id, int article_id) {
		StringBuilder builder = new StringBuilder();
		builder.append(user_id);
		builder.append("::");
		builder.append(article_id);
		return builder.toString();
	}
}
