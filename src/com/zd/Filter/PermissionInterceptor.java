package com.zd.Filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Permission;
import com.zd.Entity.User;
import com.zd.Util.Msg;
import com.zd.Util.RoleUtil;

import net.sf.json.JSONObject;

public class PermissionInterceptor implements HandlerInterceptor {

	public static final Logger logger = LogManager.getLogger(PermissionInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		User user = (User) request.getSession().getAttribute("user");
		// 获取库中url权限数据
		if (!RoleUtil.getIsLoad()) {
			RoleUtil.init();
		}
		logger.info("URI: " + url);
		AntPathMatcher pathMatcher = new AntPathMatcher();
		List<Permission> list = RoleUtil.URIRole;
		for (Permission p : list) {
			// 与权限里的url进行匹配
			boolean is = pathMatcher.match(p.getUrl() + "/**", url);
			if (is) {
				// 无需任何权限
				if (p.getType() == 0) {
					return true;
				} else if (p.getType() == 1) {
					// 需要登录才能访问的
					if (user != null) {
						return true;
					} else {
						JSONResult<Object> json = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
						response.setContentType("text/json;charset=utf-8");
						response.getWriter().write(JSONObject.fromObject(json).toString());
						return false;
					}
				} else {
					// 需要管理员才能访问的
					if (user != null) {
						if (user.getIsAdmin() == 1) {
							return true;
						} else {
							JSONResult<Object> json = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NO_PERMISSION);
							response.setContentType("text/json;charset=utf-8");
							response.getWriter().write(JSONObject.fromObject(json).toString());
							return false;
						}
					} else {
						JSONResult<Object> json = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
						response.setContentType("text/json;charset=utf-8");
						response.getWriter().write(JSONObject.fromObject(json).toString());
						return false;
					}
				}
			}
		}
		return true;
	}

}
