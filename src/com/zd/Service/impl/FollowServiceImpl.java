package com.zd.Service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zd.DAO.FollowDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.Entity.Follow;
import com.zd.Service.FollowService;
import com.zd.Util.Msg;
import com.zd.VO.FollowVO;

@Service("FollowService")
@Transactional
public class FollowServiceImpl implements FollowService {
	@Autowired
	FollowDAO followDAO;

	@Override
	public Follow insert(Follow follow) throws Exception {
		followDAO.insert(follow);
		if (follow.getId() != 0) {
			return follow;
		}
		return null;
	}

	@Override
	public JSONResult<FollowVO> get(int user_id, int page, int limit) {
		JSONResult<FollowVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = followDAO.getCountByUser(user_id);
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<FollowVO> data = followDAO.get(user_id, pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<FollowVO> getByFollow(int user_id, int page, int limit) {
		JSONResult<FollowVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = followDAO.getCountByUser(user_id);
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<FollowVO> data = followDAO.getByPid(user_id, pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public Follow hasFollowed(int user_id, int p_id) {
		return followDAO.hasFollow(user_id, p_id);
	}

	// @Override
	// public List<FollowVO> get(int user_id) {
	// return followDAO.get(user_id);
	// }

	@Override
	public Integer getCountByUser(int user_id) {
		return followDAO.getCountByUser(user_id);
	}

	@Override
	public Integer getCount() {
		return followDAO.getCount();
	}

	@Override
	public Integer delete(int user_id, int p_id) {
		return followDAO.delete(user_id, p_id);
	}

}
