package com.zd.Service;

import java.util.List;

import com.zd.Entity.Likes;

public interface LikesService {
	public Likes save(Likes like);

	public List<Likes> saveAll(List<Likes> likes);

	public Likes getByArticleIdAndUserId(int user_id, int article_id);

	// 将Redis的点赞数据传入数据库中
	public void transLikesFromRedis();

	// 將Redis的點贊數傳入數據庫中
	public void transLikesCountFromRedis();

	public List<Likes> isLikeList(String[] article_id, int user_id);

	Integer getLike(int user_id, int article_id);
}
