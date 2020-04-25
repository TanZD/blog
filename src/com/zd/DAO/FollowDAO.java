package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Follow;
import com.zd.VO.FollowVO;

public interface FollowDAO {
	public Follow insert(Follow follow) throws Exception;

	public Integer delete(int user_id, int p_id);

	public List<FollowVO> get(int user_id, int start, int limit);

	public List<Follow> get(int user_id);

	public List<FollowVO> getByPid(int p_id, int start, int limit);

	public List<FollowVO> getByPid(int p_id);

	public Integer getCountByUser(int user_id);

	public Integer getCountByFollow(int user_id);

	public Integer getCount();

	public Follow hasFollow(int user_id, int p_id);

}
