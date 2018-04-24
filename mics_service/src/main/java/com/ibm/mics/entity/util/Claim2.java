package com.ibm.mics.entity.util;

public class Claim2 {
	long policyNumber;
	String underwritingPerson;
	int claimNumber;
	String claimHolder;
	String claimState;
	
	public Claim2(long policyNumber,String underwritingPerson,int claimNumber,String claimHolder,String claimState){
		super();
		this.policyNumber = policyNumber;
		this.underwritingPerson = underwritingPerson;
		this.claimNumber = claimNumber;
		this.claimHolder = claimHolder;
		this.claimState = claimState;
	}
	
	public long getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(long policyNumber) {
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
