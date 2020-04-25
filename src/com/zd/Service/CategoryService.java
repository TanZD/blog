package com.zd.Service;

import java.util.List;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Category;
import com.zd.VO.CategoryVO;

public interface CategoryService {
	public Category insert(Category category);

	public List<Category> getAll();

	public JSONResult<CategoryVO> get(int page, int limit, int order);

	public Integer getCount();

	public List<Category> getByPid(int pid);

	public List<Category> getByTime(String data);

	public List<Category> findByArticleId(int article_id);

	public List<Category> findByUserId(int user_id);

	public JSONResult<CategoryVO> findByUsedTimes(int limit);

	public List<CategoryVO> findByUsedWithUserId(int user_id);

	public Category findById(int category_id);

	public Category findByName(String name);

	public Category update(Category category);

	public Integer delete(int category_id);

	public Category saveOrUpdate(Category category);

	List<Category> getOrInsert(String[] cate, int user_id);

	List<Category> getByWeight();

	Integer getCountByWeight();

	Integer updateWeight(int cate_id, int weight);

}
