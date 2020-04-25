package com.zd.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zd.DTO.JSONResult;
import com.zd.Entity.SensitiveWord;
import com.zd.Entity.User;
import com.zd.Service.SensitiveWordService;
import com.zd.Util.Msg;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "sensitive")
public class SensitiveWordController {
	@Autowired
	SensitiveWordService sensitiveWordService;

	@RequestMapping(value = "getAll", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getAll(HttpServletRequest request) {
		JSONResult<SensitiveWord> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			List<SensitiveWord> data = sensitiveWordService.get();
			result.setModels(data);
			result.setCodeMsg(Msg.success_code, Msg.success);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("id") int id, HttpServletRequest request) {
		JSONResult<SensitiveWord> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			SensitiveWord word = new SensitiveWord();
			word.setId(id);
			if (sensitiveWordService.delete(word) != 0) {
				result.setCodeMsg(Msg.success_code, Msg.success);
			} else {
				result.setCodeMsg(Msg.fail_code, Msg.fail);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get(@PathVariable("page") int page, @PathVariable("limit") int limit, HttpServletRequest request) {
		JSONResult<SensitiveWord> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			result = sensitiveWordService.get(page, limit);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "search/{word}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String search(@PathVariable("word") String word, HttpServletRequest request) {
		JSONResult<SensitiveWord> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			SensitiveWord key_word = sensitiveWordService.getByWord(word);
			result.setCodeMsg(Msg.success_code, Msg.success);
			result.setData(key_word);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String save(SensitiveWord word, HttpServletRequest request) {
//		System.out.println(word.getWord().toString());
		JSONResult<SensitiveWord> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			SensitiveWord key_word = sensitiveWordService.insert(word);
			if (key_word != null) {
				result.setCodeMsg(Msg.success_code, Msg.success);
			} else {
				result.setCodeMsg(Msg.fail_code, "已存在");
			}
		}
		return JSONObject.fromObject(result).toString();
	}
}
