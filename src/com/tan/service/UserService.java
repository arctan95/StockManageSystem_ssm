package com.tan.service;

import com.tan.entity.User;

public interface UserService {

	/**
	 * 通过用户名及密码核查用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public User checkLogin(String userName,String password);
}
