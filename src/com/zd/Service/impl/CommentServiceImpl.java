package com.zd.Service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zd.DAO.CommentDAO;
import com.zd.DAO.ToastDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.Entity.Comment;
import com.zd.Service.CommentService;
import com.zd.Service.ToastService;
import com.zd.Util.Msg;
import com.zd.Util.MyUtil;
import com.zd.Util.TextFilter;
import com.zd.VO.CommentVO;

@Service("CommentService")
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	CommentDAO commentDAO;
	@Autowired
	ToastDAO toastDAO;
	@Autowired
	ToastService toastService;

	@Override
	public Comment insert(Comment comment) {
		comment.setCreate_time(MyUtil.NowTime());
		commentDAO.insert(comment);
		// 消息表添加消息
		toastService.insertByComment(comment);
		return comment;
	}

	@Override
	public Comment update(Comment comment) {
		return commentDAO.update(comment);
	}

	@Override
	public Integer delete(int comment_id) {
		Integer data = commentDAO.delete(comment_id);
		// 刪除之前所添加的消息
		toastService.deleteByCommentId(comment_id);
		return data;
	}

	@Override
	public List<CommentVO> getAll() {
		return commentDAO.getAll();
	}

	@Override
	public List<CommentVO> TextFilter(List<CommentVO> data) {
		List<CommentVO> result = new ArrayList<CommentVO>();
		for (CommentVO c : data) {
			CommentVO v = new CommentVO();
			v.setComment_article_id(c.getComment_article_id());
			v.setComment_content(TextFilter.doFilter(c.getComment_content()));
			v.setComment_pid(c.getComment_pid());
			v.setComment_user_id(c.getComment_user_id());
			v.setCreate_time(c.getCreate_time());
			v.setId(c.getId());
			v.setP_username(c.getP_username());
			v.setUsername(c.getUsername());
			v.setIs_article(c.getIs_article());
			result.add(v);
		}
		return result;
	}

	@Override
	public JSONResult<CommentVO> get(int page, int limit, int order) {
		JSONResult<CommentVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = commentDAO.getCount();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<CommentVO> data = TextFilter(commentDAO.get(pageInfo.getStartNum(), limit, order));
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<CommentVO> getByContent(String content, int page, int limit) {
		int status = 0;
		JSONResult<CommentVO> result = new JSONResult<>(200, status, "成功");
		Integer totalNum = commentDAO.getCountByContent(content);
		if (totalNum != 0) {
			status = 1;
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<CommentVO> data = TextFilter(commentDAO.getByContent(content, pageInfo.getStartNum(), limit));
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public List<CommentVO> getByUserId(int user_id) {
		return commentDAO.getByUserId(user_id);
	}

	@Override
	public JSONResult<CommentVO> getByUserId(int user_id, int page, int limit, int order) {
		int status = 0;
		JSONResult<CommentVO> result = new JSONResult<>(200, status, "成功");
		Integer totalNum = commentDAO.getCountByUserId(user_id);
		if (totalNum != 0) {
			status = 1;
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<CommentVO> data = TextFilter(commentDAO.getByUserId(user_id, pageInfo.getStartNum(), limit, order));
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public List<CommentVO> getByArticleId(int article_id) {
		return commentDAO.getByArticleId(article_id);
	}

	@Override
	public JSONResult<CommentVO> getByArticleId(int article_id, int page, int limit, int order) {
		int status = 0;
		JSONResult<CommentVO> result = new JSONResult<>(200, status, "成功");
		Integer totalNum = commentDAO.getCountByArticleId(article_id);
		if (totalNum != 0) {
			status = 1;
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<CommentVO> data = TextFilter(
					commentDAO.getByArticleId(article_id, pageInfo.getStartNum(), limit, order));
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public List<CommentVO> getByAcceptorId(int pid) {
		return null;
	}

	@Override
	public JSONResult<CommentVO> getByAcceptorId(int user_id, int page, int limit, int order) {
		JSONResult<CommentVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = commentDAO.getCountByAcceptorId(user_id);
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<CommentVO> data = TextFilter(
					commentDAO.getByAcceptorId(user_id, pageInfo.getStartNum(), limit, order));
			result.setCodeMsg(Msg.success_code, Msg.success);
			result.setModels(data);
		}
		return result;
	}

	@Override
	public CommentVO getById(int id) {
		return commentDAO.getById(id);
	}

	@Override
	public List<CommentVO> getByDate(String date) {
		return commentDAO.getByDate(date);
	}

	@Override
	public JSONResult<CommentVO> getByDate(String date, int page, int limit) {
		int status = 0;
		JSONResult<CommentVO> result = new JSONResult<>(200, status, "成功");
		Integer totalNum = commentDAO.getCountByDate(date);
		if (totalNum != 0) {
			status = 1;
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<CommentVO> data = TextFilter(commentDAO.getByDate(date, pageInfo.getStartNum(), limit));
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public Integer getCountByDate(String date) {
		return commentDAO.getCountByDate(date);
	}

	@Override
	public Integer getCount() {
		return commentDAO.getCount();
	}

	@Override
	public Comment insertOrUpdate(Comment comment) {
		if (comment.getId() != 0) {
			return this.update(comment);
		} else {
			return this.insert(comment);
		}
	}

}
