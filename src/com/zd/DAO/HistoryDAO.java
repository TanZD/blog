package com.zd.DAO;

import java.util.List;

import com.zd.Entity.History;
import com.zd.VO.HistoryVO;

public interface HistoryDAO {
	public History insert(History history);

	public List<HistoryVO> get(int user_id, int start, int limit);

	public List<HistoryVO> get(int user_id);

	public List<HistoryVO> getByArticleId(int article_id);

	public List<HistoryVO> getByArticleId(int article_id, int start, int limit);

	public Integer delete(int id);

	public Integer deleteByUserId(int user_id);

	public Integer getCountByUser(int user_id);

	public Integer getCountByArticle(int article_id);

	public Integer getCount();

}
