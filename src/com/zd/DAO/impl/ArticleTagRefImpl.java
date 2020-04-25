package com.zd.DAO.impl;

import org.springframework.stereotype.Repository;

import com.zd.DAO.ArticleTagRef;

@Repository("ArticleTagRef")
public class ArticleTagRefImpl extends BaseDAOImpl implements ArticleTagRef {

	@Override
	public Integer deleteByArticleId(int article_id) {
		String sql = "DELETE FROM article_tag_ref WHERE article_id=:a_id";
		return getSession().createSQLQuery(sql).setParameter("a_id", article_id).executeUpdate();
	}

	@Override
	public Integer deleteByTagId(int tag_id) {
		String sql = "DELETE FROM article_tag_ref WHERE tag_id=:t_id";
		return getSession().createSQLQuery(sql).setParameter("t_id", tag_id).executeUpdate();
	}

	@Override
	public Integer getCountByArticleId(int article_id) {
		String sql = "SELECT COUNT(*) FROM article_tag_ref WHERE article_id=:a_id";
		Object object = getSession().createSQLQuery(sql).setParameter("a_id", article_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCountByTagId(int tag_id) {
		String sql = "SELECT COUNT(*) FROM article_tag_ref WHERE tag_id=:t_id";
		Object object = getSession().createSQLQuery(sql).setParameter("t_id", tag_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer add(int article_id, int tag_id) {
		String sql = "INSERT INTO article_tag_ref(article_id,tag_id) VALUES(:a_id,:t_id)";
		return getSession().createSQLQuery(sql).setParameter("a_id", article_id).setParameter("t_id", tag_id)
				.executeUpdate();
	}

}
