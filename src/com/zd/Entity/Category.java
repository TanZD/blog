package com.zd.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "category_name")
	private String category_name;

	@Column(name = "creator_id")
	private int creator_id;

	@Column(name = "create_time")
	private String create_time;

	@Column(name = "is_order")
	private int is_order;

	public int getIs_order() {
		return is_order;
	}

	public void setIs_order(int is_order) {
		this.is_order = is_order;
	}

	public int getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(int creator_id) {
		this.creator_id = creator_id;
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

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}


	@Override
	public String toString() {
		return "Category [id=" + id + ", category_name=" + category_name + ", creator_id=" + creator_id
				+ ", create_time=" + create_time + ", is_order=" + is_order + "]";
	}

}
