package com.zd.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zd.Service.ArticleService;
import com.zd.Service.LikesService;

@Component
@EnableScheduling
public class TimeScheduled {
	private static final Logger logger = LogManager.getLogger(TimeScheduled.class);
	
	@Autowired
	LikesService likeService;
	
	@Autowired
	ArticleService articleService;

	@Scheduled(cron = "0 0 0/1 * * ?") // 间隔小时执行
	public void tranLikes() {
		logger.info("定时执行:将redis中点赞数据存放到数据库中");
		likeService.transLikesFromRedis();
	}

	@Scheduled(cron = "0 0 0/1 * * ?") // 间隔小时执行
	public void tranViews(){
		logger.info("定时执行:将redis中点击量数据存放到数据库中");
		articleService.tranViewsFromRedis();
	}
}
