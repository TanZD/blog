package com.zd.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FollowVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int follow_user_id;

	private int follow_pid;

	private String p_username;

	private String p_image;

	private String profile;

	@Override
	public String toString() {
		return "FollowVO [id=" + id + ", follow_user_id=" + follow_user_id + ", follow_pid=" + follow_pid
				+ ", p_username=" + p_username + ", p_image=" + p_image + ", profile=" + profile + "]";
	}

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

	public String getP_username() {
		return p_username;
	}

	public void setP_username(String p_username) {
		this.p_username = p_username;
	}

	public String getP_image() {
		return p_image;
	}

	public void setP_image(String p_image) {
		this.p_image = p_image;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	FollowVO() {
	}

}
