package com.ibm.mics.sql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ibm.mics.entity.util.Contract;
import com.ibm.mics.entity.util.User;

public interface ContractMapper {
	 @Select("SELECT * FROM contract")
	 List<Contract> getAll();
	 @Select("SELECT * FROM contract WHERE orderedName=#{0} and certifiNumber=#{1} and contractId=#{2}")
	 List<Contract> getContract(String orderedName,String certifiNumber,String contractId);
	 @Select("SELECT * FROM contract WHERE userName=#{userName}")
	 List<Contract> getContractOrder(String userName);
	 @Insert("INSERT INTO contract (kind1,range1,customQuotation1,kind2,range2,\r\n" + 
	   "             customQuotation2,kind3,range3,customQuotation3,kind4,range4,\r\n" + 
	   "             customQuotation4,kind5,range5,customQuotation5,kind6,range6,\r\n" + 
	   "             customQuotation6,kind7,range7,customQuotation7,kind8,range8,\r\n" + 
	   "             customQuotation8,date,totalValue,orderstartDate,orderendDate,city,orderName,certifitype,certifiNumber,sex,birthday,phonenumber,email,orderedName,relationship,certifiType2,certifiNumber2,birthday2,sex2,phoneNumber2,contractState,userName,contractName,manager,contractId) "
	   + "VALUES(#{contract.kind1},#{contract.range1},#{contract.customQuotation1},#{contract.kind2},#{contract.range2},#{contract.customQuotation2},#{contract.kind3},#{contract.range3},#{contract.customQuotation3},"
	   + "#{contract.kind4},#{contract.range4},#{contract.customQuotation4},#{contract.kind5},#{contract.range5},"
	   + "#{contract.customQuotation5},#{contract.kind6},#{contract.range6},#{contract.customQuotation6},#{contract.kind7},#{contract.range7},"
	   + "#{contract.customQuotation7},#{contract.kind8},#{contract.range8},#{contract.customQuotation8},#{contract.date},#{contract.totalValue},"
	   + "#{contract.orderstartDate},#{contract.orderendDate},#{contract.city},#{contract.orderName},#{contract.certifitype},"
	   + "#{contract.certifiNumber},#{contract.sex},#{contract.birthday},#{contract.phonenumber},#{contract.email},#{contract.orderedName},"
	   + "#{contract.relationship},#{contract.certifiType2},"
	   + "#{contract.certifiNumber2},#{contract.birthday2},#{contract.sex2},#{contract.phoneNumber2},#{contract.contractState},#{contract.userName},#{contract.contractName},#{contract.manager},#{contract.contractId})")
	 void insertContract(@Param("contract") Contract contract);
	}



