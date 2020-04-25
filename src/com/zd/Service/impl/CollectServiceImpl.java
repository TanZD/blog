package com.zd.Service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zd.DAO.CollectDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.Entity.Collect;
import com.zd.Service.CollectService;
import com.zd.Util.Msg;
import com.zd.VO.CollectVO;

@Service("CollectService")
@Transactional
public class CollectServiceImpl implements CollectService {
	@Autowired
	CollectDAO collectDAO;

	@Override
	public Collect insert(Collect collect) {
		collect = collectDAO.insert(collect);
		if (collect.getId() != 0) {
			return collect;
		}
		return null;
	}

	@Override
	public Integer delete(int article_id, int user_id) {
		return collectDAO.delete(article_id, user_id);
	}

	@Override
	public JSONResult<CollectVO> getByUser(int user_id, int page, int limit) {
		JSONResult<CollectVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		int totalNum = collectDAO.getCountByUser(user_id);
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<CollectVO> data = collectDAO.getByUser(user_id, pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<CollectVO> getByUser(int user_id) {
		JSONResult<CollectVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		int totalNum = collectDAO.getCountByUser(user_id);
		if (totalNum != 0) {
			List<CollectVO> data = collectDAO.getByUser(user_id);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, data);
		}
		return result;
	}

	@Override
	public Integer getCount() {
		return collectDAO.getCount();
	}

	@Override
	public Collect hasCollected(int user_id, int article_id) {
		return collectDAO.hasCollected(user_id, article_id);
	}

}
