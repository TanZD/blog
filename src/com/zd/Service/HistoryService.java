package com.zd.Service;

import java.util.List;

import com.zd.DTO.JSONResult;
import com.zd.Entity.History;
import com.zd.VO.HistoryVO;

public interface HistoryService {
	public History save(History history);

	public JSONResult<HistoryVO> get(int user_id, int page, int limit);

	public List<HistoryVO> get(int user_id);

	public JSONResult<HistoryVO> getByArticle(int article_id, int page, int limit);

	public List<HistoryVO> getByArticle(int article_id);

	public Integer getCount();

	public Integer getCountByUser(int user_id);

	public Integer getCountByArticle(int article_id);

	public Integer delete(int id);

	public Integer deleteByUserId(int user_id);

}
