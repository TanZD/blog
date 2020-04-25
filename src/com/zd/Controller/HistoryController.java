package com.zd.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zd.DTO.JSONResult;
import com.zd.Entity.History;
import com.zd.Entity.User;
import com.zd.Service.HistoryService;
import com.zd.Util.Msg;
import com.zd.VO.HistoryVO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "history")
public class HistoryController {
	@Autowired
	HistoryService historyService;

	@RequestMapping(value = "get/user/{user_id}/page/{page}/limit/{limit}", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get(@PathVariable("user_id") int user_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, HttpServletRequest request) {
		JSONResult<HistoryVO> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if(user.getId()==user_id){
				result = historyService.get(user_id, page, limit);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get(int article_id, HttpServletRequest request) {
		JSONResult<History> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.fail);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			History h = new History();
			h.setHistory_user_id(user.getId());
			h.setHistory_article_id(article_id);
			h = historyService.save(h);
			result.setCodeMsg(Msg.success_code, Msg.success);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("id") int id, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			Integer d = historyService.delete(id);
			result.setCodeMsg(Msg.success_code, Msg.success);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "delete/user/{user_id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String deleteByUserid(@PathVariable("user_id") int user_id, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if(user.getId()==user_id){
				Integer r = historyService.deleteByUserId(user_id);
				if (r != 0) {
					result.setCodeMsg(Msg.success_code, Msg.success);
				}
			}
		}
		return JSONObject.fromObject(result).toString();
	}

}
