package com.zd.VO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LikesVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private int likes_user_id;

	private int likes_article_id;

	private String create_time;

	private String update_time;

	private int status;
	
	private int is_article;
	

}
