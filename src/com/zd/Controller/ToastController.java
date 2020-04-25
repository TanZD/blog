package com.zd.Controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zd.DTO.JSONResult;
import com.zd.Entity.User;
import com.zd.Service.ToastService;
import com.zd.Util.Msg;
import com.zd.VO.ToastVO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "toast")
public class ToastController {
	@Autowired
	ToastService toastService;

	/**
	 * 获取用户的消息
	 * 
	 * @param user_id
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "user/{user_id}/page/{page}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getToastByUser(@PathVariable("user_id") int user_id, @PathVariable("page") int page) {
		JSONResult<ToastVO> data = toastService.getByUserId(user_id, page, 30);
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 设置已读
	 * 
	 * @param toast_id
	 * @return
	 */
	@RequestMapping(value = "isRead", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String setIsRead(int user_id, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null){
			if (user.getId() == user_id) {
				toastService.setIsRead(user_id);
			} else {
				result.setCodeMsg(Msg.err_code, Msg.NO_PERMISSION);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 查看是否有消息
	 * 
	 * @param user_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "isToast/{user_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String isToastByUser(@PathVariable("user_id") int user_id, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			HashMap<String, Integer> data = new HashMap<>();
			data.put("num", toastService.getCountByUserId(user_id));
			result.setData(data);
			result.setCodeMsg(Msg.success_code, Msg.success);
		}
		return JSONObject.fromObject(result).toString();
	}

}
