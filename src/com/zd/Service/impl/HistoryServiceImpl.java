package com.zd.Service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zd.DAO.HistoryDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.Entity.History;
import com.zd.Service.HistoryService;
import com.zd.Util.Msg;
import com.zd.VO.HistoryVO;

@Service("HistoryService")
@Transactional
public class HistoryServiceImpl implements HistoryService {
	@Autowired
	HistoryDAO historyDAO;

	@Async
	@Override
	public History save(History history) {
		historyDAO.insert(history);
		return history;
	}

	@Override
	public JSONResult<HistoryVO> get(int user_id, int page, int limit) {
		JSONResult<HistoryVO> result = new JSONResult<HistoryVO>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = historyDAO.getCountByUser(user_id);
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<HistoryVO> data = historyDAO.get(user_id, pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public List<HistoryVO> get(int user_id) {
		return historyDAO.get(user_id);
	}

	@Override
	public JSONResult<HistoryVO> getByArticle(int article_id, int page, int limit) {
		JSONResult<HistoryVO> result = new JSONResult<HistoryVO>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = historyDAO.getCountByArticle(article_id);
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<HistoryVO> data = historyDAO.getByArticleId(article_id, pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public List<HistoryVO> getByArticle(int article_id) {
		return historyDAO.getByArticleId(article_id);
	}

	@Override
	public Integer getCount() {
		return historyDAO.getCount();
	}

	@Override
	public Integer getCountByUser(int user_id) {
		return historyDAO.getCountByUser(user_id);
	}

	@Override
	public Integer getCountByArticle(int article_id) {
		return historyDAO.getCountByArticle(article_id);
	}

	@Override
	public Integer delete(int id) {
		return historyDAO.delete(id);
	}

	@Override
	public Integer deleteByUserId(int user_id) {
		return historyDAO.deleteByUserId(user_id);
	}
}
