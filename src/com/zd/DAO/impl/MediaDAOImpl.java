package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.MediaDAO;
import com.zd.Entity.Media;

@Repository("MediaDAOImpl")
public class MediaDAOImpl extends BaseDAOImpl implements MediaDAO {

	@Override
	public Media insert(Media media) {
		getSession().save(media);
		return media;
	}

	@Override
	public Integer delete(Media media) {
		String hql = "DELETE FROM Media WHERE id=:id";
		return getSession().createQuery(hql).setParameter("id", media.getId()).executeUpdate();
	}

	@Override
	public List<Media> get(int start, int limit) {
		String hql = "FROM Media ";
		return getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public List<Media> getByName(String name, int type, int start, int limit) {
		String hql = "FROM Media WHERE file_name LIKE CONCAT('%',:name,'%') AND file_type=:type";
		return getSession().createQuery(hql).setParameter("name", name).setParameter("type", type).list();
	}

	@Override
	public Media getById(int id) {
		String hql = "FROM Media WHERE id=:id";
		return (Media) getSession().createQuery(hql).setParameter("id", id).uniqueResult();
	}

	@Override
	public List<Media> getByUser(int user_id, int type, int start, int limit) {
		String hql = "FROM media WHERE user_id=:u_id AND file_type=:type";
		return getSession().createQuery(hql).setParameter("u_id", user_id).setParameter("type", type)
				.setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public List<Media> getByArticle(int article_id, int type, int start, int limit) {
		String hql = "FROM media WHERE article_id=:a_id AND file_type=:type";
		return getSession().createQuery(hql).setParameter("a_id", article_id).setParameter("type", type)
				.setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public List<Media> getByArticle(int article_id) {
		String hql = "FROM Media WHERE article_id=:id";
		return getSession().createQuery(hql).setParameter("id", article_id).list();
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(id) FROM media";
		Object object = getSession().createSQLQuery(sql).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCountByType(int type) {
		String sql = "SELECT COUNT(*) FROM media WHERE file_type=:type";
		Object object = getSession().createSQLQuery(sql).setParameter("type", type).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCountByUser(int user_id, int type) {
		String sql = "SELECT COUNT(*) FROM media WHERE user_id=:u_id AND file_type=:type";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", user_id).setParameter("type", type).list()
				.get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCountByName(String name, int type) {
		String sql = "SELECT COUNT(*) FROM media WHERE file_name LIKE CONCAT('%',:name,'%') AND file_type=:type";
		Object object = getSession().createSQLQuery(sql).setParameter("name", name).setParameter("type", type).list()
				.get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCountByArticle(int article_id, int type) {
		String sql = "SELECT COUNT(*) FROM media WHERE article_id=:a_id AND file_type=:type";
		Object object = getSession().createSQLQuery(sql).setParameter("a_id", article_id).setParameter("type", type)
				.list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Media getByName(String name, int type) {
		String hql = "FROM Media WHERE file_name=:name AND file_type=:type";
		return (Media) getSession().createQuery(hql).setParameter("name", name).setParameter("type", type)
				.setMaxResults(1).uniqueResult();
	}

}
