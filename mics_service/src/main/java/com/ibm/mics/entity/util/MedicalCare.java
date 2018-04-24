package com.ibm.mics.entity.util;

public class MedicalCare {
	String kind;
	String range_value;
	String payment;
	String patientName;
	String certifyType3;
	String certifiNumber3;
	String age;
	String sex3;
	String in_hospital;
	String type_value;
	String visitTime;
	String describe;
	String history;
	String medicalcareID;
	
	public MedicalCare(String certifyType3,String certifiNumber3,String age,String sex3,String in_hospital,String type_value,String visitTime,String describe,
			String kind,String range_value,String history,String payment, String patientName,String medicalcareID) {
		this.kind = kind;
		this.range_value = range_value;
		this.payment = payment;
		this.patientName = patientName;
		this.age=age;
		this.certifyType3=certifyType3;
		this.certifiNumber3=certifiNumber3;
		this.sex3=sex3;
		this.in_hospital=in_hospital;
		this.type_value=type_value;
		this.visitTime=visitTime;
	   this.history=history;
		this.describe=describe;
		this.medicalcareID=medicalcareID;
	}
	
	public String getMedicalcareID() {
		return medicalcareID;
	}

	public void setMedicalcareID(String medicalcareID) {
		this.medicalcareID = medicalcareID;
	}

	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public String getRange_value() {
		return range_value;
	}

	public void setRange_value(String range_value) {
		this.range_value = range_value;
	}

	public String getIn_hospital() {
		return in_hospital;
	}

	public void setIn_hospital(String in_hospital) {
		this.in_hospital = in_hospital;
	}

	public String getType_value() {
		return type_value;
	}

	public void setType_value(String type_value) {
		this.type_value = type_value;
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
