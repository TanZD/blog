package com.zd.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MessageVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int sender_id;

	private int receiver_id;

	private String content;

	private String create_time;

	private int type;

	private int isRead;

	private String receiver;

	private String r_image;

	private String s_image;

	private String sender;

	private int flag;

	@Override
	public String toString() {
		return "MessageVO [id=" + id + ", sender_id=" + sender_id + ", receiver_id=" + receiver_id + ", content="
				+ content + ", create_time=" + create_time + ", type=" + type + ", isRead=" + isRead + ", receiver="
				+ receiver + ", r_image=" + r_image + ", s_image=" + s_image + ", sender=" + sender + ", flag=" + flag
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSender_id() {
		return sender_id;
	}

	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getR_image() {
		return r_image;
	}

	public void setR_image(String r_image) {
		this.r_image = r_image;
	}

	public String getS_image() {
		return s_image;
	}

	public void setS_image(String s_image) {
		this.s_image = s_image;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	MessageVO() {
	}
}
