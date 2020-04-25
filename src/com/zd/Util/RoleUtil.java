package com.zd.Util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zd.Entity.Permission;
import com.zd.Service.PermissionService;

@Component
public class RoleUtil {
	static PermissionService permissionService;
	private static boolean isLoad = false;

	public static List<Permission> URIRole = new ArrayList<>();

	@Autowired
	PermissionService service;

	@PostConstruct
	private void beforeInit() {
		permissionService = this.service;
	}

	public static boolean getIsLoad() {
		return isLoad;
	}

	public static void init() {
		URIRole.clear();
		URIRole = permissionService.get();
		isLoad = true;
	}

}
