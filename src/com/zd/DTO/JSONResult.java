package com.zd.DTO;

import java.util.List;

import com.zd.Entity.Category;

public class JSONResult<T> {

	private Integer status;

	private Integer code;

	private String msg;

	private PageInfo pageInfo;

	private T data;

	private List<T> models;

	public JSONResult() {
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public List<T> getModels() {
		return models;
	}

	public void setModels(List<T> models) {
		this.models = models;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setCodeMsg(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public JSONResult(Integer status, Integer code, String msg) {
		this.status = status;
		this.code = code;
		this.msg = msg;
	}

	public JSONResult(Integer status, Integer code, String msg, T data) {
		this.status = status;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public JSONResult(Integer status, Integer code, String msg, List<T> models) {
		super();
		this.status = status;
		this.code = code;
		this.msg = msg;
		this.models = models;
	}

	public JSONResult(Integer status, Integer code, String msg, PageInfo pageInfo, List<T> models) {
		this.status = status;
		this.code = code;
		this.msg = msg;
		this.pageInfo = pageInfo;
		this.models = models;
	}

	public JSONResult(Integer status, Integer code, String msg, PageInfo pageInfo, T data) {
		super();
		this.status = status;
		this.code = code;
		this.msg = msg;
		this.pageInfo = pageInfo;
		this.data = data;
	}

	@Override
	public String toString() {
		return "JSONResult [status=" + status + ", code=" + code + ", msg=" + msg + ", pageInfo=" + pageInfo + ", data="
				+ data + ", models=" + models + "]";
	}

}
