package cn.mics.brank.fabric.entity;

public class MedicalCare {
	String kind;
	String fanwei;
	String payment;
	String patientName;
	
   String certifyType3;
   String certifiNumber3;
   String age;
   String sex3;
   String zhuyuan;
   String leixing; 
   String visitTime;
   String describe;
   String history;
public MedicalCare(String kind, String fanwei, String payment, String patientName, String certifyType3,
		String certifiNumber3, String age, String sex3, String zhuyuan, String leixing, String visitTime,
		String describe, String history) {
	super();
	this.kind = kind;
	this.fanwei = fanwei;
	this.payment = payment;
	this.patientName = patientName;
	this.certifyType3 = certifyType3;
	this.certifiNumber3 = certifiNumber3;
	this.age = age;
	this.sex3 = sex3;
	this.zhuyuan = zhuyuan;
	this.leixing = leixing;
	this.visitTime = visitTime;
	this.describe = describe;
	this.history = history;
}
public String getKind() {
	return kind;
}
public void setKind(String kind) {
	this.kind = kind;
}
public String getFanwei() {
	return fanwei;
}
public void setFanwei(String fanwei) {
	this.fanwei = fanwei;
}
public String getPayment() {
	return payment;
}
public void setPayment(String payment) {
	this.payment = payment;
}
public String getPatientName() {
	return patientName;
}
public void setPatientName(String patientName) {
	this.patientName = patientName;
}
public String getCertifyType3() {
	return certifyType3;
}
public void setCertifyType3(String certifyType3) {
	this.certifyType3 = certifyType3;
}
public String getCertifiNumber3() {
	return certifiNumber3;
}
public void setCertifiNumber3(String certifiNumber3) {
	this.certifiNumber3 = certifiNumber3;
}
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
public String getSex3() {
	return sex3;
}
public void setSex3(String sex3) {
	this.sex3 = sex3;
}
public String getZhuyuan() {
	return zhuyuan;
}
public void setZhuyuan(String zhuyuan) {
	this.zhuyuan = zhuyuan;
}
public String getLeixing() {
	return leixing;
}
public void setLeixing(String leixing) {
	this.leixing = leixing;
}
public String getVisitTime() {
	return visitTime;
}
public void setVisitTime(String visitTime) {
	this.visitTime = visitTime;
}
public String getDescribe() {
	return describe;
}
public void setDescribe(String describe) {
	this.describe = describe;
}
public String getHistory() {
	return history;
}
public void setHistory(String history) {
	this.history = history;
}

}
