package com.zd.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MessageList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int receiver_id;

	private String content;

	private String create_time;

	private String username;

	private String image;

	private String flag;

	private int isRead;

	MessageList() {
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(int receiver_id) {
		this.receiver_id = receiver_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "MessageList [id=" + id + ", receiver_id=" + receiver_id + ", content=" + content + ", create_time="
				+ create_time + ", username=" + username + ", image=" + image + ", flag=" + flag + ", isRead=" + isRead
				+ "]";
	}

}
