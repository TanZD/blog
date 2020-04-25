package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Permission;

public interface PermissionDAO {
	public List<Permission> get();

	public Permission insert(Permission permission);

	public Integer delete(Permission permission);

	public Permission update(Permission permission);

	public Permission get(int id);

	public Permission getByUrl(String url);

}
