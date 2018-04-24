package com.ibm.mics.entity.base;

import com.ibm.mics.entity.util.User;

public class StrEntity {
	private int code;
	private String msg;
	private User user;
	
	public StrEntity(Constants con) {
		this.code=con.getCode();
		this.msg=con.getMsg();
	}
	
	public StrEntity(Constants con,User user) {
		this.code=con.getCode();
		this.msg=con.getMsg();
		this.user=user;
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
