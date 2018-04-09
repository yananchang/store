package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;

/**
 * Servlet implementation class AdminOrderServlet
 */
public class AdminOrderServlet extends BaseServlet {
	
	/**
	 * 查询订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.接收state
		String state = request.getParameter("state");
		
		//2.调用service
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		List<Order> list = os.findAllByState(state);
		//3.将返回值list放入域中   请求转发
		request.setAttribute("list", list);
		
		return "/admin/order/list.jsp";
	}

}
