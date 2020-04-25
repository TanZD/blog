package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.UserDAO;
import com.zd.Entity.User;
import com.zd.VO.UserSimple;
import com.zd.VO.UserVO;

@Repository("UserDAO")
public class UserDAOImpl extends BaseDAOImpl implements UserDAO {

	@Override
	public User insert(User user) {
		String sql = "INSERT INTO user(username,password,user_mail,register_time) VALUES(:name,:password,:mail,curtime());";
		getSession().createSQLQuery(sql).setParameter("name", user.getUsername())
				.setParameter("password", user.getPassword()).setParameter("mail", user.getUser_mail()).executeUpdate();
		// int result = (Integer) getSession().save(user);
		return user;
	}

	@Override
	public User getByUsernameOrMail(String username) {
		String hql = "FROM User WHERE username=:U OR user_mail=:M";
		User user = (User) getSession().createQuery(hql).setParameter("U", username).setParameter("M", username)
				.setMaxResults(1).uniqueResult();
		return user;
	}

	@Override
	public User getByUserId(int id) {
		String hql = "FROM User WHERE id=:id";
		return (User) getSession().createQuery(hql).setParameter("id", id).uniqueResult();
	}

	@Override
	public User getByMail(String mail) {
		String hql = "FROM User WHERE user_mail=:N";
		User user = (User) getSession().createQuery(hql).setParameter("N", mail).setMaxResults(1).uniqueResult();
		return user;
	}

	@Override
	public User getByUsername(String username) {
		String hql = "FROM User WHERE username=:U";
		User user = (User) getSession().createQuery(hql).setParameter("U", username).setMaxResults(1).uniqueResult();
		return user;
	}

	@Override
	public String getForget(int id) {
		String hql = "SELECT forget FROM User WHERE id=:id";
		return (String) getSession().createQuery(hql).setParameter("id", id).uniqueResult();
	}

	@Override
	public User update(User user) {
		getSession().update(user);
		return user;
	}

	@Override
	public Integer updatePassword(User user) {
		String hql = "UPDATE User SET password=:password WHERE id=:id";
		return getSession().createQuery(hql).setParameter("password", user.getPassword())
				.setParameter("id", user.getId()).executeUpdate();
	}

	@Override
	public User getById(int id) {
		String hql = "FROM User WHERE id=:id";
		return (User) getSession().createQuery(hql).setParameter("id", id).setMaxResults(1).uniqueResult();
	}

	@Override
	public List<UserSimple> get(int start, int limit) {
		String sql = "SELECT id,username,user_mail,image,register_time,PROFILE,gender,isAdmin FROM USER ORDER BY register_time DESC LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(UserSimple.class).list();
	}

	@Override
	public List<UserVO> getOrderByArticleNum(int start, int limit) {
		String sql = "SELECT u.`id`,u.`image`,u.`gender`,u.`profile`,u.`register_time`,u.`isAdmin`,u.`username`,u.`user_mail`,COUNT(a.`id`) AS COUNT "
				+ " FROM USER AS u" + " LEFT JOIN article AS a ON a.`user_id`=u.`id`" + " GROUP BY u.`id`"
				+ " ORDER BY COUNT DESC" + " LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(UserVO.class).list();
	}

	@Override
	public List<UserVO> getOrderByCommentNum(int start, int limit) {
		String sql = "SELECT u.`id`,u.`image`,u.`gender`,u.`profile`,u.`register_time`,u.`isAdmin`,u.`username`,u.`user_mail`,COUNT(c.`id`) AS COUNT "
				+ " FROM USER AS u" + " LEFT JOIN COMMENT AS c ON c.`comment_user_id`=u.`id`" + " GROUP BY u.`id`"
				+ " ORDER BY COUNT DESC" + " LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(UserVO.class).list();
	}

	@Override
	public List<UserVO> getOrderByHistoryNum(int start, int limit) {
		String sql = "SELECT u.`id`,u.`image`,u.`gender`,u.`profile`,u.`register_time`,u.`isAdmin`,u.`username`,u.`user_mail`,COUNT(h.`id`) AS COUNT "
				+ " FROM USER AS u " + " LEFT JOIN history AS h ON h.`history_user_id`=u.`id`" + " GROUP BY u.`id`"
				+ " ORDER BY COUNT DESC" + " LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(UserVO.class).list();
	}

	@Override
	public List<UserVO> getOrderByCollectNum(int start, int limit) {
		String sql = "SELECT u.`id`,u.`image`,u.`gender`,u.`profile`,u.`register_time`,u.`isAdmin`,u.`username`,u.`user_mail`,COUNT(c.`id`) AS COUNT "
				+ " FROM USER AS u" + " LEFT JOIN collect AS c ON c.`collect_user_id`=u.`id`" + " GROUP BY u.`id`"
				+ " ORDER BY COUNT DESC" + " LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(UserVO.class).list();
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(id) FROM USER";
		Object object = getSession().createSQLQuery(sql).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer setForget(String forget, int id) {
		String hql = "UPDATE User SET forget=:f WHERE id=:id";
		return getSession().createQuery(hql).setParameter("f", forget).setParameter("id", id).executeUpdate();
	}

	@Override
	public List<Object[]> CountByArticleNum(int limit) {
		String sql = "SELECT u.`username`,COUNT(a.`id`) AS COUNT" + " FROM USER AS u"
				+ " LEFT JOIN article AS a ON a.`user_id`=u.`id`" + " GROUP BY u.`id`" + " ORDER BY COUNT DESC"
				+ " LIMIT :limit";
		return getSession().createSQLQuery(sql).setParameter("limit", limit).list();
	}

	@Override
	public List<Object[]> CountByRegisterTime(int limit, int order) {
		String sql = "SELECT COUNT(id) AS count,register_time " + " FROM user " + " GROUP BY register_time "
				+ " ORDER BY register_time DESC" + " LIMIT :limit";
		return getSession().createSQLQuery(sql).setParameter("limit", limit).list();
	}

	@Override
	public List<UserSimple> searchById(int id, int start, int limit) {
		String sql = "SELECT id,username,user_mail,image,register_time,PROFILE,gender,isAdmin "
				+ " FROM user WHERE id=:id ORDER BY register_time DESC LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("id", id).setParameter("limit", limit)
				.setParameter("start", start).addEntity(UserSimple.class).list();
	}

	@Override
	public List<UserSimple> searchByName(String name, int start, int limit) {
		String sql = "SELECT id,username,user_mail,image,register_time,PROFILE,gender,isAdmin "
				+ " FROM user WHERE username LIKE CONCAT('%',\"" + name + "\",'%') "
				+ " ORDER BY register_time DESC LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(UserSimple.class).list();
	}

	@Override
	public List<UserSimple> searchByMail(String mail, int start, int limit) {
		String sql = "SELECT id,username,user_mail,image,register_time,PROFILE,gender,isAdmin "
				+ " FROM user WHERE user_mail LIKE CONCAT('%',\"" + mail + "\",'%') "
				+ " ORDER BY register_time DESC LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(UserSimple.class).list();
	}

	@Override
	public List<UserSimple> searchByRegisterTime(String time, int start, int limit) {
		String sql = "SELECT id,username,user_mail,image,register_time,PROFILE,gender,isAdmin "
				+ " FROM user WHERE register_time LIKE CONCAT('%',\"" + time + "\",'%') "
				+ " ORDER BY register_time DESC LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(UserSimple.class).list();
	}

	@Override
	public List<UserSimple> searchByGender(int gender, int start, int limit) {
		String sql = "SELECT id,username,user_mail,image,register_time,PROFILE,gender,isAdmin "
				+ " FROM user WHERE gender=:gender" + " ORDER BY register_time DESC LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("gender", gender).setParameter("start", start)
				.setParameter("limit", limit).addEntity(UserSimple.class).list();
	}

	@Override
	public Integer countById(int id) {
		String sql = "SELECT COUNT(*) AS count" + " FROM user WHERE id=:id";
		String data = getSession().createSQLQuery(sql).setParameter("id", id).list().get(0).toString();
		return Integer.valueOf(data);
	}

	@Override
	public Integer countByName(String name) {
		String sql = "SELECT COUNT(*) AS count" + " FROM user WHERE username LIKE CONCAT('%',\"" + name + "\",'%') ";
		String data = getSession().createSQLQuery(sql).list().get(0).toString();
		return Integer.valueOf(data);
	}

	@Override
	public Integer countByMail(String mail) {
		String sql = "SELECT COUNT(*) AS count" + " FROM user WHERE user_mail LIKE CONCAT('%',\"" + mail + "\",'%') ";
		String data = getSession().createSQLQuery(sql).list().get(0).toString();
		return Integer.valueOf(data);
	}

	@Override
	public Integer countByRegisterTime(String time) {
		String sql = "SELECT COUNT(*) AS count" + " FROM user WHERE register_time LIKE CONCAT('%',\"" + time
				+ "\",'%') ";
		String data = getSession().createSQLQuery(sql).list().get(0).toString();
		return Integer.valueOf(data);
	}

	@Override
	public Integer countByGender(int gender) {
		String sql = "SELECT COUNT(*) FROM user WHERE gender=:gender";
		String data = getSession().createSQLQuery(sql).setParameter("gender", gender).list().get(0).toString();
		return Integer.valueOf(data);
	}

	@Override
	public UserSimple getUserSimple(int user_id) {
		String sql = "SELECT id,username,user_mail,image,register_time,PROFILE,gender,isAdmin FROM USER WHERE id=:id LIMIT 1";
		return (UserSimple) getSession().createSQLQuery(sql).setParameter("id", user_id).addEntity(UserSimple.class)
				.uniqueResult();
	}

	@Override
	public Integer setAdmin(int user_id) {
		String hql = "UPDATE User SET isAdmin=1 WHERE id=:id";
		return getSession().createQuery(hql).setParameter("id", user_id).executeUpdate();
	}

	@Override
	public Integer delete(int user_id) {
		String hql = "DELETE FROM User WHERE id=:u_id";
		return getSession().createQuery(hql).setParameter("u_id", user_id).executeUpdate();
	}

}
