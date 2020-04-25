package com.zd.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ArticleVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int user_id;

	// private String content;

	private int view_count;

	private String title;

	private String create_time;

	private String is_article;

	private Integer likes_count;

	private Integer comment_count;

	private String summary;

	private String username;

	// @ElementCollection(targetClass = Tag.class)
	// private List<Tag> tag;
	//
	// @ElementCollection(targetClass = Category.class)
	// private List<Category> category;

	public ArticleVO() {
	}

	public String getIs_article() {
		return is_article;
	}

	public void setIs_article(String is_article) {
		this.is_article = is_article;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getLikes_count() {
		return likes_count;
	}

	public void setLikes_count(Integer likes_count) {
		this.likes_count = likes_count;
	}

	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	// public String getContent() {
	// return content;
	// }
	//
	// public void setContent(String content) {
	// this.content = content;
	// }

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	//
	// public String getIs_article() {
	// return is_article;
	// }
	//
	// public void setIs_article(String is_article) {
	// this.is_article = is_article;
	// }

	// public List<Tag> getTag() {
	// return tag;
	// }
	//
	// public void setTag(List<Tag> tag) {
	// this.tag = tag;
	// }
	//
	// public List<Category> getCategory() {
	// return category;
	// }
	//
	// public void setCategory(List<Category> category) {
	// this.category = category;
	// }

	@Override
	public String toString() {
		return "ArticleVO [id=" + id + ", user_id=" + user_id + ", view_count=" + view_count + ", title=" + title
				+ ", create_time=" + create_time + ", is_article=" + is_article + ", likes_count=" + likes_count
				+ ", comment_count=" + comment_count + ", summary=" + summary + ", username=" + username + "]";
	}

}
