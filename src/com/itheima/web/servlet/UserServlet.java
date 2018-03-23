package com.itheima.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.UUIDUtils;

/**
 * 和用户相关的servlet   
 */
public class UserServlet extends BaseServlet {

	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("userservlet the add method executing");
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
		BeanUtils.populate(user, request.getParameterMap());
		
		//1.1 设置用户id
		user.setUid(UUIDUtils.getId());
		//1.2 设置激活码
		user.setCode(UUIDUtils.getCode());
		
		//2.调用service完成注册
		UserService s = new UserServiceImpl();
		s.regist(user);
	
		//3.页面请求转发
		request.setAttribute("msg", "register success, check email to activate~~");
		
		
		return "/jsp/msg.jsp";
	}
}
