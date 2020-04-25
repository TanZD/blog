package com.zd.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "history")
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "history_article_id")
	private int history_article_id;

	@Column(name = "history_user_id")
	private int history_user_id;

	@Column(name = "create_time")
	private String create_time;

	@Column(name = "access_time")
	private String access_time;

	public String getAccess_time() {
		return access_time;
	}

	public void setAccess_time(String access_time) {
		this.access_time = access_time;
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

	@Override
	public String toString() {
		return "History [id=" + id + ", history_article_id=" + history_article_id + ", history_user_id="
				+ history_user_id + ", create_time=" + create_time + ", access_time=" + access_time + "]";
	}

}
