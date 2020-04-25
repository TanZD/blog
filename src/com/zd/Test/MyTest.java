package com.zd.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

import com.zd.DAO.ArticleDAO;
import com.zd.DAO.TagDAO;
import com.zd.Service.ArticleService;
import com.zd.Service.RedisService;
import com.zd.Service.TagService;
import com.zd.Service.UserService;
import com.zd.Util.MailSender;
import com.zd.Util.MyUtil;

import cn.hutool.core.lang.UUID;

//@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MyTest {
	TagService tagService;
	TagDAO tagDAO;
	SessionFactory session;
	RedisService redisService;
	// @Autowired
	ArticleDAO articleDAO;
	ArticleService articleService;
	UserService userService;
	HashMap<Object, Object> sensitiveWordMap;

	// @Before
	// public void MyTest() {
	// ApplicationContext context = new
	// ClassPathXmlApplicationContext("applicationContext.xml");
	// session = (SessionFactory) context.getBean("sessionFactory");
	// tagService = (TagService) context.getBean("TagService");
	// tagDAO = (TagDAO) context.getBean("TagDAO");
	// articleDAO = (ArticleDAO) context.getBean("ArticleDAO");
	// redisService = (RedisService) context.getBean("RedisService");
	// articleService = (ArticleService) context.getBean("ArticleService");
	// uploadService = (QiniuUploadService) context.getBean("QiniuService");
	// userService = (UserService) context.getBean("UserService");
	// }

	// @Test
	// public void test() {
	// /**
	// * md5加密
	// */
	// try {
	// MessageDigest md = MessageDigest.getInstance("MD5");
	// md.update((String.valueOf("i love you")).getBytes());
	// byte[] digest = md.digest();
	// System.out.println(digest);
	// String code = new BigInteger(1, digest).toString(16);
	// for (int i = 0; i < 32 - code.length(); i++) {
	// code = "0" + code;
	// }
	// System.out.println(code);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// String text = "i love you";
	// String md5code = DigestUtils.md5DigestAsHex(text.getBytes());
	// System.out.println(md5code);
	//
	// }

	// @Test
	// public void test2(){
	// User result=new User();
	// result.setUsername("sdasd");
	// result.setPassword("123123");
	// result.setUser_mail("asdasd");
	// JSONResult<User> data = new JSONResult<User>(200, 1, "注册成功", result);
	// System.out.println(data);
	// System.out.println(JSONObject.fromObject(data));
	// System.out.println(JSONObject.fromObject(result));
	// }

	@Test
	public void test3() {
		// Tag tag = new Tag();
		// tag.setTag_name("高达");
		// Tag result = tagService.findByName(tag.getTag_name());
		// System.out.println(result.toString());
		// Tag s = tagService.insert(tag);
		// System.out.println(s);
		//
		// List<Tag> data2 = (List<Tag>)
		// session.openSession().createSQLQuery("SELECT * FROM tag").list();
		// List<Tag> data = (List<Tag>)
		// session.openSession().createSQLQuery("SELECT * FROM
		// tag").addEntity(Tag.class)
		// .list();
		// System.out.println(data.toString());
		// System.out.println(data2.toString());

		// redisService.add();
		// List<Object> values = redisService.get();
		// System.out.println(values.toString());
		// JSONResult<ArticleDTO<ArticleVO>> data = articleService.get(1, 100,
		// 0);
		// System.out.println(JSONObject.fromObject(data).toString());
		// try {
		// uploadService.upload();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// String s="<blockquote>\n<div class=\"padding-left: 40px\">Testing中文
		// 啊asdasdasdasd实打实的asda<strong>dasdasd<em>asda</em></strong></div>\n</blockquote>";
		// System.out.println(s);
		// System.out.println(MyUtil.cleanEnter(s));

		// System.out.println(userService.get(1, 110, 0).toString());

		// JSONResult<ArticleDTO<ArticleVO>> result = articleService.get(1, 100,
		// 0);
		// result.getModels().get(0).getArticle().setLikes_count(10);
		// System.out.println(result.toString());

		// Set<String> keywords = new HashSet<String>();
		//
		// keywords.add("中美");
		// keywords.add("中国");
		// keywords.add("垃圾");
		// keywords.add("北美病夫");
		// keywords.add("北极");
		// keywords.add("北极熊");
		//
		// initSensitiveWord(keywords);
		// System.out.println(sensitiveWordMap.size());
		// String text = "美中不足中国美国中日北极熊";
		// List<String> words = getSensitiveWord(text);
		// System.out.println(words);

		// String text = "我操你妈逼个臭嘿，叼你老母只嘿，死扑街捞仔，ong你老母落屎坑";
		// String text2 = "我这里没有敏感词";
		// System.out.println("原文本:");
		// System.out.println(text);
		// System.out.println("过滤后的文本:");
		// System.out.println(TextFilter.doFilter(text));
		// System.out.println(TextFilter.hasSensitiveWord(text2));
		
//		try {
//			readfile("D://apache-tomcat-9.0.11/webapps/static/pictures");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

		AntPathMatcher pathMatcher = new AntPathMatcher();
		boolean t=pathMatcher.match("/Blog/article/delete"+"/**", "/Blog/article/delete/");
		System.out.println(t);
	}

	public static void readfile(String filepath) throws FileNotFoundException, IOException {
		try {

			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("文件");
				System.out.println("path=" + file.getPath());
				System.out.println("absolutepath=" + file.getAbsolutePath());
				System.out.println("name=" + file.getName());

			} else if (file.isDirectory()) {
				System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						System.out.println("path=" + readfile.getPath());
						System.out.println("absolutepath=" + readfile.getAbsolutePath());
						System.out.println("name=" + readfile.getName());

					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
	}

	public void initSensitiveWord(Set<String> keywords) {
		sensitiveWordMap = new HashMap<>(keywords.size());
		String key = null;
		Map nowMap = null;
		Map<Object, Object> newWordMap = null;
		Iterator<String> iterator = keywords.iterator();
		while (iterator.hasNext()) {
			key = iterator.next();
			nowMap = sensitiveWordMap;
			System.out.println("key: " + key);
			System.out.println("nowMap: " + nowMap);
			for (int i = 0; i < key.length(); i++) {
				char keyChar = key.charAt(i);
				System.out.println("keyChar: " + keyChar);
				Object wordMap = nowMap.get(keyChar);
				System.out.println("wordMap: " + wordMap);
				if (wordMap != null) {
					nowMap = (Map) wordMap;
					System.out.println(nowMap);
				} else {
					newWordMap = new HashMap<Object, Object>();
					System.out.println("newWordMap: " + newWordMap);
					newWordMap.put("isEnd", 0);
					nowMap.put(keyChar, newWordMap);
					nowMap = newWordMap;
					System.out.println("nowMap: " + nowMap.toString());
				}
				if (i == key.length() - 1) {
					nowMap.put("isEnd", 1);
				}
			}
		}
		System.out.println(sensitiveWordMap);
	}

	public List<String> getSensitiveWord(String text) {
		List<String> words = new ArrayList<>();
		char[] text_ch = text.toCharArray();
		Map now = sensitiveWordMap;
		int sensitive_length = 0; // 敏感词长度
		int sensitive_count = 0;
		int start_count = 0; // 敏感词开始的位置
		boolean isLast = false;
		for (int i = 0; i < text_ch.length; i++) {
			char key = text_ch[i];
			now = (Map) sensitiveWordMap.get(key);
			// 匹配到首字为敏感字
			if (now != null) {
				start_count = i;
				// 循环查看文本的下一个字是否为子树
				for (; ++start_count < text_ch.length;) {
					now = (Map) now.get(text_ch[start_count]);
					if (now == null) {
						// 不为敏感字的子树 结束循环
						break;
					}
					// 文本下一个字为子树
					// 查看其是否最后一个
					if (now.get("isEnd").equals(1)) {
						// 记录下敏感词的长度
						sensitive_length = start_count - i;
						isLast = true;
					}
				}
				if (isLast) {
					for (int j = 0; j <= sensitive_length; j++) {
						text_ch[i + j] = '*';
					}
					i += sensitive_length;
					isLast = false;
				}
			}

			// char key = text.charAt(i);
			// now = (Map) now.get(key);
			// // 敏感词库存在该字
			// if (now != null) {
			// sensitive_count++;
			// // 只记录敏感词开始的位置为1时
			// if (sensitive_count == 1) {
			// start_count = i;
			// }
			// if (now.get("isEnd").equals(1)) {
			// now = sensitiveWordMap;
			// String word = text.substring(start_count, start_count +
			// sensitive_count);
			// System.out.println("检测到敏感词: " + word);
			// words.add(word);
			// i += sensitive_count - 1;
			// sensitive_count = 0;
			// }
			// } else {
			// now = sensitiveWordMap;
			// if (sensitive_count != 0) {
			// String word = text.substring(start_count, start_count +
			// sensitive_count);
			// System.out.println("检测到敏感词: " + word);
			// words.add(word);
			// i += sensitive_count - 1;
			// sensitive_count = 0;
			// }
			// }
		}
		String newText = String.valueOf(text_ch);
		System.out.println("newText: " + newText);
		return words;
	}

	public String doFilter(String text) {
		// 检测出敏感词
		List<String> words = getSensitiveWord(text);
		Iterator<String> iterator = words.iterator();
		String word = null;
		while (iterator.hasNext()) {
			word = iterator.next();

		}
		return null;
	}

}
