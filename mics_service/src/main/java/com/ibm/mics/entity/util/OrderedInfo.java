package com.ibm.mics.entity.util;

public class OrderedInfo {
	
	String orderedName;
	String relationship;
	String certifiType2;
	String certifiNumber2;
	String birthday2;
	String sex2;
	String phoneNumber2;
	
	
	public OrderedInfo(String orderedName, String relationship, String certifiType2, String certifiNumber2,
			String birthday2, String sex2, String phoneNumber2) {
		this.orderedName = orderedName;
		this.relationship = relationship;
		this.certifiType2 = certifiType2;
		this.certifiNumber2 = certifiNumber2;
		this.birthday2 = birthday2;
		this.sex2 = sex2;
		this.phoneNumber2 = phoneNumber2;
	}
	public String getOrderedName() {
		return orderedName;
	}
	public void setOrderedName(String orderedName) {
		this.orderedName = orderedName;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getCertifiType2() {
		return certifiType2;
	}
	public void setCertifiType2(String certifiType2) {
		this.certifiType2 = certifiType2;
	}
	public String getCertifiNumber2() {
		return certifiNumber2;
	}
	public void setCertifiNumber2(String certifiNumber2) {
		this.certifiNumber2 = certifiNumber2;
	}
	public String getBirthday2() {
		return birthday2;
	}
	public void setBirthday2(String birthday2) {
		this.birthday2 = birthday2;
	}
	public String getSex2() {
		return sex2;
	}
	public void setSex2(String sex2) {
		this.sex2 = sex2;
	}
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

}
