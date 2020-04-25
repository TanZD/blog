package com.zd.Service;

import java.util.List;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Comment;
import com.zd.VO.CommentVO;

public interface CommentService {
	public Comment insert(Comment comment);

	public Comment update(Comment comment);

	public Integer delete(int comment_id);

	public List<CommentVO> getAll();

	public JSONResult<CommentVO> get(int page, int limit, int order);

	public JSONResult<CommentVO> getByContent(String content, int page, int limit);

	public List<CommentVO> getByUserId(int user_id);

	public JSONResult<CommentVO> getByUserId(int user_id, int page, int limit, int order);

	public List<CommentVO> getByArticleId(int article_id);

	public JSONResult<CommentVO> getByArticleId(int article_id, int page, int limit, int order);

	public List<CommentVO> getByAcceptorId(int pid);

	public CommentVO getById(int id);

	public JSONResult<CommentVO> getByAcceptorId(int pid, int page, int limit, int order);

	public List<CommentVO> getByDate(String date);

	public JSONResult<CommentVO> getByDate(String date, int page, int limit);

	public Integer getCount();

	public Comment insertOrUpdate(Comment comment);

	public Integer getCountByDate(String date);

	public List<CommentVO> TextFilter(List<CommentVO> data);

}
