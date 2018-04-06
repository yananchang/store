package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Generic servlet used for sub-classes to extend, is like a job hub & dispatcher
 * 2 purposes: 1-find the right servlet; 2-find the right method
 * 
 */
public class BaseServlet extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取子类               find the class that extends BaseServlet
			Class clazz = this.getClass();
//			System.out.println(this);
			
			//2.获取请求的方法
			String m = request.getParameter("method");
			
			if(m==null) {    // consider the case if there is no method, then return to index page
				m="index";
			}
//			System.out.println(m);
			
			//3.获取方法对象
			Method method = clazz.getMethod(m, HttpServletRequest.class,HttpServletResponse.class);
			
			//4.让方法执行  返回值为请求转发的路径    the returned string is the forwarded path
			String s = (String) method.invoke(this, request,response); //equals to: userservlet.add(req,res)
			
			//5.判断s是否为空  
			if(s!=null) {
				request.getRequestDispatcher(s).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return null;
	}
}
