package com.ibm.mics.sql.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;

import com.ibm.mics.entity.util.*;

public interface UserMapper {
	
	@Select("SELECT * FROM user")
	List<User> getAll();
	@Select("SELECT * FROM user WHERE userName=#{userName}")
	User getUser(String userName);
	@Insert("INSERT INTO user(userName,password) VALUES(#{user.userName}, #{user.password})")
	void insert(@Param("user") User user);
	@Update("UPDATE user SET password=#{password} WHERE userName =#{userName}")
	void update(User user);
	@Delete("DELETE FROM user WHERE userName =#{userName}")
	void Delete(String username);
}
