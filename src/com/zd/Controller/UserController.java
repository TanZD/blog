package com.zd.Controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zd.DTO.JSONResult;
import com.zd.DTO.UserDTO;
import com.zd.Entity.User;
import com.zd.Service.ArticleService;
import com.zd.Service.CategoryService;
import com.zd.Service.CommentService;
import com.zd.Service.MailSenderService;
import com.zd.Service.UserService;
import com.zd.Util.Msg;
import com.zd.Util.MyUtil;
import com.zd.Util.TextFilter;
import com.zd.VO.UserSimple;
import com.zd.VO.UserVO;

import cn.hutool.core.lang.UUID;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	MailSenderService mailService;

	@Autowired
	ArticleService articleService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	CommentService commentService;

	public static final Logger logger = LogManager.getLogger(UserController.class);

	@RequestMapping(value = "/notindex", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String index(@RequestParam(defaultValue = "") String word) {
		JSONObject json = new JSONObject();
		System.out.println("word: " + word);
		json.put("msg", -1);
		json.put("word", word);
		json.put("中文", "中文");
		System.out.println(json.toString());
		return json.toString();
	}

	@RequestMapping(value = "/indexPage")
	public String indexPage() {
		return "hello";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String doRegister(User user) {
		JSONResult<User> data = new JSONResult<User>();
		if (user.getUsername() == null || user.getUser_mail() == null || user.getPassword() == null) {
			data.setCodeMsg(Msg.err_code, Msg.fail);
		} else if (TextFilter.hasSensitiveWord(user.getUsername())) {
			// 检测用户名是否包含敏感词
			data.setCodeMsg(Msg.sensitive_code, Msg.SENSITIVE);
		} else {
			// 检测邮箱是否已被注册
			User old = userService.getByMail(user.getUser_mail());
			// System.out.println(old);
			if (old != null) {
				data.setCodeMsg(Msg.sensitive_code, Msg.MAIL_EXIST);
			} else {
				// 检测用户名是否已被注册
				User old2 = userService.getByUsername(user.getUsername());
				// System.out.println(old2);
				if (old2 != null) {
					data.setCodeMsg(Msg.sensitive_code, Msg.USERNAME_EXIST);
				} else {
					// 注册用户
					User result = userService.insert(user);
					data = new JSONResult<User>(200, 1, Msg.REGISTER_SUCCESS);
				}
			}
		}
		return JSONObject.fromObject(data).toString();
	}

	@RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response) {
		JSONResult<User> data = new JSONResult<>(200, 0, "", null);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userService.getByUsernameOrMail(username);
		if (user != null) {
			String Md5password = MyUtil.md5code(password);
			if (Md5password.equals(user.getPassword())) {
				User user_message = new User();
				user_message.setId(user.getId());
				user_message.setUsername(user.getUsername());
				user_message.setUser_mail(user.getUser_mail());
				user_message.setIsAdmin(user.getIsAdmin());
				data.setData(user_message);
				data.setCodeMsg(1, "登录成功");
				request.getSession(true).setAttribute("user", user);
			} else {
				user = null;
				data.setCodeMsg(0, "密码错误");
			}
		} else {
			data.setCodeMsg(-1, "该账号不存在");
		}
		return JSONObject.fromObject(data).toString();
	}

	@RequestMapping(value = "getByUsername", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByUsername(String username) {
		JSONResult<User> data = new JSONResult<User>(200, 0, "");
		if (username != null) {
			User user = userService.getByUsername(username);
			if (user != null) {
				data.setMsg("用户名已存在");
			} else {
				data.setCode(1);
				data.setMsg("用户名不存在");
			}
		} else {
			data.setCode(-1);
			data.setMsg("缺少参数");
		}
		return JSONObject.fromObject(data).toString();
	}

	@RequestMapping(value = "getByMail", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByMail(String mail) {
		JSONResult<User> data = new JSONResult<>(200, 0, "", null);
		if (mail != null && mail != "") {
			User user = userService.getByMail(mail);
			if (user != null) {
				data.setMsg("该邮箱已被注册");
			} else {
				data.setCode(1);
				data.setMsg("该邮箱可用");
			}
		} else {
			data.setCode(-1);
			data.setMsg("缺少参数");
		}
		return JSONObject.fromObject(data).toString();

	}

	@RequestMapping(value = "logout", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String logout(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		json.put("status", 200);
		json.put("msg", "登出成功");
		json.put("code", 1);
		request.getSession().invalidate();
		return json.toString();
	}

	@RequestMapping(value = "islogin", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String islogin(HttpServletRequest request) {
		JSONResult<User> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		if (request.getSession().getAttribute("user") == null) {
			result.setCodeMsg(Msg.err_code, Msg.NOT_LOGIN);
		} else {
			User user = (User) request.getSession().getAttribute("user");
			User user_message = new User();
			user_message.setImage(user.getImage());
			user_message.setId(user.getId());
			user_message.setUsername(user.getUsername());
			user_message.setIsAdmin(user.getIsAdmin());
			user_message.setUser_mail(user.getUser_mail());
			result.setData(user_message);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/page/{page}/limit/{limit}/order/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("order") int order, HttpServletRequest request) {
		JSONResult<UserVO> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.fail);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			switch (order) {
			case 0:
				result = userService.getOrderByArticleNum(page, limit);
				break;
			case 1:
				result = userService.getOrderByCollectNum(page, limit);
				break;
			case 2:
				result = userService.getOrderByCommentNum(page, limit);
				break;
			case 3:
				result = userService.getOrderByHistoryNum(page, limit);
				break;
			default:
				break;
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "getList/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getList(@PathVariable("page") int page, @PathVariable("limit") int limit,
			HttpServletRequest request) {
		JSONResult<UserSimple> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			result = userService.get(page, limit);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "{user_id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getSimple(@PathVariable("user_id") int user_id, HttpServletRequest request) {
		JSONResult<UserDTO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		UserDTO data = userService.getByUser(user_id);
		if (data != null) {
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, data);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String update(User user, MultipartFile[] file, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user_login = (User) request.getSession().getAttribute("user");
		boolean isOk = true;
		// System.out.println(user_login.toString());
		if (user_login != null) {
			// 修改的用户信息为当前登录用户
			if (user_login.getId() == user.getId() || user_login.getIsAdmin() == 1) {
				try {
					User old_user = userService.getByUserId(user.getId());
					String filename = old_user.getImage();
					// 是否有更换头像
					if (file.length > 0) {
						// 保存文件返回保存路径(存于数据库)
						System.out.println(file);
						System.out.println(file.toString());
						List<String> file_path = MyUtil.saveFile(request, file, 4);
						filename = file_path.get(0);
						// 删除原来的头像
						if (old_user.getImage().indexOf("etc_image") == -1) {
							MyUtil.deleteFile(request, old_user.getImage());
						}
					}
					// 用户名敏感词检测
					if (!TextFilter.hasSensitiveWord(user.getUsername())) {
						User old = userService.getByUsername(user.getUsername());
						if (old != null) {
							if (!(old_user.getId() == old.getId())) {
								result.setCodeMsg(Msg.fail_code, Msg.USERNAME_EXIST);
								isOk = false;
							}
						}
					} else {
						result.setCodeMsg(Msg.fail_code, Msg.SENSITIVE);
						isOk = false;
					}
					// 个人简介敏感词检测
					if (user.getProfile() != null) {
						if (TextFilter.hasSensitiveWord(user.getProfile())) {
							result.setCodeMsg(Msg.fail_code, Msg.SENSITIVE);
							isOk = false;
						}
					}
					if (isOk) {
						user.setForget(old_user.getForget());
						user.setUser_mail(old_user.getUser_mail());
						user.setIsAdmin(old_user.getIsAdmin());
						user.setImage(filename);
						user.setPassword(old_user.getPassword());
						user.setId(old_user.getId());
						User updateUser = userService.update(user);
						result.setCodeMsg(Msg.success_code, Msg.success);
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "getCount", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getCount() {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		HashMap<String, Integer> h = new HashMap<>();
		h.put("count", userService.getCount());
		result.setData(h);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "updatePassword", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String updatePassword(String password, String old_password, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.PASSWORD_WRONG);
		User user_login = (User) request.getSession().getAttribute("user");
		String old_pass = user_login.getPassword();
		System.out.println(old_pass);
		System.out.println(old_password);
		if (user_login != null) {
			// 旧密码一致
			if (MyUtil.md5code(old_password).equals(old_pass)) {
				User user = new User();
				user.setId(user_login.getId());
				user.setPassword(password);
				Integer d = userService.updatePassword(user);
				if (d != 0) {
					System.out.println(user);
					request.getSession().setAttribute("user", user);
					result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
				} else {
					result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
				}
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "setPassword", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String setPassword(String password, int user_id, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		User user = (User) request.getSession().getAttribute("user");
		if (user.getIsAdmin() == 1) {
			User a_user = new User();
			a_user.setId(user_id);
			a_user.setPassword(password);
			Integer d = userService.updatePassword(a_user);
			if (d != 0) {
				result.setCodeMsg(Msg.success_code, Msg.success);
			} else {
				result.setCodeMsg(Msg.fail_code, Msg.fail);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "fotgetPass", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String forgetPass(String email, String username) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		User user = (User) userService.getByMail(email);
		if (user != null) {
			String uuid = UUID.randomUUID().toString();
			String token = MyUtil.md5code(uuid);
			Integer r = userService.setForget(token, user.getId());
			mailService.resetPassMail(email, user.getId(), token);
			result.setCodeMsg(Msg.success_code, "重置信息已发送到邮箱，请注意查收");
		} else {
			result.setCodeMsg(Msg.err_code, "该邮箱不存在");
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "getForget", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getForget(String pid, int id) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		HashMap<String, String> data = new HashMap<>();
		User user = (User) userService.getByUserId(id);
		if (user != null) {
			String p = userService.getForget(id);
			if (p.equals(pid)) {
				result.setCodeMsg(Msg.success_code, Msg.success);
			} else {
				result.setCodeMsg(Msg.err_code, Msg.fail);
			}
		} else {
			result.setCodeMsg(Msg.err_code, Msg.fail);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "resetPass", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String resetPass(String password, String user_mail, String pid) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		User user = (User) userService.getByMail(user_mail);
		if (user != null) {
			if (user.getForget().equals(pid)) {
				User newUser = new User();
				newUser.setId(user.getId());
				newUser.setPassword(password);
				userService.updatePassword(newUser);
			} else {
				result.setCodeMsg(Msg.err_code, Msg.fail);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "adminlogin", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String adminLogin(String username, String password, HttpServletRequest request) {
		JSONResult<User> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		User user = userService.getByUsernameOrMail(username);
		if (user != null) {
			if (user.getPassword().equals(MyUtil.md5code(password))) {
				if (user.getIsAdmin() == 0) {
					result.setCodeMsg(Msg.fail_code, Msg.NO_PERMISSION);
				} else {
					User user_message = new User();
					user_message.setId(user.getId());
					user_message.setUsername(user.getUsername());
					user_message.setUser_mail(user.getUser_mail());
					user_message.setIsAdmin(user.getIsAdmin());
					result.setData(user_message);
					result.setCodeMsg(Msg.success_code, Msg.success);
					request.getSession(true).setAttribute("user", user);
				}
			} else {
				result.setCodeMsg(Msg.err_code, Msg.PASSWORD_WRONG);
			}
		} else {
			result.setCodeMsg(Msg.err_code, "用户不存在");
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "orderByArticle/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String countUserNum(@PathVariable("limit") int limit) {
		JSONResult<HashMap> data = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		data.setModels(userService.countByArticleNum(limit));
		return JSONObject.fromObject(data).toString();
	}

	@RequestMapping(value = "orderByRegisterTime/{limit}/{order}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String countRegisterTime(@PathVariable("limit") int limit, @PathVariable("order") int order) {
		JSONResult<HashMap> data = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		data.setModels(userService.countByRegisterTime(limit, order));
		return JSONObject.fromObject(data).toString();
	}

	@RequestMapping(value = "search/word/{key_word}/type/{type}/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String search(@PathVariable("key_word") String key_word, @PathVariable("type") int type,
			@PathVariable("limit") int limit, @PathVariable("page") int page) {
		JSONResult<UserSimple> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		result = userService.search(key_word, type, page, limit);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "setAdmin", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String setAdmin(int user_id) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		Integer d = userService.setAdmin(user_id);
		if (d == 0) {
			result.setCodeMsg(Msg.fail_code, Msg.fail);
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "adminIndex", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String adminIndex() {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		Integer article_count = articleService.getTotalCount();
		Integer comment_count = commentService.getCount();
		Integer user_count = userService.getCount();
		Integer category_count = categoryService.getCount();
		HashMap<String, Integer> r = new HashMap<>();
		r.put("article_num", article_count);
		r.put("comment_num", comment_count);
		r.put("user_num", user_count);
		r.put("category_num", category_count);
		result.setData(r);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "delete/{user_id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable int user_id) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		Integer d = userService.delete(user_id);
		if (d == 0) {
			result.setCodeMsg(Msg.fail_code, Msg.fail);
		}
		return JSONObject.fromObject(result).toString();
	}

}
