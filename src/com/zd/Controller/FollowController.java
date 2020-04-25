package com.zd.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Follow;
import com.zd.Entity.User;
import com.zd.Service.FollowService;
import com.zd.Util.Msg;
import com.zd.VO.FollowVO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "follow")
public class FollowController {
	@Autowired
	FollowService followService;

	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String save(Follow follow, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user.getId() == follow.getFollow_user_id()) {
				try {
					Follow old = followService.hasFollowed(user.getId(), follow.getFollow_pid());
					if (old != null) {
						followService.delete(user.getId(), follow.getFollow_pid());
						result.setCodeMsg(Msg.success_code, Msg.CANCEL);
					} else {
						followService.insert(follow);
						result.setCodeMsg(Msg.success_code, Msg.success);
					}
				} catch (Exception e) {
					result.setCodeMsg(Msg.fail_code, Msg.fail);
					e.printStackTrace();
				}
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/user/{user_id}/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get(@PathVariable("user_id") int user_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, HttpServletRequest request) {
		JSONResult<FollowVO> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user.getId() == user_id) {
				result = followService.get(user_id, page, limit);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "delete/user/{user_id}/pid/{pid}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("user_id") int user_id, @PathVariable("pid") int pid,
			HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user.getId() == user_id) {
				Integer data = followService.delete(user_id, pid);
				if (data != 0) {
					result.setCodeMsg(Msg.success_code, Msg.success);
				} else {
					result.setCodeMsg(Msg.fail_code, Msg.fail);
				}
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/pid/{user_id}/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByPid(@PathVariable("user_id") int user_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, HttpServletRequest request) {
		JSONResult<FollowVO> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user.getId() == user_id) {
				result = followService.getByFollow(user_id, page, limit);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "isFollowed/{p_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String isFollowed(@PathVariable("p_id")int p_id,HttpServletRequest request){
		JSONResult<Object> result=new JSONResult<>(Msg.OK,Msg.err_code,Msg.NOT_LOGIN);
		User user=(User)request.getSession().getAttribute("user");
		if(user!=null){
			Follow old=followService.hasFollowed(user.getId(), p_id);
			if(old!=null){
				result.setCodeMsg(Msg.success_code, "已关注");
			}else{
				result.setCodeMsg(Msg.fail_code, "未关注");
			}
		}
		return JSONObject.fromObject(result).toString();
	}
	
}
