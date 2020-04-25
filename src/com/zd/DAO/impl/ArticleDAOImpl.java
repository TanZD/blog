package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.ArticleDAO;
import com.zd.VO.DateCount;
import com.zd.Entity.Article;
import com.zd.VO.ADetailVO;
import com.zd.VO.ArticleSimple;
import com.zd.VO.ArticleVO;

@Repository("ArticleDAO")
public class ArticleDAOImpl extends BaseDAOImpl implements ArticleDAO {
	String sql = "SELECT a.`id`,a.`title`,a.`is_article`,a.`view_count`,DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time"
			+ ",a.`summary`,a.`user_id`,u.`username`,COUNT(DISTINCT c.`id`) AS comment_count"
			+ ",COUNT(DISTINCT l.`id`) AS likes_count " + "FROM article AS a LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
			+ "LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` "
			+ "LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 ";
	// + "GROUP BY a.`id` ORDER BY a.`view_count` DESC";

	String groupByAId = " GROUP BY a.`id` ";

	public String getOrder(int order) {
		String str = " ";
		switch (order) {
		case 0:
			str += "ORDER BY a.`view_count` DESC ";
			break;
		case 1:
			str += "ORDER BY likes_count DESC ";
			break;
		case 2:
			str += "ORDER BY comment_count DESC ";
			break;
		case 3:
			str += "ORDER BY a.`create_time` DESC ";
			break;
		case 4:
			str += "ORDER BY a.`view_count` ";
			break;
		case 5:
			str += "ORDER BY likes_count ";
			break;
		case 6:
			str += "ORDER BY comment_count ";
			break;
		case 7:
			str += "ORDER BY a.`create_time` ";
			break;
		default:
			str += "ORDER BY a.`create_time` DESC ";
			break;
		}
		return str;
	}

	@Override
	public Article insert(Article article) {
		getSession().save(article);
		return article;
	}

	@Override
	public Integer delete(int article_id) {
		String hql = "DELETE FROM Article WHERE id=:id";
		return getSession().createQuery(hql).setParameter("id", article_id).executeUpdate();
	}

	@Override
	public Article update(Article article) {
		getSession().update(article);
		return article;
	}

	@Override
	public List<ArticleVO> getAll() {
		String sql = this.sql + this.groupByAId;
		return getSession().createSQLQuery(sql).list();
	}

	@Override
	public List<ArticleVO> get(int start, int limit, int order) {
		String sql = this.sql + this.groupByAId + this.getOrder(order) + " LIMIT :start, :limit ";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(ArticleVO.class).list();
	}

	@Override
	public Article getById(int article_id) {
		String hql = "FROM Article WHERE id=:id";
		return (Article) getSession().createQuery(hql).setParameter("id", article_id).uniqueResult();
	}

	@Override
	public ADetailVO getDetailById(int article_id) {
		String sql="SELECT  a.* ,u.`username`"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` "
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1"
				+ " WHERE a.`id`=:a_id"
				+ " GROUP BY a.`id` ";
		return (ADetailVO) getSession().createSQLQuery(sql).setParameter("a_id", article_id).addEntity(ADetailVO.class)
				.uniqueResult();
	}

	@Override
	public List<ArticleVO> getByUserId(int user_id) {
		String sql = this.sql + " WHERE a.`user_id`=:user_id " + this.groupByAId;
		return getSession().createSQLQuery(sql).setParameter("user_id", user_id).addEntity(ArticleVO.class).list();
	}

	@Override
	public List<ArticleVO> getByUserId(int user_id, int start, int limit, int order) {
		String sql = this.sql + " WHERE a.`user_id`=:user_id " + this.groupByAId + this.getOrder(order)
				+ " LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("user_id", user_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}
	
	@Override
	public List<ArticleVO> getByCategoryId(int category_id, int start, int limit, int order) {
		String sql = "SELECT a.`id`,a.`is_article`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`"
				+ ",a.`summary`,u.`username`,cat.`id` AS category_id,cat.`category_name`"
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN article_category_ref AS tc ON tc.`article_id`=a.`id` "
				+ " LEFT JOIN category AS cat ON cat.`id`=tc.`category_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` "
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE cat.`id`=:c_id "
				+ " GROUP BY a.`id`";
		sql += this.getOrder(order) + " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("c_id", category_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}
	

	@Override
	public List<ArticleVO> getByUserAndCategory(int user_id,int category_id, int start, int limit, int order){
		String sql = "SELECT a.`id`,a.`is_article`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`"
				+ ",a.`summary`,u.`username`,cat.`id` AS category_id,cat.`category_name`"
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN article_category_ref AS tc ON tc.`article_id`=a.`id` "
				+ " LEFT JOIN category AS cat ON cat.`id`=tc.`category_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` "
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE cat.`id`=:c_id AND a.`user_id`=:u_id"
				+ " GROUP BY a.`id`";
		sql += this.getOrder(order) + " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("c_id", category_id).setParameter("u_id", user_id)
				.setParameter("start", start).setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}

	@Override
	public List<ArticleVO> getByCategoryId(int category_id) {
		String sql = "SELECT a.`id`,a.`is_article`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`"
				+ ",a.`summary`,u.`username`,cat.`id` AS category_id,cat.`category_name`"
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN article_category_ref AS tc ON tc.`article_id`=a.`id` "
				+ " LEFT JOIN category AS cat ON cat.`id`=tc.`category_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` "
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE cat.`id`=:c_id "
				+ " GROUP BY a.`id`"
				+ " ORDER BY a.`create_time` DESC ";
		return getSession().createSQLQuery(sql).setParameter("c_id", category_id).addEntity(ArticleVO.class).list();
	}

	@Override
	public List<ArticleVO> getByTagId(int tag_id, int start, int limit, int order) {
		String sql = "SELECT a.`id`,a.`is_article`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`"
				+ ",a.`summary`,u.`username`,t.`id` AS tag_id,t.`tag_name`"
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id` "
				+ " LEFT JOIN tag AS t ON t.`id`=tr.`tag_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id`"
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE t.`id`=:t_id"
				+ " GROUP BY a.`id`";
		sql += this.getOrder(order) + " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("t_id", tag_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}
	
	@Override
	public List<ArticleVO> getByUserAndTag(int user_id,int tag_id, int start, int limit, int order){
		String sql = "SELECT a.`id`,a.`is_article`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`"
				+ ",a.`summary`,u.`username`,t.`id` AS tag_id,t.`tag_name`"
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id` "
				+ " LEFT JOIN tag AS t ON t.`id`=tr.`tag_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id`"
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE t.`id`=:t_id AND a.`user_id`=:u_id"
				+ " GROUP BY a.`id`";
		sql += this.getOrder(order) + " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("t_id", tag_id).setParameter("u_id", user_id)
				.setParameter("start", start).setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}

	@Override
	public List<ArticleVO> getByTagId(int tag_id) {
		String sql = "SELECT a.`id`,a.`is_article`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`"
				+ ",a.`summary`,u.`username`,t.`id` AS tag_id,t.`tag_name`"
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id` "
				+ " LEFT JOIN tag AS t ON t.`id`=tr.`tag_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id`"
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE t.`id`=:t_id"
				+ " GROUP BY a.`id`"
				+ " ORDER BY a.`create_time` DESC ";
		return getSession().createSQLQuery(sql).setParameter("t_id", tag_id).addEntity(ArticleVO.class).list();
	}

	@Override
	public List<ArticleVO> getByFollow(int user_id) {
		String sql = "SELECT  a.`id`,a.`is_article`,a.`title`,a.`view_count`,a.`summary` "
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time,a.`user_id`,u.`username`"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` "
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE a.`user_id` IN (SELECT follow.`follow_pid` FROM follow WHERE follow_user_id=:u_id) "
				+ " GROUP BY a.`id` "
				+ " ORDER BY a.`create_time` DESC";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).addEntity(ArticleVO.class).list();
	}

	@Override
	public List<ArticleVO> getByFollow(int user_id, int start, int limit, int order) {
		String sql = "SELECT  a.`id`,a.`is_article`,a.`title`,a.`view_count`,a.`summary` "
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time,a.`user_id`,u.`username`"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` "
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE a.`user_id` IN (SELECT follow.`follow_pid` FROM follow WHERE follow_user_id=:u_id) "
				+ " GROUP BY a.`id` "
				+ " ORDER BY a.`create_time` DESC"
				+ " LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}

	@Override
	public Integer getCountByFollow(int user_id) {
		String sql = "SELECT COUNT(*) FROM article WHERE user_id IN (SELECT follow.`follow_pid` FROM follow WHERE follow_user_id=:u_id)";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", user_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public List<ArticleSimple> search(String[] words, int start, int limit) {
//		String sql = "SELECT * FROM article WHERE title LIKE CONCAT('%',:words,'%') "
//				+ " OR summary LIKE CONCAT('%',:words,'%') ORDER BY create_time DESC";
		String sql = "SELECT id,user_id,title,view_count,create_time,last_edit_time,is_article,summary FROM article WHERE ";
		/**
		    SELECT id,title,view_count,create_time,last_edit_time,is_article,readOnly,summary 
			FROM article WHERE 
			
			title LIKE CONCAT("%","文","%") 
			OR summary LIKE CONCAT("%","文","%") 
			
			OR title LIKE CONCAT("%","新","%") 
			OR summary LIKE CONCAT("%","新","%") 
			
			ORDER BY create_time DESC
		 */
		String key_word="";
		for (int i = 0; i < words.length; i++) {
			key_word += " title LIKE CONCAT('%',\"" + words[i] + "\",'%') OR summary LIKE CONCAT('%',\""
					+ words[i] + "\",'%') AND is_article=1";
			if (i != words.length - 1) {
				key_word += "OR";
			}
		}
		sql += key_word + " ORDER BY create_time DESC LIMIT :start, :limit";
		System.out.println(sql.toString());
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(ArticleSimple.class).list();
	}

	@Override
	public Integer getCountBySearch(String[] words) {
		String sql = "SELECT COUNT(id) FROM article WHERE ";
		String key_word = "";
		for (int i = 0; i < words.length; i++) {
			key_word += " title LIKE CONCAT('%',\"" + words[i] + "\",'%') OR summary LIKE CONCAT('%',\""
					+ words[i] + "\",'%')  AND is_article=1";
			if (i != words.length - 1) {
				key_word += "OR";
			}
		}
		sql += key_word;
		System.out.println(sql);
		Object object = getSession().createSQLQuery(sql).list().get(0);
		return Integer.valueOf(object.toString());
	}
	
	@Override
	public Integer updateReadable(int article_id,int status) {
//		String hql = "UPDATE Article SET readOnly=:status WHERE id=:id";
//		return getSession().createQuery(hql).setParameter("status", status).setParameter("id", article_id)
//				.executeUpdate();
		return null;
	}

	@Override
	public List<DateCount> ListByYear() {
		String sql = "SELECT DATE_FORMAT(create_time,'%Y') AS date,COUNT(*) AS count FROM article GROUP BY DATE ORDER BY DATE DESC";
		return getSession().createSQLQuery(sql).addEntity(DateCount.class).list();
	}

	@Override
	public List<DateCount> ListByYearAndMonth() {
		String sql = "SELECT COUNT(*) AS count,DATE_FORMAT(create_time,'%Y-%m') AS date FROM article GROUP BY DATE ORDER BY DATE DESC";
		return getSession().createSQLQuery(sql).addEntity(DateCount.class).list();
	}

	@Override
	public List<DateCount> ListByYearFromUserId(int user_id) {
		String sql = "SELECT COUNT(*) AS COUNT ,DATE_FORMAT(create_time,'%Y') AS DATE  FROM article WHERE user_id=:u_id GROUP BY DATE ORDER BY DATE DESC";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).addEntity(DateCount.class).list();
	}

	@Override
	public List<DateCount> ListByYearAndMonthFromUserId(int user_id) {
		String sql = "SELECT COUNT(*) AS COUNT,DATE_FORMAT(create_time,'%Y-%m') AS DATE FROM article WHERE user_id=:u_id GROUP BY DATE ORDER BY DATE DESC";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).addEntity(DateCount.class).list();
	}

	@Override
	public Integer getTotalViews() {
		String sql = "SELECT SUM(view_count) AS SUM FROM Article ";
		Object object = getSession().createSQLQuery(sql).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCount() {
		String sql = "SELECT COUNT(*) FROM article";
		return Integer.valueOf(getSession().createSQLQuery(sql).list().get(0).toString());
	}

	@Override
	public Integer getCountByTagId(int tag_id) {
		String sql = "SELECT COUNT(*) FROM article AS a INNER JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id` WHERE tr.`tag_id`=:t_id";
		Object object = getSession().createSQLQuery(sql).setParameter("t_id", tag_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCountByCategoryId(int category_id) {
		String sql = "SELECT COUNT(*) FROM article AS a INNER JOIN article_category_ref AS tc ON a.`id`=tc.`article_id` WHERE tc.`category_id`=:c_id";
		Object object = getSession().createSQLQuery(sql).setParameter("c_id", category_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer getCountByUserId(int user_id) {
		String sql = "SELECT COUNT(*) FROM article WHERE user_id=:u_id";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", user_id).list().get(0);
		return Integer.valueOf(object.toString());
	}
	
	@Override
	public Integer getCountByUserIdAndTag(int user_id,int tag_id) {
		String sql = "SELECT COUNT(*) FROM article AS a "
				+ " INNER JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id` "
				+ " INNER JOIN USER AS u ON u.`id`=a.`user_id` WHERE tr.`tag_id`=:t_id AND u.`id`=:u_id";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", user_id).setParameter("t_id", tag_id)
				.list().get(0);
		return Integer.valueOf(object.toString());
	}
	

	@Override
	public Integer getCountByUserIdAndCategory(int user_id,int category_id) {
		String sql = "SELECT COUNT(*) FROM article AS a "
				+ " INNER JOIN article_category_ref AS tc ON a.`id`=tc.`article_id` "
				+ " INNER JOIN USER AS u ON u.`id`=a.`user_id` WHERE tc.`category_id`=:c_id AND u.`id`=:u_id";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", user_id).setParameter("c_id", category_id)
				.list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer updateViews(int article_id, int views) {
		String hql = "UPDATE Article SET view_count=view_count + :view WHERE id=:id";
		return getSession().createQuery(hql).setParameter("view", views).setParameter("id", article_id).executeUpdate();
	}
	
	@Override
	public Integer updateViews(List<Article> article) {
//		String sql="UPDATE SET "
		return null;
	}

	@Override
	public List<ArticleVO> getPost(int start, int limit, int order) {
		String sql = this.sql + " WHERE a.`is_article`=0 " + this.groupByAId + this.getOrder(order)
				+ " LIMIT :start, :limit ";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(ArticleVO.class).list();
	}

	@Override
	public Integer countByPost() {
		String sql = "SELECT COUNT(*) FROM article WHERE is_article=0";
		return Integer.valueOf(getSession().createSQLQuery(sql).list().get(0).toString());
	}

	@Override
	public List<ArticleVO> postByUserId(int user_id, int start, int limit, int order) {
		String sql = this.sql + " WHERE a.`is_article`=0 AND a.`user_id`=:user_id" + this.groupByAId
				+ this.getOrder(order) + " LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("user_id", user_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}

	@Override
	public Integer countPostByUserId(int user_id) {
		String sql = "SELECT COUNT(*) FROM article WHERE user_id=:user_id AND is_article=0";
		return Integer
				.valueOf(getSession().createSQLQuery(sql).setParameter("user_id", user_id).list().get(0).toString());
	}

	@Override
	public List<ArticleVO> postByTag(int tag_id, int start, int limit, int order) {
		String sql = "SELECT a.`id`,a.`is_article`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`"
				+ ",a.`summary`,u.`username`,t.`id` AS tag_id,t.`tag_name`"
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id` "
				+ " LEFT JOIN tag AS t ON t.`id`=tr.`tag_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id`"
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE t.`id`=:t_id AND a.`is_article`=0 "
				+ " GROUP BY a.`id`";
		sql += this.getOrder(order) + " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("t_id", tag_id).setParameter("limit", limit)
				.setParameter("start", start).addEntity(ArticleVO.class).list();
	}

	@Override
	public Integer countPostByTag(int tag) {
		String sql = "SELECT COUNT(*) FROM article AS a INNER JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id` WHERE tr.`tag_id`=:t_id AND a.`is_article`=0";
		return Integer.valueOf(getSession().createSQLQuery(sql).setParameter("t_id", tag).list().get(0).toString());
	}

	@Override
	public List<ArticleVO> postByFollow(int follow_id, int start, int limit, int order) {
		String sql = "SELECT  a.`id`,a.`is_article`,a.`title`,a.`view_count`,a.`summary` "
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time,a.`user_id`,u.`username`"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` "
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE a.`user_id` IN (SELECT follow.`follow_pid` FROM follow WHERE follow_user_id=:u_id) AND a.`is_article`=0 "
				+ " GROUP BY a.`id`";
		sql += this.getOrder(order) + " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("u_id", follow_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}

	@Override
	public Integer countPostByFollow(int follow_id) {
		String sql = "SELECT COUNT(*) FROM article WHERE user_id IN (SELECT follow.`follow_pid` FROM follow WHERE follow_user_id=:u_id) AND article.`is_article`=0";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", follow_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public List<ArticleVO> getArticle(int start, int limit, int order) {
		String sql = this.sql +" WHERE a.`is_article`=1 "+ this.groupByAId + this.getOrder(order) + " LIMIT :start, :limit ";
		return getSession().createSQLQuery(sql).setParameter("start", start).setParameter("limit", limit)
				.addEntity(ArticleVO.class).list();
	}

	@Override
	public Integer countArticle() {
		String sql = "SELECT COUNT(*) FROM article WHERE is_article=1";
		return Integer.valueOf(getSession().createSQLQuery(sql).list().get(0).toString());
	}

	@Override
	public List<ArticleVO> ArticleByUserId(int user_id, int start, int limit, int order) {
		String sql = this.sql + " WHERE a.`user_id`=:user_id AND a.`is_article`=1 " + this.groupByAId
				+ this.getOrder(order) + " LIMIT :start, :limit";
		return getSession().createSQLQuery(sql).setParameter("user_id", user_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}

	@Override
	public Integer countArticleByUserId(int user_id) {
		String sql = "SELECT COUNT(*) FROM article WHERE is_article=1 AND user_id=:u_id";
		return Integer.valueOf(getSession().createSQLQuery(sql).setParameter("u_id", user_id).list().get(0).toString());
	}

	@Override
	public List<ArticleVO> ArticleByCategory(int cate_id, int start, int limit, int order) {
		String sql = "SELECT a.`id`,a.`is_article`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`"
				+ ",a.`summary`,u.`username`,cat.`id` AS category_id,cat.`category_name`"
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN article_category_ref AS tc ON tc.`article_id`=a.`id` "
				+ " LEFT JOIN category AS cat ON cat.`id`=tc.`category_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` "
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE cat.`id`=:c_id AND a.`is_article`=1"
				+ " GROUP BY a.`id`";
		sql += this.getOrder(order) + " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("c_id", cate_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}

	@Override
	public Integer countArticleByCategory(int cate_id) {
		String sql = "SELECT COUNT(*) FROM article AS a INNER JOIN article_category_ref AS tc ON a.`id`=tc.`article_id` WHERE tc.`category_id`=:c_id AND a.`is_article`=1";
		Object object = getSession().createSQLQuery(sql).setParameter("c_id", cate_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public List<ArticleVO> ArticleByTag(int tag_id, int start, int limit, int order) {
		String sql = "SELECT a.`id`,a.`is_article`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`"
				+ ",a.`summary`,u.`username`,t.`id` AS tag_id,t.`tag_name`"
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id` "
				+ " LEFT JOIN tag AS t ON t.`id`=tr.`tag_id` "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id`"
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE t.`id`=:t_id AND a.`is_article`=1"
				+ " GROUP BY a.`id`";
		sql += this.getOrder(order) + " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("t_id", tag_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}

	@Override
	public Integer countArticleByTag(int tag_id) {
		String sql = "SELECT COUNT(*) FROM article AS a INNER JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id` WHERE tr.`tag_id`=:t_id AND a.`is_article`=1";
		Object object = getSession().createSQLQuery(sql).setParameter("t_id", tag_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public List<ArticleVO> ArticleByFollow(int follow_id, int start, int limit, int order) {
		String sql = "SELECT  a.`id`,a.`is_article`,a.`title`,a.`view_count`,a.`summary` "
				+ ",DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time,a.`user_id`,u.`username`"
				+ ",COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count "
				+ " FROM article AS a "
				+ " LEFT JOIN USER AS u ON u.`id`=a.`user_id` "
				+ " LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` "
				+ " LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1 "
				+ " WHERE a.`user_id` IN (SELECT follow.`follow_pid` FROM follow WHERE follow_user_id=:u_id) AND a.`is_article`=1 "
				+ " GROUP BY a.`id`";
		sql += this.getOrder(order) + " LIMIT :start,:limit ";
		return getSession().createSQLQuery(sql).setParameter("u_id", follow_id).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}

	@Override
	public Integer countArticleByFollow(int follow_id) {
		String sql = "SELECT COUNT(*) FROM article WHERE user_id IN (SELECT follow.`follow_pid` FROM follow WHERE follow_user_id=:u_id) AND article.`is_article`=1";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", follow_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public List<ArticleVO> ArticleByTime(String time, int start,int limit) {
		String sql = this.sql + " WHERE a.`is_article`=1 AND DATE_FORMAT(a.`create_time`,'%Y-%m')=:date" + this.groupByAId
				+ " ORDER BY create_time DESC LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("date", time).setParameter("start", start)
				.setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}
	
	@Override
	public Integer countArticleByTime(String time) {
		String sql = "SELECT COUNT(*) FROM article WHERE is_article=1 AND DATE_FORMAT(create_time,'%Y-%m')=:date";
		return Integer.valueOf(getSession().createSQLQuery(sql).setParameter("date", time).list().get(0).toString());
	}

	@Override
	public List<ArticleVO> ArticleByTimeAndUserId(String time, int user_id, int start, int limit) {
		String sql = this.sql
				+ " WHERE a.`is_article`=1 AND DATE_FORMAT(a.`create_time`,'%Y-%m')=:date AND a.`user_id`=:user_id "
				+ this.groupByAId + " ORDER BY create_time DESC LIMIT :start,:limit";
		return getSession().createSQLQuery(sql).setParameter("date", time).setParameter("user_id", user_id)
				.setParameter("start", start).setParameter("limit", limit).addEntity(ArticleVO.class).list();
	}
	
	@Override
	public Integer countArticleByTimeAndUserId(String time,int user_id) {
		String sql = "SELECT COUNT(*) FROM article WHERE is_article=1 AND DATE_FORMAT(create_time,'%Y-%m')=:date AND user_id=:user_id";
		return Integer.valueOf(getSession().createSQLQuery(sql).setParameter("date", time).setParameter("user_id", user_id).list().get(0).toString());
	}

	
}
