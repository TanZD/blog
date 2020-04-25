package com.zd.VO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ADetailVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int user_id;

	private String content;

	private int view_count;

	private String title;

	private String create_time;

	private String last_edit_time;

	private String is_article;

	private String summary;

	private Integer comment_count;

	private String username;

	private Integer likes_count;

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

	public String getIs_article() {
		return is_article;
	}

	public void setIs_article(String is_article) {
		this.is_article = is_article;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getLikes_count() {
		return likes_count;
	}

	public void setLikes_count(Integer likes_count) {
		this.likes_count = likes_count;
	}

	@Override
	public String toString() {
		return "ADetailVO [id=" + id + ", user_id=" + user_id + ", content=" + content + ", view_count=" + view_count
				+ ", title=" + title + ", create_time=" + create_time + ", last_edit_time=" + last_edit_time
				+ ", is_article=" + is_article + ", summary=" + summary + ", comment_count=" + comment_count
				+ ", username=" + username + ", likes_count=" + likes_count + "]";
	}

}
