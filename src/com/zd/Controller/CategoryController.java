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
import com.zd.Entity.Category;
import com.zd.Entity.User;
import com.zd.Service.CategoryService;
import com.zd.Util.Msg;
import com.zd.VO.CategoryVO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	/**
	 * 保存/修改分类
	 * 
	 * @param category
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String save(Category category, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		JSONResult<Category> data = new JSONResult<>(200, 0, "保存失败", null);
		if (user != null) {
			category.setCreator_id(user.getId());
			Category result = categoryService.saveOrUpdate(category);
			if (result != null) {
				data.setCodeMsg(1, "保存成功");
				data.setData(result);
			}
		} else {
			data.setCodeMsg(-1, "用户未登录");
		}
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 获取所有分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAll", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getAll() {
		List<Category> result = categoryService.getAll();
		JSONResult<Category> data = new JSONResult<>(200, 1, "成功", result);
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 获取分类(带分页)
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
		return JSONObject.fromObject(categoryService.get(page, limit, order)).toString();
	}

	/**
	 * 获取文章的分类
	 * 
	 * @param article_id
	 * @return
	 */
	@RequestMapping(value = "getArticleCategory/{article_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getArticleCategory(@PathVariable("article_id") int article_id) {
		List<Category> result = categoryService.findByArticleId(article_id);
		JSONResult<Category> data = new JSONResult<>(200, 1, "成功", result);
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 获取热门分类
	 * 
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "getHotCategory/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getHotCategory(@PathVariable("limit") int limit) {
		return JSONObject.fromObject(categoryService.findByUsedTimes(limit)).toString();
	}

	/**
	 * 获取用户使用过的分类
	 * 
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "getFromUser/{user_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getUsed(@PathVariable("user_id") int user_id) {
		List<CategoryVO> result = categoryService.findByUsedWithUserId(user_id);
		JSONResult<CategoryVO> data = new JSONResult<>(200, 1, "成功", result);
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 获取子分类
	 * 
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "getChildCategory/{pid}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getChildCategory(@PathVariable("pid") int pid) {
		JSONResult<Category> data = new JSONResult<>(200, 1, "成功", null);
		List<Category> result = categoryService.getByPid(pid);
		if (result.size() != 0) {
			data.setModels(result);
		} else {
			data.setCode(0);
		}
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 删除分类
	 * 
	 * @param category_id
	 * @return
	 */
	@RequestMapping(value = "delete/{category_id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("category_id") int category_id) {
		Integer result = categoryService.delete(category_id);
		String msg = "成功";
		if (result != 1)
			msg = "失败";
		JSONResult<Object> data = new JSONResult<>(200, 1, msg);
		return JSONObject.fromObject(data).toString();
	}

	@RequestMapping(value = "totalCount", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getCount() {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		Integer count = categoryService.getCount();
		HashMap<String, Integer> r = new HashMap<>();
		r.put("num", count);
		result.setData(r);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "getByWeight", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByWeight() {
		JSONResult<Category> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		result.setModels(categoryService.getByWeight());
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "updateWeight", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String updateWeight(int id, int weight) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
//		System.out.println(id);
//		System.out.println(weight);
		Integer data = categoryService.updateWeight(id, weight);
		if (data == null) {
			result.setCodeMsg(Msg.fail_code, Msg.fail);
		}
		return JSONObject.fromObject(result).toString();
	}

}
