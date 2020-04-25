package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.CategoryDAO;
import com.zd.Entity.Category;
import com.zd.VO.CategoryVO;

@Repository("CategoryDAO")
public class CategoryDAOImpl extends BaseDAOImpl implements CategoryDAO {

	@Override
	public Category insert(Category category) {
		Integer result = (Integer) getSession().save(category);
		if (result != null) {
			return category;
		}
		return null;
	}

	@Override
	public Category findById(int category_id) {
		String hql = "FROM Category WHERE id=:id";
		Category category = (Category) getSession().createQuery(hql).setParameter("id", category_id).uniqueResult();
		return category;
	}

	@Override
	public Category findByName(String name) {
		String hql = "FROM Category WHERE category_name=:name";
		Category category = (Category) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
		return category;
	}

	@Override
	public List<Category> findByArticleId(int article_id) {
		String sql = "SELECT c.* FROM category AS c,article_category_ref AS tc WHERE tc.`category_id`=c.`id` AND tc.`article_id`=:article_id";
		List<Category> result = getSession().createSQLQuery(sql).setParameter("article_id", article_id)
				.addEntity(Category.class).list();
		return result;
	}

	@Override
	public List<Category> findByUserId(int user_id) {
		String sql = "SELECT c.* FROM category AS c,USER AS u WHERE c.`creator_id`=u.`id` AND u.`id`=:user_id";
		List<Category> result = getSession().createSQLQuery(sql).setParameter("user_id", user_id)
				.addEntity(Category.class).list();
		return result;
	}

	@Override
	public List<CategoryVO> findByUsedWithUserId(int user_id) {
		String sql = "SELECT c.*,COUNT(*) AS times FROM category AS c,article_category_ref AS tc,article AS a WHERE c.`id`=tc.`category_id` AND tc.`article_id`=a.`id` AND a.`user_id`=:user_id GROUP BY c.`id` ORDER BY times DESC";
		List<CategoryVO> result = (List<CategoryVO>) getSession().createSQLQuery(sql).setParameter("user_id", user_id)
				.addEntity(CategoryVO.class).list();
		return result;
	}

	@Override
	public List<CategoryVO> findByUsedTimes(int limit) {
		String sql = "SELECT c.*,COUNT(*) AS times FROM category AS c,article_category_ref AS tc WHERE c.`id`=tc.`category_id` GROUP BY c.`id` ORDER BY times DESC LIMIT :limit ";
		List<CategoryVO> result = (List<CategoryVO>) getSession().createSQLQuery(sql).setParameter("limit", limit)
				.addEntity(CategoryVO.class).list();
		return result;
	}

	@Override
	public List<Category> getByTime(String data) {
		String hql = "FROM Category WHERE create_time=:data";
		List<Category> result = getSession().createQuery(hql).setParameter("data", data).list();
		return result;
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(*) FROM category";
		return Integer.valueOf(getSession().createSQLQuery(sql).list().get(0).toString());
	}

	@Override
	public List<Category> getAll() {
		String hql = "FROM Category ORDER BY create_time DESC";
		List<Category> result = getSession().createQuery(hql).list();
		return result;
	}

	@Override
	public List<CategoryVO> get(int limit, int start, int order) {
//		String hql = "FROM Category ORDER BY create_time ";
//		if (order == 1) {
//			hql += "DESC";
//		}
		String sql="SELECT c.*,COUNT(tc.`category_id`) AS times "
				+ " FROM category AS c "
				+ " LEFT JOIN article_category_ref AS tc"
				+ " ON tc.`category_id`=c.`id`"
				+ " GROUP BY c.`id`";
		if (order == 1) {
			sql += "ORDER BY c.`create_time`";
		} else if (order == 0) {
			sql += "ORDER BY c.`create_time` DESC";
		} else if (order == 2) {
			sql += "ORDER BY times DESC";
		} else if (order == 3) {
			sql += "ORDER BY is_order DESC";
		}
		sql += " LIMIT :start,:limit";
		List<CategoryVO> result = getSession().createSQLQuery(sql).setParameter("start", start)
				.setParameter("limit", limit).addEntity(CategoryVO.class).list();
		return result;
	}

	@Override
	public List<Category> getByPid(int pid) {
		String hql = "FROM Category WHERE category_pid=:pid";
		return getSession().createQuery(hql).setParameter("pid", pid).list();
	}

	@Override
	public Category update(Category category) {
		getSession().update(category);
		return category;
	}

	@Override
	public Integer delete(int category_id) {
		String hql = "DELETE FROM Category WHERE id=:id";
		return getSession().createQuery(hql).setParameter("id", category_id).executeUpdate();
	}

	@Override
	public Integer updateWeight(int cate_id, int weight) {
		String hql = "UPDATE Category SET is_Order=:weight WHERE id=:id";
		return getSession().createQuery(hql).setParameter("weight", weight).setParameter("id", cate_id).executeUpdate();
	}

	@Override
	public List<Category> getByWeight() {
		String hql = "FROM Category WHERE is_Order!=0 ORDER BY is_Order DESC";
		return getSession().createQuery(hql).list();
	}
	
	@Override
	public Integer getCountByWeight(){
		String sql="SELECT COUNT(id) FROM Category WHERE is_Order!=0";
		return Integer.valueOf(getSession().createSQLQuery(sql).list().get(0).toString());
	}

	@Override
	public Category getById(int id) {
		String hql = "FROM Category WHERE id=:id";
		return (Category) getSession().createQuery(hql).setParameter("id", id).uniqueResult();
	}
}
