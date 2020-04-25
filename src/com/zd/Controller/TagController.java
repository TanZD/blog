package com.zd.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zd.DTO.JSONResult;
import com.zd.Entity.Tag;
import com.zd.Entity.User;
import com.zd.Service.TagService;
import com.zd.VO.TagVO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/tag")
public class TagController {
	@Autowired
	TagService tagService;

	/**
	 * 添加/修改标签
	 * 
	 * @param tag
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String save(Tag tag, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		JSONResult<Tag> data = new JSONResult<>(200, 0, "");
		if (user != null) {
			System.out.println(tag.getTag_name());
			tag.setCreator_id(user.getId());
			// 删除标签中的空格
			tag.setTag_name(StringUtils.deleteWhitespace(tag.getTag_name()));
			System.out.println(tag.getTag_name());
			Tag result = tagService.insertOrUpdate(tag);
			if (result == null) {
				// result = tagService.findByName(tag.getTag_name());
				data.setCodeMsg(0, "保存失败，已有重名标签");
			} else {
				data.setCodeMsg(1, "保存成功");
			}
			data.setData(result);
		} else {
			data.setCodeMsg(-1, "用户未登录");
		}
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 删除标签
	 * 
	 * @param tag
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delete/{tag_id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("tag_id") Integer tag_id, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		JSONResult<Object> data = new JSONResult<>(200, 0, "删除失败");
		if (user != null) {
			int status = tagService.delete(tag_id);
			if (status != 0) {
				data.setCodeMsg(1, "删除成功");
			}
		} else {
			data.setCodeMsg(-1, "用户未登录");
		}
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 返回所有标签
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAll", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getAll() {
		List<Tag> tag = tagService.getAll();
		JSONResult<Tag> data = new JSONResult<Tag>(200, 1, "成功", tag);
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 返回标签(带分页)
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
		return JSONObject.fromObject(tagService.get(limit, page, order)).toString();
	}

	/**
	 * 返回使用次数多的标签
	 * 
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getHotTags/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getHotTags(@PathVariable("limit") int limit) {
		JSONResult<TagVO> data = tagService.findByUsedTimes(limit);
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 查询文章的标签
	 * 
	 * @param article_id
	 * @return
	 */
	@RequestMapping(value = "getArticleTag/{article_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getArticleTag(@PathVariable("article_id") int article_id) {
		List<Tag> data = tagService.findByArticleId(article_id);
		JSONResult<Tag> result = new JSONResult<Tag>(200, 1, "成功", data);
		if (data.size() == 0) {
			result.setCode(0);
		}
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 返回用户使用过的标签
	 * 
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "getFromUser/{user_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getUsed(@PathVariable("user_id") int user_id) {
		List<TagVO> data = tagService.findByUsedWithUserId(user_id);
		JSONResult<TagVO> result = new JSONResult<>(200, 1, "成功", data);
		if (data.size() == 0) {
			result.setCode(0);
		}
		return JSONObject.fromObject(result).toString();
	}

}
