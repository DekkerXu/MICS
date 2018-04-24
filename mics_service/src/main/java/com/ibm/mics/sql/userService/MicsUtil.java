package com.ibm.mics.sql.userService;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ibm.mics.entity.util.Order;
import com.ibm.mics.entity.util.User;
import com.ibm.mics.sql.mapper.*;

@RestController
public class MicsUtil {
	@Autowired
	private UserMapper usermapper;
	@Autowired
	private OrderMapper ordermapper;
	
	@RequestMapping("/addOrder")
    public void saveOrder(Order order) {
    	ordermapper.insertOrder(order);
    }
	
	@RequestMapping("/getUsers")
	public List<User> getUsers(){
		List<User> users=usermapper.getAll();
		return users;
	}
    @RequestMapping("/getUser")
    public User gerUser(String userName){
    	User user=usermapper.getUser(userName);
    	return user;
    }
    @RequestMapping("/addUser")
    public void saveUser(User user) {
    	usermapper.insert(user);
    }
    @RequestMapping("/update")
    public void updateUser(User user) {
    	usermapper.update(user);
    }
    
    @RequestMapping("/delete/{userName}")
    public void deleteUser(@PathVariable String userName) {
    	usermapper.Delete(userName);
    }
}
