package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Media;

public interface MediaDAO {
	public Media insert(Media media);

	public Integer delete(Media media);

	public Media getById(int id);

	public List<Media> get(int start, int limit);
	
	public List<Media> getByArticle(int article_id);

	public List<Media> getByName(String name, int type, int start, int limit);

	public List<Media> getByUser(int user_id, int type, int start, int limit);

	public List<Media> getByArticle(int article_id, int type, int start, int limit);

	public Media getByName(String name, int type);

	public Integer getCount();

	public Integer getCountByType(int type);

	public Integer getCountByUser(int user_id, int type);

	public Integer getCountByName(String name, int type);

	public Integer getCountByArticle(int article_id, int type);

}
