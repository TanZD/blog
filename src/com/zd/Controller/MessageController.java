package com.zd.Controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Message;
import com.zd.Entity.User;
import com.zd.Service.MessageService;
import com.zd.Util.Msg;
import com.zd.VO.MessageList;
import com.zd.VO.MessageVO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "message")
public class MessageController {
	@Autowired
	MessageService messageService;

	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String save(Message message, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user.getId() == message.getSender_id()) {
				if(user.getId()!=message.getReceiver_id()){
					Message a=messageService.insert(message);
					if (a != null) {
						result.setCodeMsg(Msg.success_code, Msg.success);
					}else{
						result.setCodeMsg(Msg.fail_code, Msg.fail);
					}
				} else {
					result.setCodeMsg(Msg.fail_code, Msg.fail);
				}
			} else {
				result.setCodeMsg(Msg.err_code, Msg.NO_PERMISSION);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "getList/{user_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getList(@PathVariable("user_id") int user_id, HttpServletRequest request) {
		JSONResult<MessageList> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user_id == user.getId()) {
				List<MessageList> data = messageService.getMessageList(user_id);
				result.setModels(data);
				result.setCodeMsg(Msg.success_code, Msg.success);
			} else {
				result.setCodeMsg(Msg.err_code, Msg.NO_PERMISSION);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "getMessage/user_id/{user_id}/p_id/{p_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getMessage(@PathVariable("user_id") int user_id, @PathVariable("p_id") int p_id,
			HttpServletRequest request) {
		JSONResult<MessageVO> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user_id == user.getId()) {
				List<MessageVO> data = messageService.getMessage(user_id, p_id);
				result.setModels(data);
				result.setCodeMsg(Msg.success_code, Msg.success);
			} else {
				result.setCodeMsg(Msg.err_code, Msg.NO_PERMISSION);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "isMessage/{user_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String isMessage(@PathVariable("user_id") int user_id, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user_id == user.getId()) {
				Integer data = messageService.isMessage(user_id);
				HashMap<String, Integer> h = new HashMap<>();
				h.put("num", data);
				result.setData(h);
				result.setCodeMsg(Msg.success_code, Msg.success);
			} else {
				result.setCodeMsg(Msg.err_code, Msg.NO_PERMISSION);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "isRead/user_id/{user_id}/p_id/{p_id}", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String setIsRead(@PathVariable("user_id") int user_id, @PathVariable("p_id") int p_id,
			HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user_id == user.getId()) {
				Integer data = messageService.setIsRead(user_id, p_id);
				result.setCodeMsg(Msg.success_code, Msg.success);
			} else {
				result.setCodeMsg(Msg.err_code, Msg.NO_PERMISSION);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

}
