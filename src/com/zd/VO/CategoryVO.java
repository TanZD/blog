package com.zd.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CategoryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String category_name;

	private int creator_id;

	private String create_time;

	private int is_order;

	private int times;

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

	public int getIs_order() {
		return is_order;
	}

	public void setIs_order(int is_order) {
		this.is_order = is_order;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Override
	public String toString() {
		return "CategoryVO [id=" + id + ", category_name=" + category_name + ", creator_id=" + creator_id
				+ ", create_time=" + create_time + ", is_order=" + is_order + ", times=" + times + "]";
	}

}
