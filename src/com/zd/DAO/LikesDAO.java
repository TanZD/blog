package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Likes;
import com.zd.VO.LikesVO;

public interface LikesDAO {
	public Likes insert(Likes like);

	public List<Likes> insertAll(List<Likes> likes);

	public Likes update(Likes likes);

	// 查看用户是否对该文章有点赞记录
	public Likes getByArticleIdAndUSerId(int user_id, int article_id);

	// 将redis的点赞数据存入数据库中
	public void transFromRedis();

	// 将redis的点赞数存入数据库中
	public void transCountFromRedis();

	// 获取文章的点赞数
	public Integer getCountByArticleId(int article_id);

	// 获取用户的点赞数
	public Integer getCountByUserId(int user_id);

	// 获取文章点赞列表
	public List<Likes> isLikeList(String[] article_id, int user_id);

	public Integer getCountByPId(int user_id);
}
