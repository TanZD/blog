package com.zd.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zd.DTO.JSONResult;
import com.zd.DTO.UserDTO;
import com.zd.Entity.User;
import com.zd.VO.UserSimple;
import com.zd.VO.UserVO;

public interface UserService {
	public User insert(User user);
	
	public User getByUserId(int id);
	
	public User getByUsernameOrMail(String username);

	public User getByUsername(String username);

	public User getByMail(String mail);

	public User update(User user);

	public Integer updatePassword(User user);

	public UserDTO getByUser(int id);

	public JSONResult<UserSimple> get(int page, int limit);

	public JSONResult<UserVO> getOrderByArticleNum(int page, int limit);

	public JSONResult<UserVO> getOrderByCommentNum(int page, int limit);

	public JSONResult<UserVO> getOrderByHistoryNum(int page, int limit);

	public JSONResult<UserVO> getOrderByCollectNum(int page, int limit);

	public Integer getCount();

	public Integer setForget(String forget, int id);

	public String getForget(int id);

	List<HashMap> countByArticleNum(int limit);

	List<HashMap> countByRegisterTime(int limit, int order);

	public JSONResult<UserSimple> search(String key_word, int type, int page, int limit);
	
	public UserSimple getUserSimple(int user_id);

	Integer setAdmin(int user_id);

	Integer delete(int user_id);

}
