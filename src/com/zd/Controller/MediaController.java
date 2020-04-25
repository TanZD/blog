package com.zd.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.zd.DTO.FilePath;
import com.zd.DTO.JSONResult;
import com.zd.Entity.Media;
import com.zd.Entity.User;
import com.zd.Service.MediaService;
import com.zd.Util.Msg;
import com.zd.Util.MyUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "media")
public class MediaController {
	private static final Logger logger = LogManager.getLogger(MediaController.class);

	@Autowired
	MediaService mediaService;

	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String save(MultipartFile[] file, int type, String name, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		name = StringUtils.deleteWhitespace(name);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			// Media media = mediaService.getByName(name, 2);
			// if (media == null) {
			// } else {
			// }
			// 保存的是音频的话则需要文件名
			if (type == 2) {
				if (StringUtils.isEmpty(name)) {
					result.setCodeMsg(Msg.fail_code, Msg.fail);
					return JSONObject.fromObject(result).toString();
				}
			}
			List<String> file_path = MyUtil.saveFile(request, file, type);
			if (file_path != null) {
				for (String path : file_path) {
					// 保存到数据库
					Media newMedia = new Media();
					newMedia.setUser_id(user.getId());
					newMedia.setFile_name(name);
					newMedia.setFile_type(type);
					newMedia.setCreate_time(MyUtil.NowTime());
					newMedia.setFile_path(path);
					mediaService.insert(newMedia);
				}
				result.setCodeMsg(Msg.success_code, Msg.success);
			} else {
				result.setCodeMsg(Msg.err_code, Msg.fail);
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/word/{name}/type/{type}/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByName(@PathVariable("name") String name, @PathVariable("page") int page,
			@PathVariable("limit") int limit, @PathVariable("type") int type, HttpServletRequest request) {
		JSONResult<Media> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		System.out.println(name);
		System.out.println(type);
		System.out.println(page);
		System.out.println(limit);
		result = mediaService.getByName(name, type, page, limit);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/article/{article}/type/{type}/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByArticle(@PathVariable("article") int article_id, @PathVariable("type") int type,
			@PathVariable("page") int page, @PathVariable("limit") int limit) {
		JSONResult<Media> result = mediaService.getByArticle(article_id, type, page, limit);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String get(@PathVariable("page") int page, @PathVariable("limit") int limit) {
		JSONResult<Media> result = mediaService.get(page, limit);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "get/user/{user_id}/type/{type}/page/{page}/limit/{limit}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getByUser(@PathVariable("user_id") int user_id, @PathVariable("type") int type,
			@PathVariable("page") int page, @PathVariable("limit") int limit) {
		JSONResult<Media> result = mediaService.getByUser(user_id, type, page, limit);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("id") int id, HttpServletRequest request) {
		JSONResult<Object> result = new JSONResult<>(Msg.OK, Msg.err_code, Msg.NOT_LOGIN);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			Media media = mediaService.getById(id);
			if (media != null) {
				boolean r = MyUtil.deleteFile(request, media.getFile_name());
				if (r) {
					result.setCodeMsg(Msg.success_code, Msg.success);
				} else {
					result.setCodeMsg(Msg.fail_code, Msg.fail);
				}
			}
		}
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 获取本地文件列表
	 * 
	 * @param type_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "file/{id}", method = RequestMethod.GET, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String filePath(@PathVariable("id") int type_id, HttpServletRequest request) {
		JSONResult<FilePath> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		List<FilePath> data = MyUtil.readfile(request, type_id);
		result.setModels(data);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 删除本地文件
	 */
	@RequestMapping(value = "delfile", method = RequestMethod.DELETE, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String deleteFile(String file, HttpServletRequest request) {
		logger.info(file);
		JSONResult<FilePath> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		boolean is = MyUtil.deleteFile(request, file);
		if (is) {
			result.setCodeMsg(Msg.success_code, Msg.success);
		}
		return JSONObject.fromObject(result).toString();
	}

}
