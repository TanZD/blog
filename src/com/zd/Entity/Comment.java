package com.zd.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int id;

	@Column(name = "comment_content")
	public String comment_content;

	@Column(name = "comment_user_id")
	public int comment_user_id;

	@Column(name = "comment_article_id")
	public int comment_article_id;

	@Column(name = "comment_pid")
	public int comment_pid;

	@Column(name = "create_time")
	public String create_time;

	public int getId() {
		return id;
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

	@Override
	public String toString() {
		return "Comment [id=" + id + ", comment_content=" + comment_content + ", comment_user_id=" + comment_user_id
				+ ", comment_article_id=" + comment_article_id + ", comment_pid=" + comment_pid + ", create_time="
				+ create_time + "]";
	}

}
