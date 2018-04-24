package com.ibm.mics.entity.util;

public class UserParameters extends User {
	private int pageNo;//页码
	private int pageSize;//页面大小
	public UserParameters(String userName, String password) {
		super( userName, password);
		// TODO Auto-generated constructor stub
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
