package com.itheima.web.servlet;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.itheima.constant.Constant;
import com.itheima.domain.User;
import com.itheima.myconverter.MyConverter;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.MD5Utils;
import com.itheima.utils.UUIDUtils;

/**
 * 和用户相关的servlet
 */
public class UserServlet extends BaseServlet {

	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("UserServlet, the add method starts executing");
		return null;
	}

	/**
	 * 跳转到注册页面
	 * 
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
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1.封装数据
		User user = new User();

		// 注册自定义转换器
		ConvertUtils.register(new MyConverter(), Date.class);
		BeanUtils.populate(user, request.getParameterMap());

		// 1.1 设置用户id
		user.setUid(UUIDUtils.getId());
		// 1.2 设置激活码
		user.setCode(UUIDUtils.getCode());

		// 1.3 加密 密码
		user.setPassword(MD5Utils.md5(user.getPassword()));

		// 2.调用service完成注册
		UserService s = new UserServiceImpl();
		s.regist(user);

		// 3.页面请求转发
		request.setAttribute("msg", "registration succeeds, go to emailbox to activate~~");

		return "/jsp/msg.jsp";
	}

	/**
	 * 用户激活
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 获取激活码
		String code = request.getParameter("code");

		// 2. 调用service完成激活
		UserService s = new UserServiceImpl();
		User user = s.active(code);

		if (user == null) {
			// 通过激活码没有找到用户
			request.setAttribute("msg", "Pls re-activate");
		} else {
			// 添加信息
			request.setAttribute("msg", "activated succesfully");
		}
		// 3.请求转发到msg.jsp

		return "/jsp/msg.jsp";

	}

	/**
	 * 跳转到登陆页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/jsp/login.jsp";
	}

	/**
	 * 登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = MD5Utils.md5(password);
		
		//2.调用service完成登陆操作  返回user
		UserService s = new UserServiceImpl();
		User user = s.login(username,password);
		
		//3. 判断用户
		if(user == null) {
			//用户名密码不匹配
			request.setAttribute("msg", "用户名密码不匹配");
			return "/jsp/login.jsp";
		}else {
			//继续判断用户的状态是否激活
			if(Constant.USER_IS_ACTIVE != user.getState()) {
				request.setAttribute("msg", "用户未激活");
				return "/jsp/login.jsp";
			}
			
		}
		//4.将user放入session中  重定向
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/"); //store index page
		
		return "/jsp/register.jsp";
	}
	
	
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//干掉session
		request.getSession().invalidate();
		
		//重定向
		response.sendRedirect(request.getContextPath());
		//处理自动登录
		
		return null;
	}
}
