package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.CollectDAO;
import com.zd.Entity.Collect;
import com.zd.VO.CollectVO;

@Repository("CollectDAO")
public class CollectDAOImpl extends BaseDAOImpl implements CollectDAO {

	@Override
	public Collect insert(Collect collect) {
		getSession().save(collect);
		return collect;
	}

	@Override
	public Integer delete(int article_id, int user_id) {
		String hql = "DELETE FROM Collect WHERE collect_article_id=:a_id AND collect_user_id=:u_id";
		return getSession().createQuery(hql).setParameter("a_id", article_id).setParameter("u_id", user_id)
				.executeUpdate();
	}

	@Override
	public List<CollectVO> getByUser(int user_id, int start, int limit) {
		String sql = "SELECT c.*,u.`username` AS username,a.`title`,a.`summary`,a.`view_count`" + " FROM collect AS c "
				+ " INNER JOIN article AS a ON a.`id`=c.`collect_article_id` "
				+ " INNER JOIN USER AS u ON u.`id`=a.`user_id` " + " WHERE c.`collect_user_id`=:u_id "
				+ " ORDER BY c.`create_time` DESC " + " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(CollectVO.class).list();
	}

	@Override
	public List<CollectVO> getByUser(int user_id) {
		String sql = "SELECT c.*,u.`username` AS username,a.`title`,a.`summary`,a.`view_count`" + " FROM collect AS c "
				+ " INNER JOIN article AS a ON a.`id`=c.`collect_article_id` "
				+ " INNER JOIN USER AS u ON u.`id`=a.`user_id` " + " WHERE c.`collect_user_id`=:u_id "
				+ " ORDER BY c.`create_time` DESC ";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).addEntity(CollectVO.class).list();
	}

	@Override
	public Integer getCountByUser(int user_id) {
		String sql = "SELECT COUNT(*) FROM collect WHERE collect_user_id=:u_id ";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", user_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCountByArticle(int article_id) {
		String sql = "SELECT COUNT(*) FROM collect WHERE collect_article_id=:a_id ";
		Object object = getSession().createSQLQuery(sql).setParameter("a_id", article_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(*) FROM collect";
		Object object = getSession().createSQLQuery(sql).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Collect hasCollected(int user_id, int article_id) {
		String hql = "FROM Collect WHERE collect_article_id=:a_id AND collect_user_id=:u_id";
		return (Collect) getSession().createQuery(hql).setParameter("a_id", article_id).setParameter("u_id", user_id).setMaxResults(1).uniqueResult();
	}

}
