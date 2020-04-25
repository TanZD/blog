package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Toast;
import com.zd.VO.ToastVO;

public interface ToastDAO {
	public Toast insert(Toast toast);

	public Toast update(Toast toast);

	public Integer delete(int toast_id);

	public List<ToastVO> get();

	public List<ToastVO> getAll();

	public List<ToastVO> getByUserId(int user_id);

	public List<ToastVO> getByUserId(int user_id, int start, int limits);

	public List<ToastVO> getByType(int type);

	public List<ToastVO> getByTime(String date);

	public Integer getAllCount();

	public Integer getCount();

	public Integer getCountByUserId(int user_id);

	public Integer getCountByType(int type);

	public Integer setIsRead(int user_id);

	Integer deleteByCommentId(int comment_id);

}
