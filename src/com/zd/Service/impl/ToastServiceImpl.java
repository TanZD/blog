package com.zd.Service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zd.DAO.ToastDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.Entity.Article;
import com.zd.Entity.Comment;
import com.zd.Entity.Toast;
import com.zd.Service.ArticleService;
import com.zd.Service.CommentService;
import com.zd.Service.ToastService;
import com.zd.Util.Msg;
import com.zd.Util.TextFilter;
import com.zd.VO.CommentVO;
import com.zd.VO.ToastVO;

@Service("ToastService")
@Transactional
public class ToastServiceImpl implements ToastService {
	@Autowired
	ToastDAO toastDAO;
	@Autowired
	ArticleService articleService;
	@Autowired
	CommentService commentService;

	@Override
	@Async
	public Toast insertByComment(Comment comment) {
		Toast toast = new Toast();
		// 提醒文章作者有新的评论
		// 如果评论者就是文章作者就跳过不提醒
		Article article = articleService.getById(comment.getComment_article_id());
		if (article.getUser_id() != comment.getComment_user_id()) {
			toast.setToast_sender_id(comment.getComment_user_id());
			toast.setToast_user_id(article.getUser_id());
			toast.setToast_type(1);
			toast.setIs_read(1);
			toast.setCreate_time(comment.getCreate_time());
			toast.setToast_article_id(comment.getComment_article_id());
			toast.setToast_comment_id(comment.getId());
			this.insert(toast);
		}
		// 如果评论是回复的则提醒被评论的用户
		if (comment.getComment_pid() != 0) {
			CommentVO p_comment = commentService.getById(comment.getComment_pid());
			System.out.println(p_comment.toString());
			if (p_comment != null) {
				if (p_comment.getComment_user_id() != article.getUser_id()) {
					toast.setToast_sender_id(comment.getComment_user_id());
					toast.setToast_user_id(p_comment.getComment_user_id());
					toast.setIs_read(1);
					toast.setToast_type(2);
					toast.setCreate_time(comment.getCreate_time());
					toast.setToast_article_id(comment.getComment_article_id());
					toast.setToast_comment_id(comment.getId());
					this.insert(toast);
				}
			}
		}
		return toast;
	}

	@Override
	public Toast insert(Toast toast) {
		return toastDAO.insert(toast);
	}

	@Override
	public Toast update(Toast toast) {
		return toastDAO.update(toast);
	}

	@Override
	@Async
	public Integer deleteByCommentId(int comment_id) {
		return toastDAO.deleteByCommentId(comment_id);
	}

	@Override
	public Integer delete(int toast_id) {
		return toastDAO.delete(toast_id);
	}

	@Override
	public Integer setIsRead(int user_id) {
		return toastDAO.setIsRead(user_id);
	}

	@Override
	public List<ToastVO> get() {
		return toastDAO.get();
	}

	@Override
	public List<ToastVO> getAll() {
		return toastDAO.getAll();
	}

	@Override
	public List<ToastVO> getByUserId(int user_id) {
		return toastDAO.getByUserId(user_id);
	}

	@Override
	public JSONResult<ToastVO> getByUserId(int user_id, int page, int limit) {
		JSONResult<ToastVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		List<ToastVO> data = new ArrayList<ToastVO>();
		List<ToastVO> r_data = new ArrayList<ToastVO>();
		Integer totalNum = toastDAO.getCountByUserId(user_id);
		if (totalNum != 0) {
			if (page == -1) {
				data = this.getByUserId(user_id);
			} else {
				PageInfo pageInfo = new PageInfo(page, limit, totalNum);
				data = toastDAO.getByUserId(user_id, pageInfo.getStartNum(), limit);
				for (ToastVO v : data) {
					ToastVO v_data = new ToastVO(v.getId(), v.getToast_user_id(), v.getToast_sender_id(),
							v.getCreate_time(), v.getIs_read(), v.getToast_type(), v.getUsername(),
							v.getSender_username(), TextFilter.doFilter(v.getTitle()),
							TextFilter.doFilter(v.getComment_content()), v.getToast_article_id(),
							v.getToast_comment_id(), v.getIs_article());
					r_data.add(v_data);
				}
				result.setPageInfo(pageInfo);
			}
			result.setModels(r_data);
		}
		return result;
	}

	@Override
	public List<ToastVO> getByType(int type) {
		return toastDAO.getByType(type);
	}

	@Override
	public List<ToastVO> getByTime(String date) {
		return toastDAO.getByTime(date);
	}

	@Override
	public Integer getCountByUserId(int user_id) {
		return toastDAO.getCountByUserId(user_id);
	}

	@Override
	public Integer getCountByType(int type) {
		return toastDAO.getCountByType(type);
	}

}
