package com.zd.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zd.Entity.SensitiveWord;
import com.zd.Service.SensitiveWordService;

@Component
public class TextFilter {
	static SensitiveWordService sensitiveService;
	private static boolean isLoad = false;

	@Autowired
	SensitiveWordService service;

	@PostConstruct
	private void beforeInit() {
		sensitiveService = this.service;
	}

	// 敏感词
	public static HashMap<Object, Object> sensitiveWordMap = new HashMap<>();

	// 替换敏感
	public static char BI = '*';

	// 初始化
	public static void init() {
		long time1 = 0;
		long time2 = 0;
		if (!isLoad) {
			List<String> keyWord = new ArrayList<String>();
			// keyWord.add("随便");
			// keyWord.add("你麻痹");
			// keyWord.add("你他妈比");
			// keyWord.add("叼你老母");
			// keyWord.add("扑街");
			// keyWord.add("草泥马");
			// keyWord.add("草你妈");
			// keyWord.add("叼你老豆");
			// keyWord.add("屎忽鬼");
			// keyWord.add("奔周");
			// keyWord.add("你妈逼");
			// keyWord.add("叼你只西");
			System.out.println("敏感词树未生成，现在从数据库获取敏感词并生成子树");
			List<SensitiveWord> words = sensitiveService.get();
			for (SensitiveWord s : words) {
				keyWord.add(s.getWord());
			}
			words.clear();
			time1 = System.currentTimeMillis();
			addSensitiveWord(keyWord);
			time2 = System.currentTimeMillis();
			isLoad = true;
			System.out.println("生成敏感词数量: " + sensitiveWordMap.size());
			System.out.println("加载敏感词库用时: " + (time2 - time1) + " ms");
		}
//		System.out.println(sensitiveWordMap.toString());
	}

	// 重新设置
	public static void reset() {
		System.out.println("重新获取敏感词，生成子树");
		sensitiveWordMap.clear();
		List<String> keyWord = new ArrayList<String>();
		List<SensitiveWord> words = sensitiveService.get();
		for (SensitiveWord s : words) {
			keyWord.add(s.getWord());
		}
		addSensitiveWord(keyWord);
	}

	// 读取敏感词库
	public static List<SensitiveWord> readWord() {
		return sensitiveService.get();
	}

	// 添加敏感词树
	public static void addSensitiveWord(final List<String> keyWord) {
		if (keyWord != null) {
			if (keyWord.size() > 0) {
				sensitiveWordMap = new HashMap<>(keyWord.size());
				String key = null;
				Map nowMap = null;
				Map<Object, Object> newWordMap = null;
				Iterator<String> iterator = keyWord.iterator();
				while (iterator.hasNext()) {
					key = iterator.next();
					key = StringUtils.deleteWhitespace(key);
					if (StringUtils.isNotEmpty(key)) {
						nowMap = sensitiveWordMap;
						for (int i = 0; i < key.length(); i++) {
							char keyChar = key.charAt(i);
							Object wordMap = nowMap.get(keyChar);
							if (wordMap != null) {
								// 已经存在该子树则获取该子树以搜索下一个字符
								nowMap = (Map) wordMap;
							} else {
								// 不存在该子树则新增一个并且isEnd为0，因为不知道是不是最后一个字符
								newWordMap = new HashMap<Object, Object>();
								newWordMap.put("isEnd", 0);
								nowMap.put(keyChar, newWordMap);
								// 获取该新的子树以遍历下一个字符
								nowMap = newWordMap;
							}
							// 如果是最后一个字符则设置isEnd=1
							if (i == key.length() - 1) {
								nowMap.put("isEnd", 1);
							}
						}
					}
				}
			}
		}
	}

	// 替换敏感词
	public static String doFilter(final String text) {
		init();
		if (StringUtils.isEmpty(text)) {
			return null;
		}
		List<String> word = new ArrayList<>();
		char[] text_ch = text.toCharArray();
		Map nowMap = new HashMap<>();
		int sensitive_length = 0;// 匹配到敏感词的长度
		int end = 0;// 最后一个匹配的敏感字的位置
		boolean isLast = false;// 敏感词是否试最后一个
		for (int i = 0; i < text_ch.length; i++) {
			char key = text_ch[i];
			nowMap = (Map) sensitiveWordMap.get(key);
			// 如果匹配到敏感字
			if (nowMap != null) {
				// 记录下起始位置
				end = i;
				// 从起始位置开始遍历后面的文本
				for (; ++end < text_ch.length;) {
					nowMap = (Map) nowMap.get(text_ch[end]);
					if (nowMap == null) {
						// 如果为空则不存在该子树，结束循环
						break;
					}
					// 存在子树，且isEnd=1 已经匹配到敏感词
					if (nowMap.get("isEnd").equals(1)) {
						// 记录下敏感词的长度
						sensitive_length = end - i;
						isLast = true;
					}
				}
				// 已经匹配结束，将敏感词替换
				if (isLast) {
					for (int j = 0; j <= sensitive_length; j++) {
						text_ch[i + j] = BI;
					}
					// 调整下一个文字读取位置
					i += sensitive_length;
					isLast = false;
				}
			}
		}
		String newText = String.valueOf(text_ch);
		return newText;
	}

	// 检测是否有敏感词
	public static boolean hasSensitiveWord(final String text) {
		init();
		char[] text_ch = text.toCharArray();
		int sensitive_length = 0;
		int end = 0;
		Map nowMap = sensitiveWordMap;
		boolean isLast = false;
		boolean hasWord = false;
		for (int i = 0; i < text_ch.length; i++) {
			char key = text_ch[i];
			nowMap = (Map) sensitiveWordMap.get(key);
			if (nowMap != null) {
				end = i;
				for (; ++end < text_ch.length;) {
					nowMap = (Map) nowMap.get(text_ch[end]);
					if (nowMap == null) {
						break;
					}
					if (nowMap.get("isEnd").equals(1)) {
						isLast = true;
					}
				}
				if (isLast) {
					return true;
				}
			}
		}
		return false;
	}
}
