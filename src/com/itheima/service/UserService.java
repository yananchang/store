package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {

	void regist(User user) throws Exception;

	User active(String code);

}
