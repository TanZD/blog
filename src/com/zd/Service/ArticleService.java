package com.zd.Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.zd.DTO.ArticleDTO;
import com.zd.VO.DateCount;
import com.zd.DTO.JSONResult;
import com.zd.Entity.Article;
import com.zd.Entity.Tag;
import com.zd.VO.ADetailVO;
import com.zd.VO.ArticleSimple;
import com.zd.VO.ArticleVO;

public interface ArticleService {
	// 保存文章
	public Article insert(ArticleDTO<Article> article);

	// 更新文章
	public Article update(ArticleDTO<Article> article);

	// 删除文章
	public Integer delete(int article_id);

	// 获取所有文章或动态
	public List<ArticleVO> getAll();

	// 获取所有文章或动态(分页)
	public JSONResult<ArticleDTO<ArticleVO>> get(int page, int limit, int order);

	// 获取用户的文章或动态
	public List<ArticleVO> getByUserId(int user_id);

	// 获取用户的文章或动态(分页)
	public JSONResult<ArticleDTO<ArticleVO>> getByUserId(int user_id, int page, int limit, int order);

	// 根据分类获取文章或动态
	public List<ArticleVO> getByCategoryId(int category_id);

	// 根据分类获取文章或动态(分页)
	public JSONResult<ArticleDTO<ArticleVO>> getByCategoryId(int category_id, int page, int limit, int order);

	// 根据标签获取文章或动态
	public List<ArticleVO> getByTagId(int tag_id);

	// 根据标签获取文章或动态(分页)
	public JSONResult<ArticleDTO<ArticleVO>> getByTagId(int tag_id, int page, int limit, int order);

	// 获取关注的文章文章或动态(分页)
	public JSONResult<ArticleDTO<ArticleVO>> getByFollow(int user_id, int page, int limit, int order);

	public List<Article> getOrderByComments(int limit);

	public List<Article> getOrderByComments(int page, int limit);

	// 文章搜索
	public JSONResult<ArticleSimple> search(String words, int page, int limit);

	//文章搜索数量
	public Integer getCountBySearch(String words);

	//更新文章阅读权限
	public Integer updateReadable(int article_id, int status);

	// 根据文章Id获取文章内容
	public Article getById(int article_id);

	// 按年归档
	public List<DateCount> ListByYear();

	// 按年月归档
	public List<DateCount> ListByYearAndMonth();

	// 按年归档用户文章
	public List<DateCount> ListByYearFromUserId(int user_id);

	// 按年月归档用户文章
	public List<DateCount> ListByYearAndMonthFromUserId(int user_id);

	// 获取文章总浏览量
	public Integer getTotalViews();

	public ArticleDTO<ADetailVO> getDetailById(int article_id);

	//获取用户某分类下的文章或动态
	JSONResult<ArticleDTO<ArticleVO>> getByUserIdAndCategory(int user_id, int category_id, int page, int limit,
			int order);

	//获取用户某标签下的文章或动态
	JSONResult<ArticleDTO<ArticleVO>> getByUserIdAndTag(int user_id, int tag_id, int page, int limit, int order);

	// 发表动态
	public Article savePost(HttpServletRequest request, Article article, List<Tag> tagList, MultipartFile[] file,
			String name, int post_type, int pid);

	public List<ArticleDTO<ArticleVO>> tranDTO(List<ArticleVO> modules);

	//将redis中的阅读量存到数据库中
	public void tranViewsFromRedis();

	public Article saveOrUpdate(ArticleDTO<Article> article);

	//获取动态
	JSONResult<ArticleDTO<ArticleVO>> getPost(int page, int limit, int order);
	
	//根据用户id获取动态
	JSONResult<ArticleDTO<ArticleVO>> postByUserId(int user_id,int page,int limit, int order);
	
	//根据标签id获取动态
	JSONResult<ArticleDTO<ArticleVO>> postByTag(int tag_id,int page,int limit, int order);

	//根据关注着id获取动态
	JSONResult<ArticleDTO<ArticleVO>> postByFollow(int follow_id,int page,int limit, int order);

	//获取文章
	JSONResult<ArticleDTO<ArticleVO>> getArticle(int page,int limit,int order);
	
	//根据用户id获取文章
	JSONResult<ArticleDTO<ArticleVO>> ArticleByUserId(int user_id,int page,int limit, int order);
	
	//根据分类获取文章
	JSONResult<ArticleDTO<ArticleVO>> ArticleByCategory(int cate_id,int page,int limit, int order);
	
	//根据标签获取文章 
	JSONResult<ArticleDTO<ArticleVO>> ArticleByTag(int tag_id,int page,int limit, int order);
	
	//根据关注者获取文章
	JSONResult<ArticleDTO<ArticleVO>> ArticleByFollow(int follow_id,int page,int limit, int order);

	public Integer getTotalCount();

	JSONResult<ArticleDTO<ArticleVO>> ArticleByTime(String time, int page, int limit);

	JSONResult<ArticleDTO<ArticleVO>> ArticleByTimeAndUserId(String time, int user_id, int page, int limit);

}
