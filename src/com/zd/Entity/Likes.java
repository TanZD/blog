package com.zd.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "likes_user_id")
	private int likes_user_id;

	@Column(name = "likes_article_id")
	private int likes_article_id;

	@Column(name = "create_time")
	private String create_time;

	@Column(name = "update_time")
	private String update_time;

	@Column(name = "status")
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLikes_user_id() {
		return likes_user_id;
	}

	public void setLikes_user_id(int likes_user_id) {
		this.likes_user_id = likes_user_id;
	}

	public int getLikes_article_id() {
		return likes_article_id;
	}

	public void setLikes_article_id(int likes_article_id) {
		this.likes_article_id = likes_article_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Likes [id=" + id + ", likes_user_id=" + likes_user_id + ", likes_article_id=" + likes_article_id
				+ ", create_time=" + create_time + ", update_time=" + update_time + ", status=" + status + "]";
	}

}
