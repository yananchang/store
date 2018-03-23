package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;

public class UserServiceImpl implements UserService{

	/**
	 * 用户注册
	 */
	@Override
	public void regist(User user) throws Exception{

		UserDao dao = new UserDaoImpl();
		dao.add(user);
		
		//发送激活邮件
	}

}
