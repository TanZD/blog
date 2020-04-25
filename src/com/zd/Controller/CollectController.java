package com.zd.Controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Collect;
import com.zd.Entity.User;
import com.zd.Service.CollectService;
import com.zd.Util.Msg;
import com.zd.Util.MyUtil;
import com.zd.VO.CollectVO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "collect")
public class CollectController {

	@Autowired
	CollectService collectService;

	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String save(Collect collect, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		// 如果用户未登录
		if (user != null) {
			if (user.getId() == collect.getCollect_user_id()) {
				Collect old = collectService.hasCollected(collect.getCollect_user_id(),
						collect.getCollect_article_id());
				if (old == null) {
					collect.setCreate_time(MyUtil.ToadyDate());
					collect = collectService.insert(collect);
					if (collect.getId() != 0) {
						result.setCodeMsg(Msg.success_code, Msg.success);
					}
				} else {
					collectService.delete(collect.getCollect_article_id(), collect.getCollect_user_id());
					result.setCodeMsg(Msg.success_code, Msg.CANCEL);
				}
			} else {
				result.setCodeMsg(Msg.fail_code, Msg.fail);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/user/{user_id}/page/{page}/limit/{limit}", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get(@PathVariable("user_id") int user_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, HttpServletRequest request) {
		JSONResult<CollectVO> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		// 如果用户未登录
		if (user != null) {
			// 检测登录用户是否自己
			// 。。。
			if (user.getId() == user_id || user.getIsAdmin() == 1) {
				result = collectService.getByUser(user_id, page, limit);
			} else {
				result.setCodeMsg(Msg.err_code, "别偷看别人的啊");
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "delete/user/{user_id}/article/{article_id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("user_id") int user_id, @PathVariable("article_id") int article_id,
			HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user.getId() == user_id) {
				Integer data = collectService.delete(article_id, user_id);
				if (data != 0) {
					result.setCodeMsg(Msg.OK, Msg.success);
				} else {
					result.setCodeMsg(Msg.fail_code, Msg.fail);
				}
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "getCount", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getCount() {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		HashMap<String, Integer> data = new HashMap<>();
		data.put("totalNum", collectService.getCount());
		result.setData(data);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "isCollected/{article_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String isCollected(@PathVariable("article_id") int article_id, HttpServletRequest reqeust) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		User user = (User) reqeust.getSession().getAttribute("user");
		if (user != null) {
			Collect c = collectService.hasCollected(user.getId(), article_id);
			if (c != null) {
				result.setCodeMsg(Msg.success_code, "已收藏");
			} else {
				result.setCodeMsg(Msg.fail_code, "未收藏");
			}
		} else {
			result.setCodeMsg(Msg.err_code, Msg.NOT_LOGIN);
		}
		return JSONObject.fromObject(result).toString();
	}

}
