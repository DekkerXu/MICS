package com.ibm.mics.entity.util;

public class Claim  implements java.io.Serializable{
	String policyNumber;
	String underwritingPerson;
	int claimNumber;
	String claimHolder;
	String claimState;
	String medicalCareId;
	private Long id;
	
	public Claim(){
		
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public Claim(Long id, String policyNumber,String underwritingPerson,int claimNumber,String claimHolder,String claimState,String medicalCareId){
		super();
		this.id = id;
		this.policyNumber = policyNumber;
		this.underwritingPerson = underwritingPerson;
		this.claimNumber = claimNumber;
		this.claimHolder = claimHolder;
		this.claimState = claimState;
		this.medicalCareId = medicalCareId;
	}
	
	

	public String getMedicalCareId() {
		return medicalCareId;
	}
	public void setMedicalCareId(String medicalCareId) {
		this.medicalCareId = medicalCareId;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getUnderwritingPerson() {
		return underwritingPerson;
	}

	public void setUnderwritingPerson(String underwritingPerson) {
		this.underwritingPerson = underwritingPerson;
	}

	public int getClaimNumber() {
		return claimNumber;
	}

	public void setClaimNumber(int claimNumber) {
		this.claimNumber = claimNumber;
	}

	public String getClaimHolder() {
		return claimHolder;
	}

	public void setClaimHolder(String claimHolder) {
		this.claimHolder = claimHolder;
	}

	public String getClaimState() {
		return claimState;
	}

	public void setClaimState(String claimState) {
		this.claimState = claimState;
	}
	
	
}
