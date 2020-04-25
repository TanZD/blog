package com.zd.Service;

import java.sql.SQLException;
import com.zd.DTO.JSONResult;
import com.zd.Entity.Follow;
import com.zd.VO.FollowVO;

public interface FollowService {
	public Follow insert(Follow follow) throws SQLException, Exception;

	public JSONResult<FollowVO> get(int user_id, int page, int limit);

	// public List<FollowVO> get(int user_id);

	public Integer getCountByUser(int user_id);

	public JSONResult<FollowVO> getByFollow(int user_id, int page, int limit);

	public Integer getCount();

	public Integer delete(int user_id, int p_id);

	public Follow hasFollowed(int user_id, int p_id);
}
