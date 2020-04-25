package com.zd.Service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zd.DAO.PermissionDAO;
import com.zd.Entity.Permission;
import com.zd.Service.PermissionService;
import com.zd.Util.RoleUtil;

@Service("PermissionService")
@Transactional
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	PermissionDAO permissionDAO;

	@Override
	public List<Permission> get() {
		return permissionDAO.get();
	}

	@Override
	public Permission insert(Permission permission) {
		permissionDAO.insert(permission);
		if (permission.getId() != 0) {
			reInit();
			return permission;
		}
		return null;
	}

	@Override
	public Integer delete(Permission permission) {
		Integer r = permissionDAO.delete(permission);
		reInit();
		return r;
	}

	@Override
	public Permission update(Permission permission) {
		Permission p = permissionDAO.update(permission);
		reInit();
		return p;
	}

	@Override
	public Permission get(int id) {
		return permissionDAO.get(id);
	}

	@Override
	public Permission getByUrl(String url) {
		return permissionDAO.getByUrl(url);
	}

	@Override
	@Async
	public void reInit() {
		RoleUtil.init();
	}

}
