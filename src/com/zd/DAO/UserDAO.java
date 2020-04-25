package com.zd.DAO;

import java.util.List;

import com.zd.Entity.User;
import com.zd.VO.UserSimple;
import com.zd.VO.UserVO;

public interface UserDAO {
	public User insert(User user);
	
	public User getByUserId(int id);
	
	public String getForget(int id);

	public User getByUsernameOrMail(String username);

	public User getByUsername(String username);

	public User getByMail(String mail);

	public User update(User user);

	public Integer updatePassword(User user);

	public User getById(int id);

	public Integer setForget(String forget, int id);

	public List<UserSimple> get(int start, int limit);

	public List<UserVO> getOrderByArticleNum(int start, int limit);

	public List<UserVO> getOrderByCommentNum(int start, int limit);

	public List<UserVO> getOrderByHistoryNum(int start, int limit);

	public List<UserVO> getOrderByCollectNum(int start, int limit);

	public Integer getCount();

	List<Object[]> CountByArticleNum(int limit);

	List<Object[]> CountByRegisterTime(int limit, int order);

	List<UserSimple> searchById(int id, int start, int limit);

	List<UserSimple> searchByMail(String mail, int start, int limit);

	List<UserSimple> searchByName(String name, int start, int limit);

	List<UserSimple> searchByRegisterTime(String time, int start, int limit);

	List<UserSimple> searchByGender(int gender, int start, int limit);

	Integer countByMail(String mail);

	Integer countByName(String name);

	Integer countByRegisterTime(String time);

	Integer countByGender(int gender);

	Integer countById(int id);

	UserSimple getUserSimple(int user_id);

	Integer setAdmin(int user_id);

	Integer delete(int user_id);

}
