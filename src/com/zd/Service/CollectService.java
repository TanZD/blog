package com.zd.Service;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Collect;
import com.zd.VO.CollectVO;

public interface CollectService {
	public Collect insert(Collect collect);

	public JSONResult<CollectVO> getByUser(int user_id, int page, int limit);

	public JSONResult<CollectVO> getByUser(int user_id);

	public Integer getCount();

	public Integer delete(int article_id, int user_id);

	public Collect hasCollected(int user_id, int article_id);
}
