package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Comment;
import com.zd.VO.CommentVO;

public interface CommentDAO {
	public Comment insert(Comment comment);

	public Comment update(Comment comment);

	public Integer delete(int comment_id);

	public List<CommentVO> getAll();

	public List<CommentVO> get(int start, int limit, int order);

	//获取文章的评论
	public List<CommentVO> getByArticleId(int article_id);

	public List<CommentVO> getByArticleId(int article_id, int start, int limit, int order);

	//获取用户的评论
	public List<CommentVO> getByUserId(int user_id);

	public List<CommentVO> getByUserId(int user_id, int start, int limit, int order);

	//获取用户的回复评论
	public List<CommentVO> getByAcceptorId(int user_id);

	public List<CommentVO> getByAcceptorId(int user_id, int start, int limit, int order);

	//获取回复的评论
	public Comment getByPid(int p_id);

	public List<CommentVO> getByDate(String date);

	public List<CommentVO> getByDate(String date, int start, int limit);

	public List<CommentVO> getByContent(String content, int start, int limit);

	public CommentVO getById(int id);

	public Integer getCount();

	public Integer getCountByContent(String content);

	public Integer getCountByUserId(int user_id);

	public Integer getCountByAcceptorId(int user_id);

	public Integer getCountByArticleId(int article_id);

	public Integer getCountByDate(String date);


}
