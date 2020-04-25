package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.CommentDAO;
import com.zd.Entity.Comment;
import com.zd.VO.CommentVO;

@Repository("CommentDAO")
public class CommentDAOImpl extends BaseDAOImpl implements CommentDAO {

	String sql = "SELECT c.* ,u1.`username` AS username,u2.`username` AS p_username,a.`is_article` FROM COMMENT AS c "
			+ " LEFT JOIN USER AS u1 ON c.`comment_user_id`=u1.`id` "
			+ " LEFT JOIN USER AS u2 ON c.`comment_pid`=u2.`id` "
			+ " LEFT JOIN article AS a ON c.`comment_article_id`=a.`id` ";

	@Override
	public Comment insert(Comment comment) {
		getSession().save(comment);
		return comment;
	}

	@Override
	public Comment update(Comment comment) {
		getSession().update(comment);
		return comment;
	}

	@Override
	public Integer delete(int comment_id) {
		String hql = "DELETE FROM Comment WHERE id=:c_id";
		return getSession().createQuery(hql).setParameter("c_id", comment_id).executeUpdate();
	}

	@Override
	public List<CommentVO> getAll() {
		String sql = "SELECT c.* ,u1.`username` AS username,u2.`username` AS p_username,a.`is_article` "
				+ " FROM COMMENT AS c "
				+ " LEFT JOIN USER AS u1 ON c.`comment_user_id`=u1.`id` "
				+ " LEFT JOIN COMMENT AS cc ON cc.`id`=c.`comment_pid` "
				+ " LEFT JOIN USER AS u2 ON u2.`id`=cc.`comment_user_id` "
				+ " LEFT JOIN article AS a ON c.`comment_article_id`=a.`id` "
				+ " ORDER BY create_time DESC";
		return getSession().createSQLQuery(sql).addEntity(Comment.class).list();
	}

	@Override
	public List<CommentVO> get(int start, int limit, int order) {
		String sql = "SELECT c.* ,u1.`username` AS username,u2.`username` AS p_username,a.`is_article` "
				+ " FROM COMMENT AS c "
				+ " LEFT JOIN USER AS u1 ON c.`comment_user_id`=u1.`id` "
				+ " LEFT JOIN COMMENT AS cc ON cc.`id`=c.`comment_pid` "
				+ " LEFT JOIN USER AS u2 ON u2.`id`=cc.`comment_user_id` "
				+ " LEFT JOIN article AS a ON c.`comment_article_id`=a.`id` ";
		sql += getOrder(order);
		sql += " LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(CommentVO.class).list();
	}

	@Override
	public List<CommentVO> getByArticleId(int article_id) {
		String sql = "SELECT c.* ,u1.`username` AS username,u2.`username` AS p_username,a.`is_article` "
				+ " FROM COMMENT AS c "
				+ " LEFT JOIN USER AS u1 ON c.`comment_user_id`=u1.`id` "
				+ " LEFT JOIN COMMENT AS cc ON cc.`id`=c.`comment_pid` "
				+ " LEFT JOIN USER AS u2 ON u2.`id`=cc.`comment_user_id` "
				+ " LEFT JOIN article AS a ON c.`comment_article_id`=a.`id` "
				+ " WHERE c.`comment_article_id`=:a_id ORDER BY create_time DESC";
		return getSession().createSQLQuery(sql).setParameter("a_id", article_id).addEntity(CommentVO.class).list();
	}

	@Override
	public List<CommentVO> getByArticleId(int article_id, int start, int limit, int order) {
		String sql = "SELECT c.* ,u1.`username` AS username,u2.`username` AS p_username,a.`is_article` "
				+ " FROM COMMENT AS c "
				+ " LEFT JOIN USER AS u1 ON c.`comment_user_id`=u1.`id` "
				+ " LEFT JOIN COMMENT AS cc ON cc.`id`=c.`comment_pid` "
				+ " LEFT JOIN USER AS u2 ON u2.`id`=cc.`comment_user_id` "
				+ " LEFT JOIN article AS a ON c.`comment_article_id`=a.`id` "
				+ " WHERE c.`comment_article_id`=:a_id ";
		sql += getOrder(order);
		sql += " LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("a_id", article_id).setParameter("a_id", article_id)
				.setParameter("start", start).setParameter("limit", limit).addEntity(CommentVO.class).list();
	}

	@Override
	public List<CommentVO> getByUserId(int user_id) {
		String sql = this.sql + " WHERE c.`comment_user_id`=:u_id";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).addEntity(CommentVO.class).list();
	}

	@Override
	public List<CommentVO> getByUserId(int user_id, int start, int limit, int order) {
		String sql = this.sql + " WHERE c.`comment_user_id`=:u_id";
		sql += getOrder(order);
		sql += " LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(CommentVO.class).list();
	}

	@Override
	public List<CommentVO> getByAcceptorId(int user_id) {
		String sql = this.sql + "LEFT JOIN COMMENT AS cc ON cc.`id`=c.`comment_pid` "
				+ " WHERE cc.`comment_user_id`=:u_id ORDER BY c.`create_time` DESC " + " LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).addEntity(CommentVO.class).list();
	}

	@Override
	public List<CommentVO> getByAcceptorId(int user_id, int start, int limit, int order) {
		String sql = this.sql + "LEFT JOIN COMMENT AS cc ON cc.`id`=c.`comment_pid` "
				+ " WHERE cc.`comment_user_id`=:u_id ORDER BY c.`create_time` DESC " + " LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(CommentVO.class).list();
	}

	@Override
	public Comment getByPid(int p_id) {
		String hql = "FROM Comment WHERE comment_pid=:p_id";
		return (Comment) getSession().createQuery(hql).setParameter("p_id", p_id).uniqueResult();
	}

	@Override
	public List<CommentVO> getByDate(String date) {
		// String hql = "FROM Comment WHERE create_time=:create_time";
		String sql = this.sql + " WHERE c.`create_time` LIKE :date";
		return getSession().createSQLQuery(sql).setParameter("date", "%" + date + "%").addEntity(CommentVO.class)
				.list();
	}

	@Override
	public List<CommentVO> getByDate(String date, int start, int limit) {
		// String hql = "FROM Comment WHERE create_time=:create_time";
		String sql = this.sql + " WHERE c.`create_time` LIKE :date LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("date", "%" + date + "%").setParameter("start", start)
				.setParameter("limit", limit).addEntity(CommentVO.class).list();
	}

	@Override
	public List<CommentVO> getByContent(String content, int start, int limit) {
		// String hql = "FROM Comment WHERE comment_content LIKE :like ORDER BY
		// create_time DESC ";
		String sql = this.sql + " c.`comment_content` LIKE :like";
		return getSession().createSQLQuery(sql).setParameter("like", "%" + content + "%").setParameter("start", start)
				.setParameter("limit", limit).addEntity(CommentVO.class).list();
	}

	@Override
	public CommentVO getById(int id) {
		// String hql = "FROM CommentVO WHERE id=:id";
		String sql = this.sql + " WHERE c.`id`=:id";
		return (CommentVO) getSession().createSQLQuery(sql).setParameter("id", id).addEntity(CommentVO.class)
				.uniqueResult();
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(*) FROM comment";
		return Integer.valueOf(getSession().createSQLQuery(sql).list().get(0).toString());
	}

	@Override
	public Integer getCountByContent(String content) {
		String hql = "SELECT COUNT(*) FROM comment WHERE comment_content LIKE :like";
		Object data = getSession().createQuery(hql).setParameter("like", "%" + content + "%").list().get(0);
		return Integer.valueOf(data.toString());
	}

	@Override
	public Integer getCountByUserId(int user_id) {
		String sql = "SELECT COUNT(*) FROM comment WHERE comment_user_id=:user_id";
		Object data = getSession().createSQLQuery(sql).setParameter("user_id", user_id).list().get(0);
		return Integer.valueOf(data.toString());
	}

	@Override
	public Integer getCountByAcceptorId(int user_id) {
		String sql = "SELECT COUNT(*) FROM COMMENT AS c INNER JOIN COMMENT AS cc ON cc.`id`=c.`comment_pid` AND cc.`comment_user_id`=:u_id";
		Object data = getSession().createSQLQuery(sql).setParameter("u_id", user_id).list().get(0);
		return Integer.valueOf(data.toString());
	}

	@Override
	public Integer getCountByArticleId(int article_id) {
		String sql = "SELECT COUNT(*) FROM comment WHERE comment_article_id=:article_id";
		Object data = getSession().createSQLQuery(sql).setParameter("article_id", article_id).list().get(0);
		return Integer.valueOf(data.toString());
	}

	@Override
	public Integer getCountByDate(String date) {
		String sql = "SELECT COUNT(*) FROM comment WHERE create_time LIKE :create_time";
		Object data = getSession().createSQLQuery(sql).setParameter("create_time", "%" + date + "%").list().get(0);
		return Integer.valueOf(data.toString());
	}

	public String getOrder(int order) {
		String hql = " ";
		switch (order) {
		case 2:
			hql += "ORDER BY create_time ";
			break;
		case 1:
		default:
			hql += "ORDER BY create_time DESC";
			break;
		}
		return hql;
	}

}
