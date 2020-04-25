package com.zd.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "media")
public class Media {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "file_name")
	private String file_name;

	@Column(name = "file_path")
	private String file_path;

	@Column(name = "file_type")
	private int file_type;

	@Column(name = "article_id")
	private int article_id;

	@Column(name = "create_time")
	private String create_time;

	@Column(name = "user_id")
	private int user_id;

	@Column(name = "pid")
	private int pid;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public int getFile_type() {
		return file_type;
	}

	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	@Override
	public String toString() {
		return "Media [id=" + id + ", file_name=" + file_name + ", file_path=" + file_path + ", file_type=" + file_type
				+ ", article_id=" + article_id + ", create_time=" + create_time + ", user_id=" + user_id + ", pid="
				+ pid + "]";
	}

}
