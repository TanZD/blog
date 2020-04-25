package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Category;
import com.zd.VO.CategoryVO;

public interface CategoryDAO {

	public Category insert(Category category);

	public Category findById(int category_id);

	public Category findByName(String name);

	// 文章的分类
	public List<Category> findByArticleId(int article_id);

	// 用户创建的分类
	public List<Category> findByUserId(int user_id);

	// 用户使用过的分类
	public List<CategoryVO> findByUsedWithUserId(int user_id);

	// 按分类的使用次数返回
	public List<CategoryVO> findByUsedTimes(int limit);

	public List<Category> getByTime(String data);

	public Integer getCount();

	public List<Category> getAll();

	public List<CategoryVO> get(int limit, int page, int order);

	public List<Category> getByPid(int pid);

	public Category update(Category category);

	public Integer delete(int category_id);

	Integer updateWeight(int cate_id, int weight);
	
	public Category getById(int id);

	List<Category> getByWeight();

	Integer getCountByWeight();
}
