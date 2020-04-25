package com.zd.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.zd.DTO.FilePath;

import cn.hutool.core.lang.UUID;
import net.sf.json.JSONObject;

public class MyUtil {
	public static final Logger logger = LogManager.getLogger(MyUtil.class);

	/*
	 * 读取JSON
	 */
	public static JSONObject receiveJSON(HttpServletRequest request) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
		return json;
	}

	/**
	 * md5加密
	 */
	public static String md5code(String text) {
		String md5code = "";
		// java自带
		// try {
		// MessageDigest md=MessageDigest.getInstance("MD5");
		// md.update(text.getBytes());
		// byte[] digest=md.digest();
		// String code=new BigInteger(1,digest).toString(16);
		// for(int i=0;i<32-code.length();i++){
		// code="0"+code;
		// }
		// md5code=code;
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// spring自带
		md5code = DigestUtils.md5DigestAsHex(text.getBytes());
		return md5code;
	}

	/**
	 * 获取当天日期
	 */
	public static String ToadyDate() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(today);
	}

	/**
	 * 获取当前时间
	 */
	public static String NowTime() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(now);
	}

	public static String cleanEnter(String text) {
		Pattern p = Pattern.compile("\r|\n");
		Matcher m = p.matcher(text);
		text = m.replaceAll("");
		return text;
	}

	public static List<String> saveFile(HttpServletRequest request, MultipartFile[] file, int file_type) {
		List<String> pathList = new ArrayList<>();
		try {
			String path = "";
			switch (file_type) {
			case 1:
				path = "/static/pictures/";
				break;
			case 2:
				path = "/static/music/";
				break;
			case 3:
				path = "/static/video/";
				break;
			case 4:
				path = "/static/images/";
				break;
			case 5:
				path = "/static/articles/";
				break;
			default:
				path = "/static/unknow/";
				break;
			}
			for (MultipartFile m : file) {
				int end = (request.getSession().getServletContext().getRealPath(""))
						.indexOf(request.getContextPath().substring(1));
				logger.info("项目位置: " + request.getSession().getServletContext().getRealPath(""));
				logger.info("end: " + end);
				logger.info("substring(1): " + request.getContextPath().substring(1));
				String pathRoot = (request.getSession().getServletContext().getRealPath("")).substring(0, end);
				String contentType = m.getOriginalFilename().substring(m.getOriginalFilename().lastIndexOf("."));
				logger.info("相对路径: " + pathRoot);
				logger.info("文件原名: " + m.getOriginalFilename());
				logger.info("文件类型: " + contentType);
				String suffix = contentType;
				String uuid = UUID.randomUUID().toString().replace("-", "");
				String filename = uuid + suffix;
				logger.info("文件名保存为: " + filename);
				// 文件路径
				String filepath = pathRoot + path + filename;
				logger.info("文件保存路径: " + filepath);
				File newFile = new File(filepath);
				if (newFile.getParentFile() != null || newFile.getParentFile().isDirectory()) {
					newFile.getParentFile().mkdirs();
				}
				m.transferTo(newFile);
				pathList.add(path + filename);
			}
			return pathList;
			// 保存到数据库
			// Media newMedia = new Media();
			// newMedia.setUser_id(user.getId());
			// newMedia.setFile_name(name);
			// newMedia.setFile_type(2);
			// newMedia.setCreate_time(MyUtil.NowTime());
			// newMedia.setFile_path(path + filename);
			// mediaService.insert(newMedia);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean deleteFile(HttpServletRequest request, String filename) {
		boolean is = false;
		if (StringUtils.isNotEmpty(filename)) {
			int end = (request.getSession().getServletContext().getRealPath(""))
					.indexOf(request.getContextPath().substring(1));
			System.out.println("realPath: " + request.getSession().getServletContext().getRealPath(""));
			System.out.println("end: " + end);
			System.out.println("substring(1): " + request.getContextPath().substring(1));
			String pathRoot = (request.getSession().getServletContext().getRealPath("")).substring(0, end);
			System.out.println("WEB PATH: " + pathRoot);
			File file = new File(pathRoot + filename);
			if (file.exists()) {
				is = file.delete();
			}
		}
		return is;
	}

	public static List<FilePath> readfile(HttpServletRequest request, int fileType) {
		int end = (request.getSession().getServletContext().getRealPath(""))
				.indexOf(request.getContextPath().substring(1));
		logger.info("项目位置: " + request.getSession().getServletContext().getRealPath(""));
		logger.info("end: " + end);
		logger.info("substring(1): " + request.getContextPath().substring(1));
		String pathRoot = (request.getSession().getServletContext().getRealPath("")).substring(0, end);
		List<FilePath> list = new ArrayList<>();
		String filepath = null;
		switch (fileType) {
		case 1:
			filepath = "/static/pictures/";
			break;
		case 2:
			filepath = "/static/music/";
			break;
		case 3:
			filepath = "/static/video/";
			break;
		case 4:
			filepath = "/static/images/";
			break;
		case 5:
			filepath = "/static/articles/";
			break;
		default:
			filepath = "/static/unknow/";
			break;
		}
		File file = new File(pathRoot + filepath);
		if (!file.isDirectory()) {
			FilePath p = new FilePath();
			p.setFileName(file.getName());
			p.setFileType(fileType);
			p.setRoot(file.getAbsolutePath());
			list.add(p);
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "\\" + filelist[i]);
				FilePath p = new FilePath();
				p.setFileName(filepath + readfile.getName());
				p.setFileType(fileType);
				p.setRoot(file.getAbsolutePath() + "\\" + readfile.getName());
				list.add(p);
			}
		}
		return list;
	}

}
