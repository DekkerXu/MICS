package com.ibm.mics.entity.base;

public class Constants {
	public static String SUCCESS = "00";
	public static String FAIL = "11";
	
	private int code;
	private String msg;
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
}
