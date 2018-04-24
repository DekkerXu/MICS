package com.ibm.mics.sql.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;

import com.ibm.mics.entity.util.OrderInfo;
import com.ibm.mics.entity.util.OrderedInfo;
public interface OrderContractMapper {
//	@Select("SELECT * FROM user")
//	List<> getAll();
//	@Select("SELECT * FROM user WHERE userName=#{userName}")
//	User getUser(String userName);
	@Insert("INSERT INTO orderinfo(orderstartDate,orderendDate,city,orderName,certifitype,certifiNumber,sex,birthday,phonenumber,email)"
			+ " VALUES(#{orderinfo.orderstartDate},#{orderinfo.orderendDate},#{orderinfo.city},#{orderinfo.orderName},#{orderinfo.certifitype},"
			+ "#{orderinfo.certifiNumber},#{orderinfo.sex},#{orderinfo.birthday},#{orderinfo.phonenumber},#{orderinfo.email})")
	void insertorderinfo(@Param("orderinfo") OrderInfo orderinfo);
//	@Update("UPDATE user SET password=#{password} WHERE userName =#{userName}")
//	void update(User user);
//	@Delete("DELETE FROM user WHERE userName =#{userName}")
//	void Delete(String username);

	@Select("SELECT * FROM orderedinfo WHERE orderedName=#{orderedName}")
	OrderedInfo getOrderedinfo(String orderedName);
	@Insert("INSERT INTO orderedinfo(orderedName,relationship,certifiType2,certifiNumber2,birthday2,sex2,phoneNumber2) VALUES(#{orderedinfo.orderedName},#{orderedinfo.relationship},#{orderedinfo.certifiType2},#{orderedinfo.certifiNumber2},#{orderedinfo.birthday2},#{orderedinfo.sex2},#{orderedinfo.phoneNumber2})")
	void insertorderedinfo(@Param("orderedinfo") OrderedInfo orderedinfo);
	
}
//,,,,,,,,,email
