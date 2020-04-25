package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.SensitiveWordDAO;
import com.zd.Entity.SensitiveWord;

@Repository
public class SensitiveWordDAOImpl extends BaseDAOImpl implements SensitiveWordDAO {

	@Override
	public SensitiveWord insert(SensitiveWord word) {
		getSession().save(word);
		if (word.getId() != 0) {
			return word;
		}
		return null;
	}

	@Override
	public Integer delete(SensitiveWord word) {
		String hql = "DELETE FROM SensitiveWord WHERE id=:id";
		return getSession().createQuery(hql).setParameter("id", word.getId()).executeUpdate();
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(id) FROM SensitiveWord";
		Object object = getSession().createSQLQuery(sql).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public List<SensitiveWord> get() {
		String hql = "FROM SensitiveWord";
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<SensitiveWord> get(int start, int limit) {
		String hql = "FROM SensitiveWord";
		return getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public SensitiveWord getByWord(String word) {
		String hql = "FROM SensitiveWord WHERE word=:word";
		return (SensitiveWord) getSession().createQuery(hql).setParameter("word", word).setMaxResults(1).uniqueResult();
	}

}
