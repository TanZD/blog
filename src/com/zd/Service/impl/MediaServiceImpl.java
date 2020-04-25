package com.zd.Service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zd.DAO.MediaDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.Entity.Media;
import com.zd.Service.MediaService;
import com.zd.Util.Msg;

@Service("MediaService")
@Transactional
public class MediaServiceImpl implements MediaService {
	@Autowired
	MediaDAO mediaDAO;

	@Override
	public Media insert(Media media) {
		return mediaDAO.insert(media);
	}

	@Override
	public Integer delete(Media media) {
		return mediaDAO.delete(media);
	}

	@Override
	public JSONResult<Media> get(int page, int limit) {
		JSONResult<Media> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = mediaDAO.getCount();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<Media> data = mediaDAO.get(pageInfo.getStartNum(), limit);
			result.setModels(data);
			result.setCodeMsg(Msg.success_code, Msg.success);
		}
		return result;
	}

	@Override
	public JSONResult<Media> getByName(String name, int type, int page, int limit) {
		JSONResult<Media> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = mediaDAO.getCountByName(name, type);
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<Media> data = mediaDAO.getByName(name, type, pageInfo.getStartNum(), limit);
			result.setModels(data);
			result.setCodeMsg(Msg.success_code, Msg.success);
		}
		return result;
	}

	@Override
	public JSONResult<Media> getByUser(int user_id, int type, int page, int limit) {
		JSONResult<Media> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = mediaDAO.getCountByUser(user_id, type);
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<Media> data = mediaDAO.getByUser(user_id, type, pageInfo.getStartNum(), limit);
			result.setModels(data);
			result.setCodeMsg(Msg.success_code, Msg.success);
		}
		return result;
	}

	@Override
	public JSONResult<Media> getByArticle(int article_id, int type, int page, int limit) {
		JSONResult<Media> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = mediaDAO.getCountByArticle(article_id, type);
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<Media> data = mediaDAO.getByArticle(article_id, type, pageInfo.getStartNum(), limit);
			result.setModels(data);
			result.setCodeMsg(Msg.success_code, Msg.success);
		}
		return result;
	}

	@Override
	public Media getById(int id) {
		return mediaDAO.getById(id);
	}

	@Override
	public List<Media> getByArticle(int article_id) {
		return mediaDAO.getByArticle(article_id);
	}

	@Override
	public Integer getCount() {
		return mediaDAO.getCount();
	}

	@Override
	public Integer getCountByType(int type) {
		return mediaDAO.getCountByType(type);
	}

	@Override
	public Integer getCountByUser(int user_id, int type) {
		return mediaDAO.getCountByUser(user_id, type);
	}

	@Override
	public Integer getCountByName(String name, int type) {
		return mediaDAO.getCountByName(name, type);
	}

	@Override
	public Integer getCountByArticle(int article_id, int type) {
		return mediaDAO.getCountByArticle(article_id, type);
	}

	@Override
	public Media getByName(String name, int type) {
		return mediaDAO.getByName(name, type);
	}

}
