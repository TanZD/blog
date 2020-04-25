package com.zd.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "toast")
public class Toast {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "toast_user_id")
	private int toast_user_id;

	@Column(name = "toast_sender_id")
	private int toast_sender_id;

	@Column(name = "create_time")
	private String create_time;

	@Column(name = "is_read")
	private int is_read;

	@Column(name = "toast_type")
	private int toast_type;

	@Column(name = "toast_article_id")
	private int toast_article_id;

	@Column(name = "toast_comment_id")
	private int toast_comment_id;

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

	@Override
	public String toString() {
		return "Toast [id=" + id + ", toast_user_id=" + toast_user_id + ", toast_sender_id=" + toast_sender_id
				+ ", create_time=" + create_time + ", is_read=" + is_read + ", toast_type=" + toast_type
				+ ", toast_article_id=" + toast_article_id + ", toast_comment_id=" + toast_comment_id + "]";
	}

}
