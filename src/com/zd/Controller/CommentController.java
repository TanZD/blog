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
import com.zd.Entity.Comment;
import com.zd.Entity.User;
import com.zd.Service.CommentService;
import com.zd.Util.Msg;
import com.zd.VO.CommentVO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "comment")
public class CommentController {
	@Autowired
	CommentService commentService;

	/**
	 * 获取所有评论
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAll", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getAll() {
		List<CommentVO> data = commentService.getAll();
		JSONResult<CommentVO> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, data);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 添加/更新评论
	 * 
	 * @param comment
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String save(Comment comment, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		JSONResult<Comment> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		if (user != null) {
			comment = commentService.insertOrUpdate(comment);
			if (comment != null) {
				result.setCodeMsg(Msg.success_code, Msg.success);
				result.setData(comment);
			} else {
				result.setCodeMsg(Msg.fail_code, Msg.fail);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取评论列表(分页)
	 * 
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "get/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("order") int order) {
		// System.out.println("page:" + page + " limit:" + limit + " order:" +
		// order);
		return JSONObject.fromObject(commentService.get(page, limit, order)).toString();
	}

	/**
	 * 拉取文章的评论
	 * 
	 * @param article_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "get/article/{id}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByArticleId(@PathVariable("id") int article_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<CommentVO> result = commentService.getByArticleId(article_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取用户的评论
	 * 
	 * @param user_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "get/user/{id}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByUserId(@PathVariable("id") int user_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<CommentVO> result = commentService.getByUserId(user_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取回复用户的评论
	 * 
	 * @param acceptor_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "get/acceptor/{id}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByAcceptorId(@PathVariable("id") int acceptor_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<CommentVO> result = commentService.getByAcceptorId(acceptor_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/date/{date}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByDate(@PathVariable("date") String date, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<CommentVO> result = commentService.getByDate(date, page, limit);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 删除评论
	 * 
	 * @param comment_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delete/{comment_id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("comment_id") int comment_id, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		if (user != null) {
			CommentVO c = commentService.getById(comment_id);
			if (c != null) {
				if (c.getComment_user_id() == user.getId() || user.getIsAdmin() == 1) {
					Integer data = commentService.delete(comment_id);
					HashMap<String, Integer> map = new HashMap<>();
					map.put("num", data);
					result.setCodeMsg(Msg.success_code, Msg.success);
					result.setData(data);
				} else {
					result.setCodeMsg(Msg.err_code, Msg.NO_PERMISSION);
				}
			} else {
				result.setCodeMsg(Msg.fail_code, Msg.fail);
			}
		}
		return JSONObject.fromObject(result).toString();

	}

	@RequestMapping(value = "totalCount", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getCount() {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		Integer count = commentService.getCount();
		HashMap<String, Integer> r = new HashMap<>();
		r.put("num", count);
		result.setData(r);
		return JSONObject.fromObject(result).toString();
	}

}
