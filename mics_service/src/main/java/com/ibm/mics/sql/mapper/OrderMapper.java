package com.ibm.mics.sql.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;

import com.ibm.mics.entity.util.Order;

public interface OrderMapper {
	@Select("SELECT * FROM order")
	List<Order> getAll();
	//@Insert("INSERT INTO baodan(date,ageType) VALUES(#{order.date},#{order.ageType})")
//	@Select("SELECT * FROM user WHERE userName=#{userName}")
//	User getUser(String userName);
	@Insert("INSERT INTO baodan (kind1,range1,customQuotation1,kind2,range2,\r\n" + 
			"		        			customQuotation2,kind3,range3,customQuotation3,kind4,range4,\r\n" + 
			"		        			customQuotation4,kind5,range5,customQuotation5,kind6,range6,\r\n" + 
			"		        			customQuotation6,kind7,range7,customQuotation7,kind8,range8,\r\n" + 
			"		        			customQuotation8,date,totalValue) "
			+ "VALUES(#{order.kind1},#{order.range1},#{order.customQuotation1},#{order.kind2},#{order.range2},#{order.customQuotation2},#{order.kind3},#{order.range3},#{order.customQuotation3},"
			+ "#{order.kind4},#{order.range4},#{order.customQuotation4},#{order.kind5},#{order.range5},#{order.customQuotation5},#{order.kind6},#{order.range6},#{order.customQuotation6},#{order.kind7},#{order.range7},#{order.customQuotation7},#{order.kind8},#{order.range8},#{order.customQuotation8},#{order.date},#{order.totalValue})")
	void insertOrder(@Param("order") Order order);
//	@Update("UPDATE user SET password=#{password} WHERE userName =#{userName}")
//	void update(User user);
//	@Delete("DELETE FROM user WHERE userName =#{userName}")
//	void Delete(String username);

}

