package com.zd.Service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zd.DAO.CategoryDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.Entity.Category;
import com.zd.Service.CategoryService;
import com.zd.Util.Msg;
import com.zd.Util.MyUtil;
import com.zd.VO.CategoryVO;

@Service("CategoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDAO categoryDAO;

	@Override
	public Category insert(Category category) {
		if (category != null) {
			// 查看输入格式
			category.setCategory_name(StringUtils.deleteWhitespace(category.getCategory_name()));
			if (!StringUtils.isBlank(category.getCategory_name())) {
				// 查看是否有同名
				Category old = categoryDAO.findByName(category.getCategory_name());
				if (old == null) {
					category.setCreate_time(MyUtil.ToadyDate());
					categoryDAO.insert(category);
					return category;
				} else {
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public List<Category> getAll() {
		return categoryDAO.getAll();
	}

	@Override
	public JSONResult<CategoryVO> get(int page, int limit, int order) {
		int status = 1;
		Integer totalNum = categoryDAO.getCount();
		if (totalNum == 0)
			status = 0;
		PageInfo pageInfo = new PageInfo(page, limit, totalNum);
		// order 1为时间倒序 0为正序
		List<CategoryVO> data = categoryDAO.get(limit, pageInfo.getStartNum(), order);
		return new JSONResult<CategoryVO>(200, status, "成功", pageInfo, data);
	}

	@Override
	public List<Category> getByPid(int pid) {
		return categoryDAO.getByPid(pid);
	}

	@Override
	public Integer getCount() {
		return categoryDAO.getCount();
	}

	@Override
	public List<Category> getByTime(String data) {
		return categoryDAO.getByTime(data);
	}

	@Override
	public List<Category> findByArticleId(int article_id) {
		return categoryDAO.findByArticleId(article_id);
	}

	@Override
	public List<Category> findByUserId(int user_id) {
		return categoryDAO.findByUserId(user_id);
	}

	@Override
	public JSONResult<CategoryVO> findByUsedTimes(int limit) {
		List<CategoryVO> data = categoryDAO.findByUsedTimes(limit);
		return new JSONResult<CategoryVO>(200, 1, "成功", data);
	}

	@Override
	public List<CategoryVO> findByUsedWithUserId(int user_id) {
		return categoryDAO.findByUsedWithUserId(user_id);
	}

	@Override
	public Category findById(int category_id) {
		return categoryDAO.findById(category_id);
	}

	@Override
	public Category findByName(String name) {
		return categoryDAO.findByName(name);
	}

	@Override
	public Category update(Category category) {
		return categoryDAO.update(category);
	}

	@Override
	public Integer delete(int category_id) {
		return categoryDAO.delete(category_id);
	}

	@Override
	public Category saveOrUpdate(Category category) {
		if (category.getId() != 0) {
			// 更新
			return update(category);
		} else {
			return insert(category);
		}
	}

	@Override
	public List<Category> getOrInsert(String[] cate, int user_id) {
		List<Category> cateList = new ArrayList<>();
		for (String c : cate) {
			c = StringUtils.deleteWhitespace(c);
			if (!StringUtils.isBlank(c)) {
				Category old = categoryDAO.findByName(c);
				// 数据库中已有该分类
				if (old != null) {
					cateList.add(old);
				} else {
					Category newC = new Category();
					newC.setCategory_name(c);
					newC.setCreator_id(user_id);
					newC.setCreate_time(MyUtil.NowTime());
					categoryDAO.insert(newC);
					cateList.add(newC);
				}
			}
		}
		return cateList;
	}

	@Override
	public List<Category> getByWeight() {
		return categoryDAO.getByWeight();
	}

	@Override
	public Integer getCountByWeight() {
		return categoryDAO.getCountByWeight();
	}

	@Override
	public Integer updateWeight(int cate_id, int weight) {
		//取消首页显示
		if(weight==0){
			return categoryDAO.updateWeight(cate_id, weight);
		}else{
			Integer orderNum = categoryDAO.getCountByWeight();
			if (orderNum != null) {
				if (orderNum <= Msg.Cate_MAX_ORDER_NUM) {
					return categoryDAO.updateWeight(cate_id, weight);
				}else{
					Category v=categoryDAO.getById(cate_id);
					if(v!=null){
						if(v.getIs_order()!=0){
							return categoryDAO.updateWeight(cate_id, weight);
						}
					}
				}
			}
		}
		return null;
	}

}
