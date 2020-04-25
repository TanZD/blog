package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.ToastDAO;
import com.zd.Entity.Toast;
import com.zd.VO.ToastVO;

@Repository
public class ToastDAOImpl extends BaseDAOImpl implements ToastDAO {

	@Override
	public Toast insert(Toast toast) {
		getSession().save(toast);
		return toast;
	}

	@Override
	public Toast update(Toast toast) {
		getSession().update(toast);
		return toast;
	}

	@Override
	public Integer delete(int toast_id) {
		String hql = "DELETE FROM Toast WHERE id=:id";
		return getSession().createQuery(hql).setParameter("id", toast_id).executeUpdate();
	}

	@Override
	public Integer deleteByCommentId(int comment_id) {
		String hql = "DELETE FROM Toast WHERE toast_comment_id=:c_id";
		return getSession().createQuery(hql).setParameter("c_id", comment_id).executeUpdate();
	}

	@Override
	public List<ToastVO> get() {
		String sql = "SELECT "
				+ "t.*,u1.`username` AS username,u2.`username` AS sender_username,c.`comment_content`,a.`title` "
				+ "FROM toast AS t " + "LEFT JOIN USER AS u1 ON u1.`id`=t.`toast_user_id` "
				+ "LEFT JOIN USER AS u2 ON u2.`id`=t.`toast_sender_id` "
				+ "LEFT JOIN COMMENT AS c ON t.`toast_comment_id`=c.`id` "
				+ "LEFT JOIN article AS a ON a.`id`=c.`comment_article_id` WHERE t.`is_read`=1;";
		return getSession().createSQLQuery(sql).addEntity(ToastVO.class).list();
	}

	@Override
	public List<ToastVO> getAll() {
		String sql = "SELECT "
				+ "t.*,u1.`username` AS username,u2.`username` AS sender_username,c.`comment_content`,a.`title` "
				+ "FROM toast AS t " + "LEFT JOIN USER AS u1 ON u1.`id`=t.`toast_user_id` "
				+ "LEFT JOIN USER AS u2 ON u2.`id`=t.`toast_sender_id` "
				+ "LEFT JOIN COMMENT AS c ON t.`toast_comment_id`=c.`id` "
				+ "LEFT JOIN article AS a ON a.`id`=c.`comment_article_id`;";
		return getSession().createSQLQuery(sql).addEntity(ToastVO.class).list();
	}

	@Override
	public List<ToastVO> getByUserId(int user_id) {
		String sql = "SELECT "
				+ "t.*,u1.`username` AS username,u2.`username` AS sender_username,c.`comment_content`,a.`title` "
				+ "FROM toast AS t " + "LEFT JOIN USER AS u1 ON u1.`id`=t.`toast_user_id` "
				+ "LEFT JOIN USER AS u2 ON u2.`id`=t.`toast_sender_id` "
				+ "LEFT JOIN COMMENT AS c ON t.`toast_comment_id`=c.`id` "
				+ "LEFT JOIN article AS a ON a.`id`=c.`comment_article_id` "
				+ "WHERE t.`is_read`=1 AND t.`toast_user_id`=:u_id ORDER BY t.`create_time` DESC";
		List<ToastVO> data = getSession().createSQLQuery(sql).setParameter("u_id", user_id).addEntity(ToastVO.class)
				.list();
		return data;
	}

	@Override
	public List<ToastVO> getByUserId(int user_id, int start, int limit) {
		String sql = "SELECT "
				+ "t.*,u1.`username` AS username,u2.`username` AS sender_username,c.`comment_content`,a.`title`,a.`is_article` "
				+ "FROM toast AS t " + "LEFT JOIN USER AS u1 ON u1.`id`=t.`toast_user_id` "
				+ "LEFT JOIN USER AS u2 ON u2.`id`=t.`toast_sender_id` "
				+ "LEFT JOIN COMMENT AS c ON t.`toast_comment_id`=c.`id` "
				+ "LEFT JOIN article AS a ON a.`id`=c.`comment_article_id` "
				+ "WHERE t.`is_read`=1 AND t.`toast_user_id`=:u_id ORDER BY t.`create_time` DESC LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ToastVO.class).list();
	}

	@Override
	public List<ToastVO> getByType(int type) {
		String sql = "SELECT "
				+ "t.*,u1.`username` AS username,u2.`username` AS sender_username,c.`comment_content`,a.`title` "
				+ "FROM toast AS t " + "LEFT JOIN USER AS u1 ON u1.`id`=t.`toast_user_id` "
				+ "LEFT JOIN USER AS u2 ON u2.`id`=t.`toast_sender_id` "
				+ "LEFT JOIN COMMENT AS c ON t.`toast_comment_id`=c.`id` "
				+ "LEFT JOIN article AS a ON a.`id`=c.`comment_article_id` "
				+ "WHERE t.`is_read`=1 AND t.`toast_type`=:type ORDER BY t.`create_time` DESC";
		return getSession().createSQLQuery(sql).setParameter("type", type).addEntity(ToastVO.class).list();
	}

	@Override
	public List<ToastVO> getByTime(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAllCount() {
		String sql = "SELECT COUNT(*) FROM toast";
		return Integer.valueOf(getSession().createSQLQuery(sql).uniqueResult().toString());
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(*) FROM toast WHERE is_read=1";
		return Integer.valueOf(getSession().createSQLQuery(sql).uniqueResult().toString());
	}

	@Override
	public Integer getCountByUserId(int user_id) {
		String sql = "SELECT COUNT(*) FROM toast WHERE is_read=1 AND toast_user_id=:u_id";
		return Integer
				.valueOf(getSession().createSQLQuery(sql).setParameter("u_id", user_id).uniqueResult().toString());
	}

	@Override
	public Integer getCountByType(int type) {
		String sql = "SELECT COUNT(*) FROM toast WHERE is_read=1 AND toast_type=:type";
		return Integer.valueOf(getSession().createSQLQuery(sql).setParameter("type", type).uniqueResult().toString());
	}

	@Override
	public Integer setIsRead(int user_id) {
		String hql="UPDATE Toast SET is_read=0 WHERE toast_user_id=:u_id";
		return getSession().createQuery(hql).setParameter("u_id", user_id).executeUpdate();
	}

}
