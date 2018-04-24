package com.ibm.mics.sql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.ibm.mics.entity.util.*;

public interface MadicalCareMapper {
	@Select("SELECT * FROM medicalcare WHERE patientName=#{0} and certifiNumber3=#{1}")
	List<MedicalCare> getMedical(String patientName,String certifiNumber3);
	@Insert("INSERT INTO medicalcare(kind,fanwei,payment,patientName"
			+ ",certifyType3,certifiNumber3,age,sex3,zhuyuan,type,visitTime,miaoshu,history,medicalcareid) "
			+ "VALUES(#{medicalcare.kind},#{medicalcare.fanwei},#{medicalcare.payment},#{medicalcare.patientName},#{medicalcare.certifyType3},"
			+ "#{medicalcare.certifiNumber3},#{medicalcare.age},#{medicalcare.sex3},#{medicalcare.zhuyuan},#{medicalcare.type},"
			+ "#{medicalcare.visitTime},#{medicalcare.describe},#{medicalcare.history},#{medicalcare.medicalcareID})")
	void insertmedical(@Param("medicalcare") MedicalCare medicalcare);

}