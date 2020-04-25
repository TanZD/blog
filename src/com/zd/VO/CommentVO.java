package com.zd.VO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	public String comment_content;

	public int comment_user_id;

	public int comment_article_id;

	public int comment_pid;

	public String create_time;

	public String username;

	public String p_username;

	public int is_article;

	@Override
	public String toString() {
		return "CommentVO [id=" + id + ", comment_content=" + comment_content + ", comment_user_id=" + comment_user_id
				+ ", comment_article_id=" + comment_article_id + ", comment_pid=" + comment_pid + ", create_time="
				+ create_time + ", username=" + username + ", p_username=" + p_username + ", is_article=" + is_article
				+ "]";
	}

	public int getId() {
		return id;
	}

	public int getIs_article() {
		return is_article;
	}

	public void setIs_article(int is_article) {
		this.is_article = is_article;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public int getComment_user_id() {
		return comment_user_id;
	}

	public void setComment_user_id(int comment_user_id) {
		this.comment_user_id = comment_user_id;
	}

	public int getComment_article_id() {
		return comment_article_id;
	}

	public void setComment_article_id(int comment_article_id) {
		this.comment_article_id = comment_article_id;
	}

	public int getComment_pid() {
		return comment_pid;
	}

	public void setComment_pid(int comment_pid) {
		this.comment_pid = comment_pid;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getP_username() {
		return p_username;
	}

	public void setP_username(String p_username) {
		this.p_username = p_username;
	}

}
