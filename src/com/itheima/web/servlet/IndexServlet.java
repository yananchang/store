package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

/**
 * the servlet related to the index page
 */
public class IndexServlet extends BaseServlet {
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//去数据库中查询最新商品和热门商品 将他们放入request域中 请求转发
		ProductService ps = new ProductServiceImpl();
		
		//最新商品
		List<Product> newList =null;
		List<Product> hotList =null;
		try {
			newList = ps.findNew();
			hotList = ps.findHot();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//热门商品

		//将两个list放入域中
		request.setAttribute("nList", newList);
		request.setAttribute("hList", hotList);
		
		return "/jsp/index.jsp";
	}

}
