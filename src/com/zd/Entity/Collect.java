package com.zd.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "collect")
public class Collect {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "collect_article_id")
	private int collect_article_id;

	@Column(name = "collect_user_id")
	private int collect_user_id;

	@Column(name = "create_time")
	private String create_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCollect_article_id() {
		return collect_article_id;
	}

	public void setCollect_article_id(int collect_article_id) {
		this.collect_article_id = collect_article_id;
	}

	public int getCollect_user_id() {
		return collect_user_id;
	}

	public void setCollect_user_id(int collect_user_id) {
		this.collect_user_id = collect_user_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "Collect [id=" + id + ", collect_article_id=" + collect_article_id + ", collect_user_id="
				+ collect_user_id + ", create_time=" + create_time + "]";
	}

}
