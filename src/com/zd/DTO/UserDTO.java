package com.zd.DTO;

public class UserDTO {
	private int id;

	private String username;

	private String user_mail;

	private String image;

	private String register_time;

	private String profile;

	private int isAdmin;

	private int gender;

	private Integer article_count;

	private Integer comment_count;

	private Integer history_count;

	private Integer follow_count;

	private Integer like_count;

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUser_mail() {
		return user_mail;
	}

	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRegister_time() {
		return register_time;
	}

	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Integer getArticle_count() {
		return article_count;
	}

	public void setArticle_count(Integer article_count) {
		this.article_count = article_count;
	}

	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}

	public Integer getHistory_count() {
		return history_count;
	}

	public void setHistory_count(Integer history_count) {
		this.history_count = history_count;
	}

	public Integer getFollow_count() {
		return follow_count;
	}

	public void setFollow_count(Integer follow_count) {
		this.follow_count = follow_count;
	}

	public Integer getLike_count() {
		return like_count;
	}

	public void setLike_count(Integer like_count) {
		this.like_count = like_count;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", user_mail=" + user_mail + ", image=" + image
				+ ", register_time=" + register_time + ", profile=" + profile + ", isAdmin=" + isAdmin + ", gender="
				+ gender + ", article_count=" + article_count + ", comment_count=" + comment_count + ", history_count="
				+ history_count + ", follow_count=" + follow_count + ", like_count=" + like_count + "]";
	}

}
