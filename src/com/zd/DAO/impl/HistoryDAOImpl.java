package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.HistoryDAO;
import com.zd.Entity.History;
import com.zd.VO.HistoryVO;

@Repository("HistoryDAO")
public class HistoryDAOImpl extends BaseDAOImpl implements HistoryDAO {

	@Override
	public History insert(History history) {
		String sql="INSERT INTO history(history_article_id,history_user_id,create_time,access_time) "
				+ " VALUES(:a_id , :u_id ,DATE_FORMAT(NOW(),'%y-%m-%d'),NOW()) "
				+ " ON DUPLICATE KEY UPDATE access_time=NOW();";
		getSession().createSQLQuery(sql).setParameter("a_id", history.getHistory_article_id())
				.setParameter("u_id", history.getHistory_user_id()).executeUpdate();
		return history;
	}

	@Override
	public List<HistoryVO> get(int user_id, int start, int limit) {
		String sql="SELECT h.`id`,h.`history_article_id`,h.`history_user_id`,DATE_FORMAT(h.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",a.`title`,u.`username` "
				+ " FROM history AS h "
				+ " INNER JOIN article AS a ON a.`id`=h.`history_article_id` "
				+ " INNER JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " WHERE h.`history_user_id`=:u_id "
				+ " ORDER BY h.`create_time` DESC "
				+ " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(HistoryVO.class).list();
	}

	@Override
	public List<HistoryVO> get(int user_id) {
		String sql="SELECT h.`id`,h.`history_article_id`,h.`history_user_id`,DATE_FORMAT(h.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",a.`title`,u.`username` "
				+ " FROM history AS h "
				+ " INNER JOIN article AS a ON a.`id`=h.`history_article_id` "
				+ " INNER JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " WHERE h.`history_user_id`=:u_id "
				+ " ORDER BY h.`create_time` DESC ";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).addEntity(HistoryVO.class).list();
	}

	@Override
	public List<HistoryVO> getByArticleId(int article_id) {
		String sql="SELECT h.`id`,h.`history_article_id`,h.`history_user_id`,DATE_FORMAT(h.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",a.`title`,u.`username` "
				+ " FROM history AS h "
				+ " LEFT JOIN article AS a ON a.`id`=h.`history_article_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=h.`history_user_id` "
				+ " WHERE h.`history_article_id`= :a_id "
				+ " ORDER BY h.`create_time` DESC ";
		return getSession().createSQLQuery(sql).setParameter("a_id", article_id).addEntity(HistoryVO.class).list();
	}

	@Override
	public List<HistoryVO> getByArticleId(int article_id, int start, int limit) {
		String sql="SELECT h.`id`,h.`history_article_id`,h.`history_user_id`,DATE_FORMAT(h.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",a.`title`,u.`username` "
				+ " FROM history AS h "
				+ " LEFT JOIN article AS a ON a.`id`=h.`history_article_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=h.`history_user_id` "
				+ " WHERE h.`history_article_id`= :a_id "
				+ " ORDER BY h.`create_time` DESC "
				+ " LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("a_id", article_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(HistoryVO.class).list();
	}

	@Override
	public Integer delete(int id) {
		String hql = "DELETE FROM History WHERE id=:id";
		return getSession().createQuery(hql).setParameter("id", id).executeUpdate();
	}

	@Override
	public Integer deleteByUserId(int user_id) {
		String hql = "DELETE FROM History WHERE history_user_id=:u_id";
		return getSession().createQuery(hql).setParameter("u_id", user_id).executeUpdate();
	}

	@Override
	public Integer getCountByUser(int user_id) {
		String sql = "SELECT COUNT(*) FROM history WHERE history_user_id=:u_id";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", user_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCountByArticle(int article_id) {
		String sql = "SELECT COUNT(*) FROM history WHERE history_article_id=:a_id";
		Object object = getSession().createSQLQuery(sql).setParameter("a_id", article_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(*) FROM history";
		Object object = getSession().createSQLQuery(sql).list().get(0);
		return Integer.valueOf(object.toString());
	}

}
