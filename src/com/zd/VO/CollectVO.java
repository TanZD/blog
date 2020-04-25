package com.zd.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CollectVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int collect_article_id;

	private int collect_user_id;

	private String create_time;

	private String title;

	private String summary;

	private Integer view_count;

	private String username;

	CollectVO() {
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getView_count() {
		return view_count;
	}

	public void setView_count(Integer view_count) {
		this.view_count = view_count;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "CollectVO [id=" + id + ", collect_article_id=" + collect_article_id + ", collect_user_id="
				+ collect_user_id + ", create_time=" + create_time + ", title=" + title + ", summary=" + summary
				+ ", view_count=" + view_count + ", username=" + username + "]";
	}

}
