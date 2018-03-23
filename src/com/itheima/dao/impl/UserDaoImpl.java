package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao {

	/**
	 * 用户注册
	 * @throws SQLException 
	 */
	@Override
	public void add(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		/**
		 * `uid` varchar(32) NOT NULL,
		  `username` varchar(20) DEFAULT NULL,
		  `password` varchar(20) DEFAULT NULL,
		  `name` varchar(20) DEFAULT NULL,
		  `email` varchar(30) DEFAULT NULL,
		  `telephone` varchar(20) DEFAULT NULL,
		  `birthday` date DEFAULT NULL,
		  `sex` varchar(10) DEFAULT NULL,
		  `state` int(11) DEFAULT NULL,
		  `code` varchar(64) DEFAULT NULL,
		 */
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql,user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),user.getCode());
		
	}

	
}
