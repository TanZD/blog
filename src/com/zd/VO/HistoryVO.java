package com.zd.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HistoryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int history_article_id;

	private int history_user_id;

	private String create_time;

	private String title;

	private String username;

	HistoryVO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHistory_article_id() {
		return history_article_id;
	}

	public void setHistory_article_id(int history_article_id) {
		this.history_article_id = history_article_id;
	}

	public int getHistory_user_id() {
		return history_user_id;
	}

	public void setHistory_user_id(int history_user_id) {
		this.history_user_id = history_user_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "HistoryVO [id=" + id + ", history_article_id=" + history_article_id + ", history_user_id="
				+ history_user_id + ", create_time=" + create_time + ", title=" + title + ", username=" + username
				+ "]";
	}

}
