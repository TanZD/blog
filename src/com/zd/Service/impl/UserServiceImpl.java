package com.zd.Service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zd.DAO.ArticleDAO;
import com.zd.DAO.CollectDAO;
import com.zd.DAO.CommentDAO;
import com.zd.DAO.FollowDAO;
import com.zd.DAO.HistoryDAO;
import com.zd.DAO.LikesDAO;
import com.zd.DAO.UserDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.DTO.UserDTO;
import com.zd.Entity.User;
import com.zd.Service.UserService;
import com.zd.Util.Msg;
import com.zd.Util.MyUtil;
import com.zd.VO.UserSimple;
import com.zd.VO.UserVO;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO userDAO;

	@Autowired
	ArticleDAO articleDAO;

	@Autowired
	CollectDAO collectDAO;

	@Autowired
	HistoryDAO historyDAO;

	@Autowired
	LikesDAO likesDAO;

	@Autowired
	CommentDAO commentDAO;

	@Autowired
	FollowDAO followDAO;

	@Override
	public User insert(User user) {
		// 密码进行md5加密
		user.setPassword(MyUtil.md5code(user.getPassword()));
		User result = userDAO.insert(user);
		return result;
	}

	@Override
	public User getByUsernameOrMail(String username) {
		return userDAO.getByUsernameOrMail(username);
	}

	@Override
	public User getByUsername(String username) {
		return userDAO.getByUsername(username);
	}

	@Override
	public User getByMail(String mail) {
		return userDAO.getByMail(mail);
	}

	@Override
	public User update(User user) {
		return userDAO.update(user);
	}

	@Override
	public Integer updatePassword(User user) {
		user.setPassword(MyUtil.md5code(user.getPassword()));
		return userDAO.updatePassword(user);
	}

	@Override
	public String getForget(int id) {
		return userDAO.getForget(id);
	}

	@Override
	public UserDTO getByUser(int id) {
		UserDTO data = new UserDTO();
		User user = userDAO.getById(id);
		if (user != null) {
			data.setId(user.getId());
			data.setUsername(user.getUsername());
			data.setImage(user.getImage());
			data.setUser_mail(user.getUser_mail());
			data.setRegister_time(user.getRegister_time());
			data.setProfile(user.getProfile());
			data.setIsAdmin(user.getIsAdmin());
			data.setGender(user.getGender());
			data.setArticle_count(articleDAO.getCountByUserId(user.getId()));
			data.setComment_count(commentDAO.getCountByUserId(user.getId()));
			data.setFollow_count(followDAO.getCountByFollow(user.getId()));
			data.setHistory_count(historyDAO.getCountByUser(user.getId()));
			data.setLike_count(likesDAO.getCountByPId(user.getId()));
		}
		return data;
	}

	@Override
	public JSONResult<UserSimple> get(int page, int limit) {
		JSONResult<UserSimple> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		Integer totalNum = userDAO.getCount();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<UserSimple> data = userDAO.get(pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<UserVO> getOrderByArticleNum(int page, int limit) {
		JSONResult<UserVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		Integer totalNum = userDAO.getCount();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<UserVO> data = userDAO.getOrderByArticleNum(pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<UserVO> getOrderByCommentNum(int page, int limit) {
		JSONResult<UserVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		Integer totalNum = userDAO.getCount();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<UserVO> data = userDAO.getOrderByCommentNum(pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<UserVO> getOrderByHistoryNum(int page, int limit) {
		JSONResult<UserVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		Integer totalNum = userDAO.getCount();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<UserVO> data = userDAO.getOrderByHistoryNum(pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<UserVO> getOrderByCollectNum(int page, int limit) {
		JSONResult<UserVO> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.fail);
		Integer totalNum = userDAO.getCount();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<UserVO> data = userDAO.getOrderByCollectNum(pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public User getByUserId(int id) {
		return userDAO.getById(id);
	}

	@Override
	public Integer getCount() {
		return userDAO.getCount();
	}

	@Override
	public Integer setForget(String forget, int id) {
		return userDAO.setForget(forget, id);
	}

	@Override
	public List<HashMap> countByArticleNum(int limit) {
		List<Object[]> data = new ArrayList<>();
		List<HashMap> result = new ArrayList<>();
		data = userDAO.CountByArticleNum(limit);
		for (Object[] o : data) {
			HashMap h = new HashMap<>();
			h.put("username", o[0]);
			h.put("count", o[1]);
			result.add(h);
		}
		return result;
	}

	@Override
	public List<HashMap> countByRegisterTime(int limit, int order) {
		List<Object[]> data = new ArrayList<>();
		List<HashMap> result = new ArrayList<>();
		data = userDAO.CountByRegisterTime(limit, order);
		for (Object[] o : data) {
			HashMap h = new HashMap<>();
			h.put("count", o[0]);
			h.put("register_time", String.valueOf(o[1]));
			result.add(h);
		}
		return result;
	}

	@Override
	public JSONResult<UserSimple> search(String key_word, int type, int page, int limit) {
		JSONResult<UserSimple> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.fail);
		List<UserSimple> data = new ArrayList<>();
		Integer totalNum = 0;
		try {

			switch (type) {
			case 1:
				totalNum = userDAO.countById(Integer.valueOf(key_word));
				if (totalNum != 0) {
					PageInfo pageInfo = new PageInfo(page, limit, totalNum);
					data = userDAO.searchById(Integer.valueOf(key_word), pageInfo.getStartNum(), limit);
					result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
				}
				break;
			case 2:
				totalNum = userDAO.countByName(key_word);
				if (totalNum != 0) {
					PageInfo pageInfo = new PageInfo(page, limit, totalNum);
					data = userDAO.searchByName(key_word, pageInfo.getStartNum(), limit);
					result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
				}
				break;
			case 3:
				totalNum = userDAO.countByMail(key_word);
				if (totalNum != 0) {
					PageInfo pageInfo = new PageInfo(page, limit, totalNum);
					data = userDAO.searchByMail(key_word, pageInfo.getStartNum(), limit);
					result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
				}
				break;
			case 4:
				totalNum = userDAO.countByRegisterTime(key_word);
				if (totalNum != 0) {
					PageInfo pageInfo = new PageInfo(page, limit, totalNum);
					data = userDAO.searchByRegisterTime(key_word, pageInfo.getStartNum(), limit);
					result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
				}
				break;
			case 5:
				totalNum = userDAO.countByGender(Integer.valueOf(key_word));
				if (totalNum != 0) {
					PageInfo pageInfo = new PageInfo(page, limit, totalNum);
					data = userDAO.searchByGender(Integer.valueOf(key_word), pageInfo.getStartNum(), limit);
					result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public UserSimple getUserSimple(int user_id) {
		return userDAO.getUserSimple(user_id);
	}
	
	@Override
	public Integer setAdmin(int user_id){
		return userDAO.setAdmin(user_id);
	}
	
	@Override
	public Integer delete(int user_id){
		return userDAO.delete(user_id);
	}

}
