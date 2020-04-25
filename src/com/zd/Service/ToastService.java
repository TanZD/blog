package com.zd.Service;

import java.util.List;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Comment;
import com.zd.Entity.Toast;
import com.zd.VO.ToastVO;

public interface ToastService {
	public Toast insert(Toast toast);

	public Toast insertByComment(Comment comment);

	public Toast update(Toast toast);

	public Integer delete(int toast_id);

	public Integer deleteByCommentId(int comment_id);

	public Integer setIsRead(int user_id);

	public List<ToastVO> get();

	public List<ToastVO> getAll();

	public List<ToastVO> getByUserId(int user_id);

	public JSONResult<ToastVO> getByUserId(int user_id, int page, int limit);

	public List<ToastVO> getByType(int type);

	public List<ToastVO> getByTime(String date);

	public Integer getCountByUserId(int user_id);

	public Integer getCountByType(int type);

}
