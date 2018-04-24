package com.ibm.mics.sql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ibm.mics.entity.util.Claim;
import com.ibm.mics.entity.util.Claim2;
import com.ibm.mics.entity.util.Contract;
import com.ibm.mics.entity.util.User;

public interface ClaimMapper {
	@Select("SELECT * FROM claiminformation")
	List<Claim> getAll();
	@Select("SELECT * FROM claiminformation WHERE claimNumber=#{claimNumber}")
	List<Claim> getAllSettle(@Param("claimNumber") int claimNumber);
	@Select("SELECT * FROM claiminformation WHERE policyNumber=#{policyNumber}")
	List<Claim> getAllClaim(@Param("policyNumber") String policyNumber);
	@Select("SELECT * FROM claiminformation WHERE underwritingPerson=#{underwritingPerson}")
	List<Claim> getClaim(@Param("underwritingPerson") String underwritingPerson);
	@Select("SELECT * FROM claiminformation WHERE underwritingPerson=#{0} and claimNumber=#{1}")
	List<Claim> getClaim2(String underwritingPerson,int claimNumber);
	@Select("SELECT * FROM claiminformation WHERE policyNumber=#{0} and medicalCareId=#{1}")
	Claim getSearchClaim(String policyNumber,String medicalCareId);
	@Select("SELECT * FROM claiminformation WHERE policyNumber=#{policyNumber}")
	Claim getCurrentClaim(@Param("policyNumber") String policyNumber);
	@Select("SELECT count(*) FROM claiminformation")
	Integer getCount();
	@Insert("INSERT INTO claiminformation(id, policyNumber,underwritingPerson,claimNumber,claimHolder,claimState,medicalCareId) VALUES(#{claim.id},#{claim.policyNumber}, #{claim.underwritingPerson}, #{claim.claimNumber}, #{claim.claimHolder}, #{claim.claimState},#{claim.medicalCareId})")
	void insert(@Param("claim") Claim claim);
	@Update("UPDATE claiminformation SET claimNumber=#{claim.claimNumber} WHERE policyNumber=#{claim.policyNumber} and medicalCareId=#{claim.medicalCareId}")
	void updateClaim(@Param("claim") Claim claim);
	@Update("UPDATE claiminformation SET claimState=#{claim.claimState} WHERE policyNumber=#{claim.policyNumber} and medicalCareId=#{claim.medicalCareId}")
	void updateClaim2(@Param("claim") Claim claim);
	@Update("UPDATE claiminformation SET claimNumber=#{claim.claimNumber} WHERE policyNumber=#{claim.policyNumber} and medicalCareId=#{claim.medicalCareId}")
	void updateClaim3(@Param("claim") Claim claim);
	@Update("UPDATE claiminformation SET underwritingPerson=#{0} WHERE policyNumber=#{1} and medicalCareId=#{2}")
	void updateClaimWorker(String underwritingPerson,String policyNumber,String medicalCareId);
	@Update("UPDATE claiminformation SET claimNumber=#{0} WHERE policyNumber=#{1} and medicalCareId=#{2}")
	void updateClaimState(int claimNumber,String policyNumber,String medicalCareId);
	@Delete("DELETE FROM claim WHERE userName =#{userName}")
	void Delete(String username);
}
