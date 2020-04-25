package com.zd.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zd.Util.MyUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "upload")
public class UploadController {

	public static final Logger logger = LogManager.getLogger(UploadController.class);

	@RequestMapping(value = "file", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	@ResponseBody
	public String upload(MultipartFile[] file, HttpServletRequest request) {
		List<String> filepath = MyUtil.saveFile(request, file, 5);
		logger.info(filepath.get(0).toString());
		JSONObject json = new JSONObject();
		json.put("location", filepath.get(0));
		return json.toString();
	}
}
