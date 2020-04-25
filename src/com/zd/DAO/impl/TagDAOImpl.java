package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.TagDAO;
import com.zd.Entity.Tag;
import com.zd.VO.CategoryVO;
import com.zd.VO.TagVO;

@Repository("TagDAO")
public class TagDAOImpl extends BaseDAOImpl implements TagDAO {

	@Override
	public Tag insert(Tag tag) {
		Integer result = (Integer) getSession().save(tag);
		if (result != 0) {
			return tag;
		}
		return null;
	}

	@Override
	public Tag findByName(String name) {
		String hql = "FROM Tag WHERE tag_name=:name";
		Tag tag = (Tag) getSession().createQuery(hql).setParameter("name", name).setMaxResults(1).uniqueResult();
		return tag;
	}

	@Override
	public List<Tag> findByArticleId(int article_id) {
		String sql = "SELECT t.* FROM tag AS t,article_tag_ref AS tr WHERE tr.`article_id`=:a_id AND tr.`tag_id`=t.`id`";
		List<Tag> tag = (List<Tag>) getSession().createSQLQuery(sql).setParameter("a_id", article_id)
				.addEntity(Tag.class).list();
		return tag;
	}

	@Override
	public List<Tag> findByUserId(int user_id) {
		String sql = "SELECT t.* FROM tag AS t,USER AS u WHERE t.`user_id`=u.`id` AND t.`user_id`=:user_id";
		List<Tag> tag = (List<Tag>) getSession().createSQLQuery(sql).setParameter("user_id", user_id).list();
		return tag;
	}

	@Override
	public List<TagVO> findByUsedTimes(int limit) {
		String sql = "SELECT t.*,COUNT(*) AS times FROM tag AS t,article_tag_ref AS tr WHERE tr.`tag_id`=t.`id` GROUP BY tr.`tag_id` ORDER BY times DESC LIMIT :limit";
		List<TagVO> tag = (List<TagVO>) getSession().createSQLQuery(sql).setParameter("limit", limit)
				.addEntity(TagVO.class).list();
		return tag;
	}

	@Override
	public List<TagVO> findByUsedWithUserId(int user_id) {
		// String sql = "SELECT t.* FROM tag AS t,article_tag_ref AS tr,article
		// AS a WHERE t.`id`=tr.`tag_id` AND tr.`article_id`=a.`id` AND
		// a.`user_id`=:user_id";
		String sql = "SELECT t.*,COUNT(*) AS times FROM tag AS t,article_tag_ref AS tr,article AS a WHERE t.`id`=tr.`tag_id` AND tr.`article_id`=a.`id` AND a.`user_id`=:user_id GROUP BY t.`id` ORDER BY times DESC";
		List<TagVO> tag = (List<TagVO>) getSession().createSQLQuery(sql).setParameter("user_id", user_id)
				.addEntity(TagVO.class).list();
		return tag;
	}

	@Override
	public Tag findById(int tag_id) {
		String hql = "FROM Tag WHERE id=:tag_id";
		Tag tag = (Tag) getSession().createQuery(hql).setParameter("tag_id", tag_id).uniqueResult();
		return tag;
	}

	@Override
	public List<TagVO> get(int limit, int start, int order) {
		String sql="SELECT t.*,COUNT(tr.`tag_id`) AS times "
				+ " FROM tag AS t "
				+ " LEFT JOIN article_tag_ref AS tr"
				+ " ON tr.`tag_id`=t.`id`"
				+ " GROUP BY t.`id`";
		if (order == 1) {
			sql += "ORDER BY t.`create_time`";
		} else if (order == 0) {
			sql += "ORDER BY t.`create_time` DESC";
		} else if (order == 2) {
			sql += "ORDER BY times DESC";
		}
		sql += " LIMIT :start,:limit";
		List<TagVO> result = getSession().createSQLQuery(sql).setParameter("start", start)
				.setParameter("limit", limit).addEntity(TagVO.class).list();
		return result;
	}

	@Override
	public List<Tag> getAll() {
		String hql = "FROM Tag";
		List<Tag> tag = (List<Tag>) getSession().createQuery(hql).list();
		return tag;
	}

	@Override
	public List<Tag> getByTime(String data) {
		String hql = "FROM Tag WHERE create_time=:time";
		List<Tag> tag = (List<Tag>) getSession().createQuery(hql).setParameter("time", data).list();
		return tag;
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(*) AS COUNT FROM tag ";
		return Integer.valueOf(getSession().createSQLQuery(sql).list().get(0).toString());
	}

	// @Override
	// public List<Tag> getByCreateTime(String data) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public Tag update(Tag tag) {
		getSession().update(tag);
		return null;
	}

	@Override
	public Integer delete(int tag_id) {
		String hql = "DELETE FROM Tag WHERE id=:tag_id";
		return getSession().createQuery(hql).setParameter("tag_id", tag_id).executeUpdate();
	}

}
