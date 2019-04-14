package com.tan.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tan.dao.UserDao;
import com.tan.entity.User;
import com.tan.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	/**
	 * 登录验证
	 */
	@Override
	public User checkLogin(String userName,String password) {
		//根据用户名实例化用户对象
		User user=userDao.getUserByName(userName);
		if(user!=null&&user.getPassword().equals(password)){
			return user;
		}else{
			return null;
		}
	}
}
