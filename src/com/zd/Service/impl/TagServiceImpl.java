package com.zd.Service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zd.DAO.TagDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.Entity.Tag;
import com.zd.Service.TagService;
import com.zd.Util.Msg;
import com.zd.Util.MyUtil;
import com.zd.VO.TagVO;

@Service("TagService")
@Transactional
public class TagServiceImpl implements TagService {
	@Autowired
	TagDAO tagDAO;

	@Override
	public Tag insert(Tag tag) {
		if (tag != null) {
			if (!StringUtils.isBlank(tag.getTag_name())) {
				// 先检测是否已经存在同名标签
				Tag result = tagDAO.findByName(tag.getTag_name());
				if (result == null) {
					// 获取系统时间
					tag.setCreate_time(MyUtil.ToadyDate());
					tagDAO.insert(tag);
					return tag;
				}
			}
		}
		return null;
	}

	@Override
	public Tag findByName(String name) {
		return tagDAO.findByName(name);
	}

	@Override
	public List<Tag> findByArticleId(int article_id) {
		return tagDAO.findByArticleId(article_id);
	}

	@Override
	public List<Tag> findByUserId(int user_id) {
		return tagDAO.findByUserId(user_id);
	}

	@Override
	public JSONResult<TagVO> findByUsedTimes(int limit) {
		List<TagVO> tag = tagDAO.findByUsedTimes(limit);
		JSONResult<TagVO> result = new JSONResult<TagVO>(200, 1, "成功", tag);
		return result;
	}

	@Override
	public List<TagVO> findByUsedWithUserId(int user_id) {
		return tagDAO.findByUsedWithUserId(user_id);
	}

	@Override
	public Tag findById(int tag_id) {
		return tagDAO.findById(tag_id);
	}

	@Override
	public JSONResult<TagVO> get(int limit, int page, int order) {
		Integer totalNum = tagDAO.getCount();
		PageInfo pageInfo = new PageInfo(page, limit, totalNum);
		List<TagVO> tag = tagDAO.get(limit, pageInfo.getStartNum(), order);
		JSONResult<TagVO> result = new JSONResult<TagVO>(Msg.OK, Msg.success_code, "成功", pageInfo, tag);
		return result;
	}

	@Override
	public List<Tag> getAll() {
		return tagDAO.getAll();
	}

	@Override
	public List<Tag> getByTime(String data) {
		return tagDAO.getByTime(data);
	}

	@Override
	public Tag update(Tag tag) {
		Tag old = tagDAO.findByName(tag.getTag_name());
		if (old == null) {
			tagDAO.update(tag);
			return tag;
		}
		return null;
	}

	@Override
	public Integer delete(int tag_id) {
		return tagDAO.delete(tag_id);
	}

	@Override
	public List<Tag> strToList(String str, int user_id) {
		String[] list = str.split(",");
		List<Tag> tags = new ArrayList<Tag>();
		for (String t : list) {
			Tag old = tagDAO.findByName(t);
			// 查看库中是否已有该标签
			if (old != null) {
				tags.add(old);
			} else {
				Tag newTag = new Tag();
				newTag.setTag_name(t.trim());
				newTag.setCreator_id(user_id);
				newTag.setCreate_time(MyUtil.ToadyDate());
				if (!StringUtils.isBlank(newTag.getTag_name())) {
					tagDAO.insert(newTag);
				}
				tags.add(newTag);
			}
		}
		return tags;
	}

	@Override
	public List<Tag> getOrInsert(String[] tag, int user_id) {
		List<Tag> tags = new ArrayList<>();
		for (String t : tag) {
			t = StringUtils.deleteWhitespace(t);
			if (!StringUtils.isBlank(t)) {
				Tag old = tagDAO.findByName(t);
				// 数据库中已有该标签
				if (old != null) {
					tags.add(old);
				} else {
					Tag newTag = new Tag();
					newTag.setTag_name(t);
					newTag.setCreator_id(user_id);
					newTag.setCreate_time(MyUtil.NowTime());
					tagDAO.insert(newTag);
					tags.add(newTag);
				}
			}
		}
		return tags;
	}

	@Override
	public Tag insertOrUpdate(Tag tag) {
		if (tag.getId() != 0) {
			return update(tag);
		} else {
			return insert(tag);
		}
	}

}
