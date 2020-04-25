package com.zd.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zd.DTO.ArticleDTO;
import com.zd.VO.DateCount;
import com.zd.DTO.JSONResult;
import com.zd.Entity.Article;
import com.zd.Entity.Category;
import com.zd.Entity.History;
import com.zd.Entity.Tag;
import com.zd.Entity.User;
import com.zd.Service.ArticleService;
import com.zd.Service.CategoryService;
import com.zd.Service.HistoryService;
import com.zd.Service.RedisService;
import com.zd.Service.TagService;
import com.zd.Util.Msg;
import com.zd.Util.MyUtil;
import com.zd.VO.ADetailVO;
import com.zd.VO.ArticleSimple;
import com.zd.VO.ArticleVO;

import cn.hutool.http.HtmlUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "article")
public class ArticleController {
	@Autowired
	ArticleService articleService;

	@Autowired
	TagService tagService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	HistoryService historyService;

	@Autowired
	RedisService redisService;

	@RequestMapping(value = "get/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.get(page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String save(Article article, String[] category, String[] tag, HttpServletRequest request) {
		System.out.println(request.getServletPath());
		User user = (User) request.getSession().getAttribute("user");
		System.out.println(request.getSession().getId().toString());
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		System.out.println(user);
		// 检测是否登录
		if (user != null) {
			// 设置文章作者id
			article.setUser_id(user.getId());
			// 去除内容中的回车键\n \r
			article.setContent(MyUtil.cleanEnter(article.getContent()));
			// 截取文章摘要
			Integer summaryLength = 100;
			String text = HtmlUtil.cleanHtmlTag(article.getContent());
			if (text.length() >= summaryLength) {
				article.setSummary(text.substring(0, summaryLength));
			} else {
				article.setSummary(text);
			}
			// 设置发布时间
			article.setCreate_time(MyUtil.NowTime());
			System.out.println(article.getContent());
			// 检测分类标签是否存在(不存在则保存)
			List<Category> cateList = new ArrayList<>();
			List<Tag> tagList = new ArrayList<>();
			if (category.length > 0) {
				cateList = categoryService.getOrInsert(category, user.getId());
			}
			if (tag.length > 0) {
				tagList = tagService.getOrInsert(tag, user.getId());
			}
			// 数据整合
			ArticleDTO<Article> newArticle = new ArticleDTO<Article>(article, cateList, tagList, null);
			// Service保存文章
			articleService.saveOrUpdate(newArticle);
			if (article.getId() != 0) {
				result.setCode(Msg.success_code);
				result.setMsg(Msg.success);
			} else {
				result.setCode(Msg.fail_code);
				result.setMsg(Msg.fail);
			}
		}
		// System.out.println(article.toString());
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "savePost", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String savePost(Article article, int post_type, String name, String[] tag, MultipartFile[] file,
			HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		int pid = 0;
		if (user != null) {
			// 去除内容中的回车键\n \r
			// article.setContent(MyUtil.cleanEnter(article.getContent()));
			// 设置发布时间
			article.setCreate_time(MyUtil.NowTime());
			article.setUser_id(user.getId());
			// 检测标签是否存在(不存在则保存)
			List<Tag> tagList = new ArrayList<>();
			if (tag.length > 0) {
				tagList = tagService.getOrInsert(tag, user.getId());
			}
			// 保存动态
			articleService.savePost(request, article, tagList, file, name, post_type, pid);
			if (article.getId() != 0) {
				result.setCodeMsg(Msg.success_code, Msg.success);
			} else {
				result.setCodeMsg(Msg.fail_code, Msg.fail);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 查看用户的文章或动态列表
	 * 
	 * @param user_id
	 * @param page
	 * @param limit
	 * @param order
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "get/user/{user}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByUser(@PathVariable("user") int user_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order, HttpServletRequest request) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		User user = (User) request.getSession().getAttribute("user");
		// 检测是否查看的用户是否本人
		// ..
		result = articleService.getByUserId(user_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取某分类的文章或动态列表
	 * 
	 * @param category_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "get/cate/{cate_id}/user/{user_id}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByCategory(@PathVariable("cate_id") int category_id, @PathVariable("user_id") int user_id,
			@PathVariable("page") int page, @PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>();
		if (user_id == 0) {
			result = articleService.getByCategoryId(category_id, page, limit, order);
		} else {
			result = articleService.getByUserIdAndCategory(user_id, category_id, page, limit, order);
		}
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取某标签的文章或动态列表
	 * 
	 * @param tag_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "get/tag/{tag_id}/user/{user_id}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByTag(@PathVariable("tag_id") int tag_id, @PathVariable("user_id") int user_id,
			@PathVariable("page") int page, @PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>();
		if (user_id == 0) {
			result = articleService.getByTagId(tag_id, page, limit, order);
		} else {
			result = articleService.getByUserIdAndTag(user_id, tag_id, page, limit, order);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get(@PathVariable("id") int id, HttpServletRequest request) {
		ArticleDTO<ADetailVO> data = articleService.getDetailById(id);
		JSONResult<ArticleDTO<ADetailVO>> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.fail);
		User user = (User) request.getSession().getAttribute("user");
		// 数据不为空
		if (data != null) {
			result = new JSONResult<ArticleDTO<ADetailVO>>(Msg.OK, Msg.success_code, Msg.success, data);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/follow/{user_id}/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByFollow(@PathVariable("user_id") int user_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, HttpServletRequest request) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			result = articleService.getByFollow(user_id, page, limit, 0);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "search/words/{words}/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String search(@PathVariable("words") String words, @PathVariable("page") int page,
			@PathVariable("limit") int limit) {
		JSONResult<ArticleSimple> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		result = articleService.search(words, page, limit);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "updateReadable", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String updateReadable(int status, int article_id, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			Article article = articleService.getById(article_id);
			if (article != null) {
				if (article.getUser_id() == user.getId()) {
					Integer r = articleService.updateReadable(article_id, status);
					if (r != 0) {
						result.setMsg(Msg.success);
					} else {
						result.setMsg(Msg.fail);
					}
				} else {
					result.setMsg(Msg.NO_PERMISSION);
				}
			} else {
				result.setMsg(Msg.NOT_ARTICLE);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "listByYear", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String ListByYear() {
		JSONResult<DateCount> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		List<DateCount> data = articleService.ListByYear();
		if (data.size() != 0) {
			result.setCodeMsg(Msg.success_code, Msg.success);
			result.setModels(data);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "listByYearAndMonth", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String ListByYearAndMonth() {
		JSONResult<DateCount> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		List<DateCount> data = articleService.ListByYearAndMonth();
		if (data.size() != 0) {
			result.setCodeMsg(Msg.success_code, Msg.success);
			result.setModels(data);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "listByYear/user/{id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String ListByYearFromUser(@PathVariable("id") int user_id) {
		JSONResult<DateCount> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		List<DateCount> data = articleService.ListByYearFromUserId(user_id);
		if (data.size() != 0) {
			result.setCodeMsg(Msg.success_code, Msg.success);
			result.setModels(data);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "listByYearAndMonth/user/{id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String ListByYearAndMonthFromUser(@PathVariable("id") int user_id) {
		JSONResult<DateCount> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		List<DateCount> data = articleService.ListByYearAndMonthFromUserId(user_id);
		if (data.size() != 0) {
			result.setCodeMsg(Msg.success_code, Msg.success);
			result.setModels(data);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "incrViews/{article_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String incrViews(@PathVariable("article_id") int article_id, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			History h = new History();
			h.setHistory_article_id(article_id);
			h.setHistory_user_id(user.getId());
			historyService.save(h);
		}
		redisService.incrViewCount(article_id);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "saveViews", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String saveViewFromRedis(HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			articleService.tranViewsFromRedis();
		}
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取文章列表
	 * 
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "a/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getArticle(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.getArticle(page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取用户的文章列表
	 * 
	 * @param user_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "a/user/{user}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getArticleByUserId(@PathVariable("user") int user_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.ArticleByUserId(user_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取带某标签的文章列表
	 * 
	 * @param tag_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "a/tag/{tag}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getArticleByTag(@PathVariable("tag") int tag_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.ArticleByTag(tag_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取带某分类的文章列表
	 * 
	 * @param cate_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "a/cate/{cate}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getArticleByCate(@PathVariable("cate") int cate_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.ArticleByCategory(cate_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取关注用户的对象的文章列表
	 * 
	 * @param follow_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "a/follow/{user}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getArticleByFollow(@PathVariable("user") int follow_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.ArticleByFollow(follow_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取动态列表
	 * 
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "p/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getPost(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.getPost(page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取用户的动态列表
	 * 
	 * @param user_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "p/user/{user}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getPostByUserId(@PathVariable("user") int user_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.postByUserId(user_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取带某标签的动态列表
	 * 
	 * @param tag_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "p/tag/{tag}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getPostByTag(@PathVariable("tag") int tag_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.postByTag(tag_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取用户关注的对象的动态列表
	 * 
	 * @param follow_id
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "p/follow/{user}/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getPostByFollow(@PathVariable("user") int follow_id, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.postByFollow(follow_id, page, limit, order);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 删除文章
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("id") int id, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		User user = (User) request.getSession().getAttribute("user");
		Article a = articleService.getById(id);
		if (a != null) {
			if (user.getIsAdmin() != 1) {
				if (user.getId() == a.getId()) {
					Integer r = articleService.delete(id);
					if (r != 0) {
						result.setCodeMsg(Msg.success_code, Msg.success);
					}
				}
			} else {
				articleService.delete(id);
				result.setCodeMsg(Msg.success_code, Msg.success);
			}
		} else {
			result.setMsg("文章不存在");
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "totalCount", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getCount() {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		Integer count = articleService.getTotalCount();
		HashMap<String, Integer> r = new HashMap<>();
		r.put("num", count);
		result.setData(r);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "a/date/{date}/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String ArticleByTime(@PathVariable String date, @PathVariable int page, @PathVariable int limit) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.ArticleByTime(date, page, limit);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "a/user/{user}/date/{date}/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String ArticleByTimeAndUserId(@PathVariable String date, @PathVariable int user, @PathVariable int page,
			@PathVariable int limit) {
		JSONResult<ArticleDTO<ArticleVO>> result = articleService.ArticleByTimeAndUserId(date, user, page, limit);
		return JSONObject.fromObject(result).toString();
	}

}
