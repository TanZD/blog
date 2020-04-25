package com.zd.Service;

import java.util.List;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Media;

public interface MediaService {
	public Media insert(Media media);

	public Integer delete(Media media);

	public JSONResult<Media> get(int page, int limit);

	public List<Media> getByArticle(int article_id);

	public JSONResult<Media> getByName(String name, int type, int page, int limit);

	public JSONResult<Media> getByUser(int user_id, int type, int page, int limit);

	public JSONResult<Media> getByArticle(int article_id, int type, int page, int limit);

	public Media getByName(String name, int type);
	
	public Media getById(int id);

	public Integer getCount();

	public Integer getCountByType(int type);

	public Integer getCountByUser(int user_id, int type);

	public Integer getCountByName(String name, int type);

	public Integer getCountByArticle(int article_id, int type);
}
