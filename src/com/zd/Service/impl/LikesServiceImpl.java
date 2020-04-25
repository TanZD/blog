package com.zd.Service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zd.DAO.LikesDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.Entity.Likes;
import com.zd.Service.LikesService;
import com.zd.Service.RedisService;
import com.zd.Util.Msg;
import com.zd.VO.LikesVO;

@Service("LikesService")
@Transactional
public class LikesServiceImpl implements LikesService {
	@Autowired
	LikesDAO likeDAO;

	@Autowired
	RedisService redisService;

	@Override
	public Likes save(Likes like) {
		return likeDAO.insert(like);
	}

	@Override
	public List<Likes> saveAll(List<Likes> likes) {
		return likeDAO.insertAll(likes);
	}
	
	@Override
	public Integer getLike(int user_id,int article_id){
		//先从redis中查找
		Integer result=redisService.getLike(user_id, article_id);
		if(result==null){
			//redis中没有再从数据库中查找
			Likes l=likeDAO.getByArticleIdAndUSerId(user_id, article_id);
			if(l==null){
				return null;
			}else{
				return l.getStatus();
			}
		}
		return result;
	}

	@Override
	public List<Likes> isLikeList(String[] article_id, int user_id) {
		if (article_id.length > 0) {
			// 查出数据库中的点赞情况
//			List<Likes> list_sql = likeDAO.isLikeList(article_id, user_id);
			List<Likes> result = new ArrayList<>();
			for (String s : article_id) {
				Integer value = redisService.getLike(user_id, Integer.valueOf(s));
				if (value == null) {
					// redis中没有 从数据库中查找
					Likes q = likeDAO.getByArticleIdAndUSerId(user_id, Integer.valueOf(s));
					if (q != null) {
						result.add(q);
					} else {
						// redis和数据库中都没有 用户对改文章从未有点赞操作
						Likes nl = new Likes();
						nl.setLikes_article_id(Integer.valueOf(s));
						nl.setLikes_user_id(user_id);
						nl.setStatus(0);
						result.add(nl);
					}
				} else {
					// 数据库和redis都有 以redis数据为准
					Likes nl = new Likes();
					nl.setLikes_article_id(Integer.valueOf(s));
					nl.setLikes_user_id(user_id);
					nl.setStatus(value);
					result.add(nl);

				}
			}
			return result;
		}
		return null;
	}

	@Override
	public Likes getByArticleIdAndUserId(int user_id, int article_id) {
		return likeDAO.getByArticleIdAndUSerId(user_id, article_id);
	}

	@Override
	public void transLikesFromRedis() {
		List<Likes> data = redisService.getLikesFromRedis();
		for (Likes like : data) {
			Likes old = this.getByArticleIdAndUserId(like.getLikes_user_id(), like.getLikes_article_id());
			if (old == null) {
				this.save(like);
			} else {
				old.setStatus(like.getStatus());
				this.save(old);
			}
		}
		redisService.deleteLikeCount();
	}

	@Override
	public void transLikesCountFromRedis() {

	}

}
