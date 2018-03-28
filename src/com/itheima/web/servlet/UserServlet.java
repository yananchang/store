package com.itheima.web.servlet;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.itheima.domain.User;
import com.itheima.myconverter.MyConverter;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.MD5Utils;
import com.itheima.utils.UUIDUtils;

/**
 * 和用户相关的servlet             the servlet about User
 */
public class UserServlet extends BaseServlet {

	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("UserServlet, the add method starts executing");
		return null;
	}
	/**
	 * 跳转到注册页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/jsp/register.jsp";
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.封装数据
		User user = new User();
		
		//注册自定义转换器
		ConvertUtils.register(new MyConverter(), Date.class);
		BeanUtils.populate(user, request.getParameterMap());
		
		//1.1 设置用户id
		user.setUid(UUIDUtils.getId());
		//1.2 设置激活码
		user.setCode(UUIDUtils.getCode());
		
		//1.3 加密 密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		//2.调用service完成注册
		UserService s = new UserServiceImpl();
		s.regist(user);
	
		//3.页面请求转发
		request.setAttribute("msg", "registration succeeds, check email to activate~~");
		
		
		return "/jsp/msg.jsp";
	}
}
