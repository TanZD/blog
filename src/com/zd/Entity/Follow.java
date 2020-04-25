package com.zd.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "follow")
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "follow_user_id")
	private int follow_user_id;

	@Column(name = "follow_pid")
	private int follow_pid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFollow_user_id() {
		return follow_user_id;
	}

	public void setFollow_user_id(int follow_user_id) {
		this.follow_user_id = follow_user_id;
	}

	public int getFollow_pid() {
		return follow_pid;
	}

	public void setFollow_pid(int follow_pid) {
		this.follow_pid = follow_pid;
	}

	@Override
	public String toString() {
		return "Follow [id=" + id + ", follow_user_id=" + follow_user_id + ", follow_pid=" + follow_pid + "]";
	}

}
