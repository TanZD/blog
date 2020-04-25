package com.zd.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "article")
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "user_id")
	private int user_id;

	@Column(name = "content")
	private String content;

	@Column(name = "view_count")
	private int view_count;

	@Column(name = "title")
	private String title;

	@Column(name = "create_time")
	private String create_time;

	@Column(name = "last_edit_time")
	private String last_edit_time;

	@Column(name = "is_article")
	private int is_article;

	@Column(name = "summary")
	private String summary;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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

	public String getLast_edit_time() {
		return last_edit_time;
	}

	public void setLast_edit_time(String last_edit_time) {
		this.last_edit_time = last_edit_time;
	}

	public int getIs_article() {
		return is_article;
	}

	public void setIs_article(int is_article) {
		this.is_article = is_article;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", user_id=" + user_id + ", content=" + content + ", view_count=" + view_count
				+ ", title=" + title + ", create_time=" + create_time + ", last_edit_time=" + last_edit_time
				+ ", is_article=" + is_article + ", summary=" + summary + "]";
	}

}
