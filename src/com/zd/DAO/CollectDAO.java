package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Collect;
import com.zd.VO.CollectVO;

public interface CollectDAO {
	public Collect insert(Collect collect);

	public List<CollectVO> getByUser(int user_id, int start, int limit);

	public List<CollectVO> getByUser(int user_id);

	public Integer getCountByUser(int user_id);

	public Integer getCountByArticle(int article_id);

	public Integer getCount();

	public Integer delete(int article_id, int user_id);

	public Collect hasCollected(int user_id, int article_id);

}
