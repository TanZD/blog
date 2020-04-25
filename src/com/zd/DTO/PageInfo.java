package com.zd.DTO;

public class PageInfo {
	private Integer page;

	private Integer startNum;

	private Integer limit;

	private Integer totalNum;

	private Integer totalpage;

	public PageInfo(Integer page, Integer limit, Integer totalNum) {
		this.page = page;
		this.limit = limit;
		this.totalNum = totalNum;
		this.totalpage = (totalNum % limit) == 0 ? (totalNum / limit) : (totalNum / limit) + 1;
		this.startNum = (page - 1) * limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(Integer totalpage) {
		this.totalpage = totalpage;
	}

	@Override
	public String toString() {
		return "PageInfo [page=" + page + ", startNum=" + startNum + ", limit=" + limit + ", totalNum=" + totalNum
				+ ", totalpage=" + totalpage + "]";
	}

}
