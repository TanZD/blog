package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.LikesDAO;
import com.zd.Entity.Likes;
import com.zd.VO.LikesVO;

@Repository("LikesDAO")
public class LikesDAOImpl extends BaseDAOImpl implements LikesDAO {

	@Override
	public Likes insert(Likes like) {
		getSession().save(like);
		return like;
	}

	@Override
	public List<Likes> insertAll(List<Likes> likes) {
		for (Likes like : likes) {
			getSession().save(like);
		}
		return likes;
	}

	@Override
	public Likes update(Likes likes) {
		getSession().update(likes);
		return likes;
	}


	@Override
	public Likes getByArticleIdAndUSerId(int user_id, int article_id) {
		String hql = "FROM Likes WHERE likes_article_id=:a_id AND likes_user_id=:u_id";
		return (Likes) getSession().createQuery(hql).setParameter("a_id", article_id).setParameter("u_id", user_id)
				.uniqueResult();
	}

	@Override
	public Integer getCountByArticleId(int article_id) {
		String sql = "SELECT COUNT(*) FROM likes WHERE likes_article_id=:a_id";
		Object data = getSession().createSQLQuery(sql).setParameter("a_id", article_id).list().get(0);
		return Integer.valueOf(data.toString());
	}

	@Override
	public Integer getCountByUserId(int user_id) {
		String sql = "SELECT COUNT(*) FROM likes WHERE likes_user_id=:u_id";
		Object data = getSession().createSQLQuery(sql).setParameter("u_id", user_id).list().get(0);
		return Integer.valueOf(data.toString());
	}

	@Override
	public List<Likes> isLikeList(String[] article_id, int user_id) {
		String sql = "SELECT * FROM likes WHERE likes_user_id=:u_id AND likes_article_id IN ";
		String article = "";
		for (int i = 0; i < article_id.length; i++) {
			article += article_id[i];
			if (i != (article_id.length - 1)) {
				article += ",";
			}
		}
		sql += "(" + article + ") ORDER BY FIELD(likes_article_id," + article + ")";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).addEntity(Likes.class).list();
	}

	@Override
	public void transFromRedis() {

	}

	@Override
	public void transCountFromRedis() {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getCountByPId(int user_id) {
		String sql = "SELECT COUNT(*) " + " FROM likes AS l "
				+ " INNER JOIN article AS a ON a.`id`=l.`likes_article_id` "
				+ " INNER JOIN USER AS u ON u.`id`=a.`user_id`  " + " WHERE u.`id`=:user_id AND l.`status`=1";
		Object object = getSession().createSQLQuery(sql).setParameter("user_id", user_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

}
