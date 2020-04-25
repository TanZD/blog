package com.zd.DTO;

import java.util.List;

public class Page<T> {
	private int page;// 当前页数

	private int limit;// 每页显示数

	private int totalNum;// 数据总数

	private int totalPage;// 总页数

	private int startNum;// 起始位置

	private List<T> data;

	public Page(int page, int limit, int totalNum) {
		this.page = page;
		this.limit = limit;
		this.totalNum = totalNum;
		this.totalPage = totalNum % limit == 0 ? (totalNum / limit) : (totalNum / limit) + 1;
		this.startNum = (page - 1) * limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Page [page=" + page + ", limit=" + limit + ", totalNum=" + totalNum + ", totalPage=" + totalPage
				+ ", startNum=" + startNum + ", data=" + data + "]";
	}

}
