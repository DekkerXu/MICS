package com.ibm.mics.entity.util;

public class OrderInfo {
	
	String orderstartDate;
	String orderendDate;
	String city;
	String orderName;
	String certifitype;
	String certifiNumber;
	String sex;
	String birthday;
	String phonenumber;
	String email;
	public OrderInfo(String orderstartDate, String orderendDate, String city, String orderName, String certifitype,
			String certifiNumber, String sex, String birthday, String phonenumber, String email) {
		this.orderstartDate = orderstartDate;
		this.orderendDate = orderendDate;
		this.city = city;
		this.orderName = orderName;
		this.certifitype = certifitype;
		this.certifiNumber = certifiNumber;
		this.sex = sex;
		this.birthday = birthday;
		this.phonenumber = phonenumber;
		this.email = email;
	}
	public String getOrderstartDate() {
		return orderstartDate;
	}
	public void setOrderstartDate(String orderstartDate) {
		this.orderstartDate = orderstartDate;
	}
	public String getOrderendDate() {
		return orderendDate;
	}
	public void setOrderendDate(String orderendDate) {
		this.orderendDate = orderendDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getCertifitype() {
		return certifitype;
	}
	public void setCertifitype(String certifitype) {
		this.certifitype = certifitype;
	}
	public String getCertifiNumber() {
		return certifiNumber;
	}
	public void setCertifiNumber(String certifiNumber) {
		this.certifiNumber = certifiNumber;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
