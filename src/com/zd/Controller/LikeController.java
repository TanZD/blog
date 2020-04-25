package com.zd.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Likes;
import com.zd.Entity.User;
import com.zd.Service.LikesService;
import com.zd.Service.RedisService;
import com.zd.Util.Msg;
import com.zd.VO.LikesVO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/like")
public class LikeController {

	@Autowired
	RedisService redisService;

	@Autowired
	LikesService likesSevice;

	@RequestMapping(value = "add/user/{user_id}/article/{article_id}", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String add(@PathVariable("user_id") int user_id, @PathVariable("article_id") int article_id) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		Integer old=likesSevice.getLike(user_id, article_id);
		if (old == null) {
			redisService.saveLikeToRedis(user_id, article_id);
			result.setCodeMsg(Msg.success_code, Msg.success);
		} else {
			if(old==1){
				redisService.saveUnLikeToRedis(user_id, article_id);
				result.setCodeMsg(Msg.fail_code, Msg.CANCEL);
			}else{
				redisService.saveLikeToRedis(user_id, article_id);
				result.setCodeMsg(Msg.success_code, Msg.success);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "saveLikes", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String saveToMySQL() {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		likesSevice.transLikesFromRedis();
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "isLikeList", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String isLikeList(String[] article_id, HttpServletRequest request) {
		JSONResult<Likes> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		System.out.println(article_id.toString());
		if (user != null) {
			List<Likes> data = likesSevice.isLikeList(article_id, user.getId());
			result.setModels(data);
			result.setCodeMsg(Msg.success_code, Msg.success);
		}
		return JSONObject.fromObject(result).toString();
	}

}
