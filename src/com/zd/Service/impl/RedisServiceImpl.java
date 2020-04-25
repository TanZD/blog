package com.zd.Service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import com.zd.Entity.Article;
import com.zd.Entity.Likes;
import com.zd.Service.RedisService;
import com.zd.Util.MyUtil;
import com.zd.Util.RedisUtils;
import com.zd.VO.LikesVO;

@Service("RedisService")
public class RedisServiceImpl implements RedisService {
	@Autowired
	RedisTemplate redisTemplate;

	public void saveLike(int user_id, int article_id) {
		Integer key = (Integer) redisTemplate.opsForHash().get(RedisUtils.MAP_KEY_USER_LIKED,
				user_id + "::" + article_id);
		if (key != null) {
			if (key == 1) {
				saveUnLikeToRedis(user_id, article_id);
			} else {
				saveLikeToRedis(user_id, article_id);
			}
		}
	}

	@Override
	public void saveLikeToRedis(int user_id, int article_id) {
		String key = RedisUtils.getLikedKey(user_id, article_id);
		redisTemplate.opsForHash().put(RedisUtils.MAP_KEY_USER_LIKED, key, String.valueOf(1));
		incrLikeCount(article_id);
	}

	@Override
	public void saveUnLikeToRedis(int user_id, int article_id) {
		String key = RedisUtils.getLikedKey(user_id, article_id);
		redisTemplate.opsForHash().put(RedisUtils.MAP_KEY_USER_LIKED, key, String.valueOf(0));
		decrLikeCount(article_id);
	}

	@Override
	public void deleteLikeFromRedis(int user_id, int article_id) {
		String key = RedisUtils.getLikedKey(user_id, article_id);
		redisTemplate.opsForHash().delete(RedisUtils.MAP_KEY_USER_LIKED, key);
	}

	@Override
	public void incrLikeCount(int article_id) {
		if (!redisTemplate.opsForHash().hasKey(RedisUtils.MAP_KEY_USER_LIKED_COUNT, String.valueOf(article_id))) {
			redisTemplate.opsForHash().put(RedisUtils.MAP_KEY_USER_LIKED_COUNT, String.valueOf(article_id),
					String.valueOf(0));
		}
		redisTemplate.opsForHash().increment(RedisUtils.MAP_KEY_USER_LIKED_COUNT, String.valueOf(article_id), 1);
	}

	@Override
	public void decrLikeCount(int article_id) {
		if (!redisTemplate.opsForHash().hasKey(RedisUtils.MAP_KEY_USER_LIKED_COUNT, String.valueOf(article_id))) {
			redisTemplate.opsForHash().put(RedisUtils.MAP_KEY_USER_LIKED_COUNT, String.valueOf(article_id),
					String.valueOf(0));
		}
		redisTemplate.opsForHash().increment(RedisUtils.MAP_KEY_USER_LIKED_COUNT, String.valueOf(article_id), -1);
	}

	@Override
	public Integer getLikeCount(int article_id) {
		String count = (String) redisTemplate.opsForHash().get(RedisUtils.MAP_KEY_USER_LIKED_COUNT,
				String.valueOf(article_id));
		if (count != null) {
			return Integer.valueOf(count);
		}
		return 0;
	}

	@Override
	public void incrViewCount(int article_id) {
		if (!redisTemplate.opsForHash().hasKey(RedisUtils.MAP_KEY_ARTICLE_VIEW_COUNT, String.valueOf(article_id))) {
			redisTemplate.opsForHash().put(RedisUtils.MAP_KEY_ARTICLE_VIEW_COUNT, String.valueOf(article_id),
					String.valueOf(0));
		}
		redisTemplate.opsForHash().increment(RedisUtils.MAP_KEY_ARTICLE_VIEW_COUNT, String.valueOf(article_id), 1);
	}

	@Override
	public Integer getViewsCount(int article_id) {
		String count = (String) redisTemplate.opsForHash().get(RedisUtils.MAP_KEY_ARTICLE_VIEW_COUNT,
				String.valueOf(article_id));
		if (count != null) {
			return Integer.valueOf(count);
		}
		return 0;
	}

	@Override
	public List<Article> getViewsFromRedis() {
		Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash()
				.scan(RedisUtils.MAP_KEY_ARTICLE_VIEW_COUNT, ScanOptions.NONE);
		List<Article> list = new ArrayList<>();
		while (cursor.hasNext()) {
			Map.Entry<Object, Object> entry = cursor.next();
			String key = (String) entry.getKey();
			Integer value = Integer.valueOf(entry.getValue().toString());
			Article article = new Article();
			article.setId(Integer.valueOf(key));
			article.setView_count(value);
			list.add(article);
			redisTemplate.opsForHash().delete(RedisUtils.MAP_KEY_ARTICLE_VIEW_COUNT, key);
		}
		return list;
	}

	@Override
	public List<Likes> getLikesFromRedis() {
		Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisUtils.MAP_KEY_USER_LIKED,
				ScanOptions.NONE);
		List<Likes> list = new ArrayList<>();
		while (cursor.hasNext()) {
			Map.Entry<Object, Object> entry = cursor.next();
			String key = (String) entry.getKey();
			// 将数据分离出user_id article_id
			String[] split = key.split("::");
			String user_id = split[0];
			String article_id = split[1];
			Integer value = Integer.valueOf(entry.getValue().toString());
			// 组装likes
			Likes like = new Likes();
			like.setLikes_user_id(Integer.valueOf(user_id));
			like.setLikes_article_id(Integer.valueOf(article_id));
			// like.setCreate_time(MyUtil.NowTime());
			like.setUpdate_time(MyUtil.NowTime());
			like.setStatus(value);
			list.add(like);
			// 存到list后从redis中删除
			redisTemplate.opsForHash().delete(RedisUtils.MAP_KEY_USER_LIKED, key);
		}
		return list;
	}

	@Override
	public List<LikesVO> getLikesCountFromRedis() {

		return null;
	}

	public boolean isAliveLike(int article_id, int user_id) {
		return redisTemplate.opsForHash().hasKey(RedisUtils.MAP_KEY_USER_LIKED, user_id + "::" + article_id);
	}
	
	@Override
	public void deleteLikeCount(){
		redisTemplate.delete(RedisUtils.MAP_KEY_USER_LIKED_COUNT);
	}

	@Override
	public Integer getLike(int user_id, int article_id) {
		String key = RedisUtils.getLikedKey(user_id, article_id);
		Object value = redisTemplate.opsForHash().get(RedisUtils.MAP_KEY_USER_LIKED, key);
		if (value != null) {
			return Integer.valueOf(value.toString());
		}
		return null;
	}
}
