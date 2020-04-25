package com.zd.DAO.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.FollowDAO;
import com.zd.DTO.JSONResult;
import com.zd.Entity.Follow;
import com.zd.VO.FollowVO;

@Repository("FollowDAO")
public class FollowDAOImpl extends BaseDAOImpl implements FollowDAO {

	@Override
	public Follow insert(Follow follow) throws Exception{
		getSession().save(follow);
		return follow;
	}

	@Override
	public Integer delete(int user_id, int p_id) {
		String hql = "DELETE FROM Follow WHERE follow_user_id=:u_id AND follow_pid=:p_id";
		return getSession().createQuery(hql).setParameter("u_id", user_id).setParameter("p_id", p_id).executeUpdate();
	}

	@Override
	public List<FollowVO> get(int user_id, int start, int limit) {
		String sql = "SELECT f.*,u.`username` AS p_username,u.`image` AS p_image,u.`profile`"
				+ " FROM follow AS f "
				+ " INNER JOIN USER AS u ON u.`id`=f.`follow_pid` "
				+ " WHERE f.`follow_user_id`=:u_id "
				+ " ORDER BY f.`id` DESC"
				+ " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(FollowVO.class).list();
	}

	@Override
	public List<Follow> get(int user_id) {
//		String sql = "SELECT f.* "
//				+ " FROM follow AS f "
//				+ " WHERE f.`follow_user_id`=:u_id "
//				+ " ORDER BY f.`id` DESC";
		// return getSession().createSQLQuery(sql).setParameter("u_id",user_id).addEntity(Follow.class).list();
		String hql = "FROM Follow WHERE follow_user_id=:u_id";
		return getSession().createQuery(hql).setParameter("u_id", user_id).list();
	}
	
	@Override
	public List<FollowVO> getByPid(int p_id) {
		String sql="SELECT f.`id`,f.`follow_pid`,f.`follow_user_id`,u.`image` AS p_image,u.`username` AS p_username,u.`profile` "
				+ " FROM follow AS f"
				+ " INNER JOIN USER AS u ON u.`id`=f.`follow_user_id`"
				+ " WHERE f.`follow_pid`=:p_id"
				+ " ORDER BY f.`id` DESC"
				+ " LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("p_id", p_id).addEntity(FollowVO.class).list();
	}
	
	@Override
	public List<FollowVO> getByPid(int p_id, int start, int limit) {
		String sql="SELECT f.`id`,f.`follow_pid`,f.`follow_user_id`,u.`image` AS p_image,u.`username` AS p_username,u.`profile` "
				+ " FROM follow AS f"
				+ " INNER JOIN USER AS u ON u.`id`=f.`follow_user_id`"
				+ " WHERE f.`follow_pid`=:p_id"
				+ " ORDER BY f.`id` DESC"
				+ " LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("p_id", p_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(FollowVO.class).list();
	}
	
	@Override
	public Follow hasFollow(int user_id, int p_id) {
		String hql = "FROM Follow WHERE follow_user_id=:u_id AND follow_pid=:pid";
		return (Follow) getSession().createQuery(hql).setParameter("u_id", user_id).setParameter("pid", p_id)
				.setMaxResults(1).uniqueResult();
	}
	
	@Override
	public Integer getCountByFollow(int user_id) {
		String sql = "SELECT COUNT(*) FROM follow WHERE follow_pid = :u_id";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", user_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCountByUser(int user_id) {
		String sql = "SELECT COUNT(*) FROM follow WHERE follow_user_id = :u_id";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", user_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(*) FROM follow";
		Object object = getSession().createSQLQuery(sql).list().get(0);
		return Integer.valueOf(object.toString());
	}

}
