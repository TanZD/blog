package com.zd.Service;

import java.util.List;

import com.zd.Entity.Article;
import com.zd.Entity.Likes;
import com.zd.VO.LikesVO;

public interface RedisService {
	// 保存点赞数据到redis
	public void saveLikeToRedis(int user_id, int article_id);

	// 保存取消点赞数据到redis
	public void saveUnLikeToRedis(int user_id, int article_id);

	// 删除redis中的点赞数据
	public void deleteLikeFromRedis(int user_id, int article_id);

	// 点赞数+1
	public void incrLikeCount(int article_id);

	// 点赞数-1s
	public void decrLikeCount(int article_id);

	// 获取redis中所有的点赞数据
	public List<Likes> getLikesFromRedis();

	// 获取redis中所有的点赞数
	public List<LikesVO> getLikesCountFromRedis();

	public Integer getLikeCount(int article_id);

	// 浏览量+1
	public void incrViewCount(int article_id);

	public Integer getViewsCount(int article_id);

	// 获取redis中文章点击量
	public List<Article> getViewsFromRedis();

	public Integer getLike(int user_id, int article_id);

	void deleteLikeCount();
}
