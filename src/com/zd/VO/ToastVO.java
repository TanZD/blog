package com.zd.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ToastVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int toast_user_id;

	private int toast_sender_id;

	private String create_time;

	private int is_read;

	private int is_article;

	private int toast_type;

	private String username;

	private String sender_username;

	private String title;

	private String comment_content;

	private int toast_article_id;

	private int toast_comment_id;

	public ToastVO() {
	}

	public int getIs_article() {
		return is_article;
	}

	public void setIs_article(int is_article) {
		this.is_article = is_article;
	}

	public ToastVO(int id, int toast_user_id, int toast_sender_id, String create_time, int is_read, int toast_type,
			String username, String sender_username, String title, String comment_content, int toast_article_id,
			int toast_comment_id, int is_article) {
		this.id = id;
		this.toast_user_id = toast_user_id;
		this.toast_sender_id = toast_sender_id;
		this.create_time = create_time;
		this.is_read = is_read;
		this.toast_type = toast_type;
		this.username = username;
		this.sender_username = sender_username;
		this.title = title;
		this.comment_content = comment_content;
		this.toast_article_id = toast_article_id;
		this.toast_comment_id = toast_comment_id;
		this.is_article = is_article;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getToast_article_id() {
		return toast_article_id;
	}

	public void setToast_article_id(int toast_article_id) {
		this.toast_article_id = toast_article_id;
	}

	public int getToast_comment_id() {
		return toast_comment_id;
	}

	public void setToast_comment_id(int toast_comment_id) {
		this.toast_comment_id = toast_comment_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getToast_user_id() {
		return toast_user_id;
	}

	public void setToast_user_id(int toast_user_id) {
		this.toast_user_id = toast_user_id;
	}

	public int getToast_sender_id() {
		return toast_sender_id;
	}

	public void setToast_sender_id(int toast_sender_id) {
		this.toast_sender_id = toast_sender_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getIs_read() {
		return is_read;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}

	public int getToast_type() {
		return toast_type;
	}

	public void setToast_type(int toast_type) {
		this.toast_type = toast_type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSender_username() {
		return sender_username;
	}

	public void setSender_username(String sender_username) {
		this.sender_username = sender_username;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	@Override
	public String toString() {
		return "ToastVO [id=" + id + ", toast_user_id=" + toast_user_id + ", toast_sender_id=" + toast_sender_id
				+ ", create_time=" + create_time + ", is_read=" + is_read + ", is_article=" + is_article
				+ ", toast_type=" + toast_type + ", username=" + username + ", sender_username=" + sender_username
				+ ", title=" + title + ", comment_content=" + comment_content + ", toast_article_id=" + toast_article_id
				+ ", toast_comment_id=" + toast_comment_id + "]";
	}

}
