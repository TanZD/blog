package com.zd.DAO;

public interface ArticleTagRef {
	public Integer deleteByArticleId(int article_id);

	public Integer deleteByTagId(int tag_id);

	public Integer getCountByArticleId(int article_id);

	public Integer getCountByTagId(int tag_id);

	public Integer add(int article_id, int tag_id);
}
