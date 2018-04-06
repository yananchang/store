package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;

/**
 * the servlet related to the index page
 */
public class IndexServlet extends BaseServlet {
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.调用categeoryService  查询所有的分类  返回值是list
		CategoryService cs = new CategoryServiceImpl();
		List<Category> clist = null;
		try {
			clist = cs.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//2.将返回值放入request域中
		request.setAttribute("clist", clist);
		
		//去数据库中查询最新商品和热门商品 将他们放入request域中 请求转发
		//query latest & hot products from db, put them into request, request forwarded
		
		return "/jsp/index.jsp";
	}

}
