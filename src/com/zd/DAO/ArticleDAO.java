package com.zd.DAO;

import java.util.List;

import com.zd.VO.DateCount;
import com.zd.Entity.Article;
import com.zd.VO.ADetailVO;
import com.zd.VO.ArticleSimple;
import com.zd.VO.ArticleVO;

public interface ArticleDAO {
	public Article insert(Article article);

	public Integer delete(int article_id);

	public Article update(Article article);

	public List<ArticleVO> getAll();

	public List<ArticleVO> get(int start, int limit, int order);

	// 根据Id获取
	public Article getById(int article_id);

	public ADetailVO getDetailById(int article_id);

	// 根据作者id获取文章
	public List<ArticleVO> getByUserId(int user_id);

	public List<ArticleVO> getByUserId(int user_id, int start, int limit, int order);

	// 根据分类id获取文章
	public List<ArticleVO> getByCategoryId(int category_id, int start, int limit, int order);

	public List<ArticleVO> getByCategoryId(int category_id);

	// 根据标签获取文章
	public List<ArticleVO> getByTagId(int tag_id, int start, int limit, int order);

	public List<ArticleVO> getByTagId(int tag_id);

	// 根据关注人获取文章列表
	public List<ArticleVO> getByFollow(int user_id, int start, int limit, int order);

	public List<ArticleVO> getByFollow(int user_id);

	// 关键词搜索标题
	public List<ArticleSimple> search(String[] words, int start, int limit);

	// 更改文章权限
	public Integer updateReadable(int article_id, int status);

	public Integer getCountBySearch(String[] words);

	// 按年归档
	public List<DateCount> ListByYear();

	// 按年月归档
	public List<DateCount> ListByYearAndMonth();

	// 用户文章按年归档
	public List<DateCount> ListByYearFromUserId(int user_id);

	// 用户文章按年月归档
	public List<DateCount> ListByYearAndMonthFromUserId(int user_id);

	public Integer getTotalViews();

	public Integer getCount();

	public Integer getCountByTagId(int tag_id);

	public Integer getCountByCategoryId(int category_id);

	public Integer getCountByUserId(int user_id);

	List<ArticleVO> getByUserAndCategory(int user_id, int category_id, int start, int limit, int order);

	List<ArticleVO> getByUserAndTag(int user_id, int tag_id, int start, int limit, int order);

	Integer getCountByUserIdAndTag(int user_id, int tag_id);

	Integer getCountByUserIdAndCategory(int user_id, int category_id);

	public Integer getCountByFollow(int user_id);

	public Integer updateViews(int article_id, int views);

	public Integer updateViews(List<Article> article);

	//获取动态
	List<ArticleVO> getPost(int start, int limit, int order);
	
	Integer countByPost();
	
	//根据用户id获取动态
	List<ArticleVO> postByUserId(int user_id,int start,int limit, int order);
	
	Integer countPostByUserId(int user_id);
	
	//根据标签id获取动态
	List<ArticleVO> postByTag(int tag_id,int start,int limit, int order);

	Integer countPostByTag(int tag);

	//根据关注着id获取动态
	List<ArticleVO> postByFollow(int follow_id,int start,int limit, int order);

	Integer countPostByFollow(int follow_id);
	
	//获取文章
	List<ArticleVO> getArticle(int start,int limit,int order);
	
	Integer countArticle();
	
	//根据用户id获取文章
	List<ArticleVO> ArticleByUserId(int user_id,int start,int limit, int order);
	
	Integer countArticleByUserId(int user_id);
	
	//根据分类获取文章
	List<ArticleVO> ArticleByCategory(int cate_id,int start,int limit, int order);
	
	Integer countArticleByCategory(int cate_id);
	
	//根据标签获取文章 
	List<ArticleVO> ArticleByTag(int tag_id,int start,int limit, int order);
	
	Integer countArticleByTag(int tag_id);
	
	//根据关注者获取文章
	List<ArticleVO> ArticleByFollow(int follow_id,int start,int limit, int order);
	
	Integer countArticleByFollow(int follow_id);


	List<ArticleVO> ArticleByTime(String time, int start, int limit);

	Integer countArticleByTime(String time);

	List<ArticleVO> ArticleByTimeAndUserId(String time, int user_id, int start, int limit);

	Integer countArticleByTimeAndUserId(String time, int user_id);
	
}
