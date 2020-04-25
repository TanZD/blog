package com.zd.Controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Permission;
import com.zd.Service.PermissionService;
import com.zd.Util.Msg;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "permission")
public class PermissionController {
	@Autowired
	PermissionService permissionService;

	@RequestMapping(value = "getAll", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get() {
		JSONResult<Permission> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		List<Permission> data = permissionService.get();
		result.setModels(data);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String save(Permission permission) {
		JSONResult<Permission> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		if (permission != null) {
			if (StringUtils.isNotEmpty(permission.getUrl())) {
				Permission s = permissionService.getByUrl(permission.getUrl());
				if (s != null) {
					result.setCodeMsg(Msg.fail_code, "已存在");
				} else {
					permission = permissionService.insert(permission);
					if (permission != null) {
						result.setCodeMsg(Msg.success_code, Msg.success);
					}
				}
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("id") int id) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		Permission p = new Permission();
		p.setId(id);
		Integer r = permissionService.delete(p);
		if (r == 0 || r == null) {
			result.setCodeMsg(Msg.fail_code, Msg.fail);
		}
		return JSONObject.fromObject(result).toString();
	}

}
