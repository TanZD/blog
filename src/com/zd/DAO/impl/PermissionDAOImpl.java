package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.PermissionDAO;
import com.zd.Entity.Permission;

@Repository
public class PermissionDAOImpl extends BaseDAOImpl implements PermissionDAO {

	@Override
	public List<Permission> get() {
		String hql = "FROM Permission";
		return getSession().createQuery(hql).list();
	}

	@Override
	public Permission insert(Permission permission) {
		getSession().save(permission);
		return permission;
	}

	@Override
	public Integer delete(Permission permission) {
		String hql = "DELETE Permission WHERE id=:id";
		return getSession().createQuery(hql).setParameter("id", permission.getId()).executeUpdate();
	}

	@Override
	public Permission update(Permission permission) {
		getSession().update(permission);
		return permission;
	}

	@Override
	public Permission get(int id) {
		String hql = "FROM Permission WHERE id=:id";
		return (Permission) getSession().createQuery(hql).setParameter("id", id).setMaxResults(1).uniqueResult();
	}

	@Override
	public Permission getByUrl(String url) {
		String hql = "FROM Permission WHERE url=:url";
		return (Permission) getSession().createQuery(hql).setParameter("url", url).setMaxResults(1).uniqueResult();
	}

}
