package com.zd.DAO.impl;

import org.springframework.stereotype.Repository;

import com.zd.DAO.ArticleCategoryRef;

@Repository("ArticleCategoryRef")
public class ArticleCategoryRefImpl extends BaseDAOImpl implements ArticleCategoryRef {

	@Override
	public Integer deleteByArticleId(int article_id) {
		String sql = "DELETE FROM article_category_ref WHERE article_id=:a_id";
		return getSession().createSQLQuery(sql).setParameter("a_id", article_id).executeUpdate();
	}

	@Override
	public Integer deleteByCategoryId(int category_id) {
		String sql = "DELETE FROM article_category_ref WHERE category_id=:c_id";
		return getSession().createSQLQuery(sql).setParameter("c_id", category_id).executeUpdate();
	}

	@Override
	public Integer getCountArticleByCategory(int category_id) {
		String sql = "SELECT COUNT(*) FROM article_category_ref WHERE category_id=:c_id";
		Object object = getSession().createSQLQuery(sql).setParameter("c_id", category_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer add(int category_id, int article_id) {
		String sql = "INSERT INTO article_category_ref(article_id,category_id) VALUES(:a_id,:c_id)";
		return getSession().createSQLQuery(sql).setParameter("a_id", article_id).setParameter("c_id", category_id)
				.executeUpdate();
	}

}
