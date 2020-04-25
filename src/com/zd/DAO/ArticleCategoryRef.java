package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Article;

public interface ArticleCategoryRef {
	// 根据文章id删除
	public Integer deleteByArticleId(int article_id);

	// 根据分类id删除
	public Integer deleteByCategoryId(int category_id);

	// 获取某分类的文章数
	public Integer getCountArticleByCategory(int category_id);

	// 添加
	public Integer add(int category_id, int article);
}
