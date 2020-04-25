package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Tag;
import com.zd.VO.TagVO;

public interface TagDAO {
	public Tag insert(Tag tag);

	public Tag findByName(String name);

	// 文章的标签
	public List<Tag> findByArticleId(int article_id);

	// 标签的创建者(首次使用者)
	public List<Tag> findByUserId(int user_id);

	// 返回使用次数最多的(使用次数排序)
	public List<TagVO> findByUsedTimes(int limit);

	// 用户用过的标签
	public List<TagVO> findByUsedWithUserId(int user_id);

	public Tag findById(int tag_id);

	public List<TagVO> get(int limit, int start, int order);
	
	public List<Tag> getAll();

	// 按时间获取
	public List<Tag> getByTime(String data);
	
	public Integer getCount();

	// 根据创建时间获取标签
//	public List<Tag> getByCreateTime(String data);

	public Tag update(Tag tag);

	public Integer delete(int tag_id);
}
