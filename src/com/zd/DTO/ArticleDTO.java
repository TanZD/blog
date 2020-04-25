package com.zd.DTO;

import java.util.List;

import com.zd.Entity.Category;
import com.zd.Entity.Media;
import com.zd.Entity.Tag;

public class ArticleDTO<T> {
	private T article;

	private UserDTO user;

	private List<Category> category;

	private List<Tag> tag;

	private List<Media> media;

	public List<Media> getMedia() {
		return media;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public void setMedia(List<Media> media) {
		this.media = media;
	}

	public T getArticle() {
		return article;
	}

	public void setArticle(T article) {
		this.article = article;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public List<Tag> getTag() {
		return tag;
	}

	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}

	public ArticleDTO(T article, List<Category> category, List<Tag> tag, List<Media> media) {
		super();
		this.article = article;
		this.category = category;
		this.tag = tag;
		this.media = media;
	}

	public ArticleDTO(T article, UserDTO user, List<Category> category, List<Tag> tag, List<Media> media) {
		super();
		this.article = article;
		this.user = user;
		this.category = category;
		this.tag = tag;
		this.media = media;
	}

	@Override
	public String toString() {
		return "ArticleDTO [article=" + article + ", user=" + user + ", category=" + category + ", tag=" + tag
				+ ", media=" + media + "]";
	}

}
